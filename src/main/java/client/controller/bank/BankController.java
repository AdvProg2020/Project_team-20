package client.controller.bank;


import client.model.bank.BankReceipt;
import client.model.bank.BankReceiptType;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class BankController {
    private static BankController bankController = null;
    private Client client;
    private AuthToken authToken;

    public static BankController getInstance() {
        if (bankController == null) {
            bankController = new BankController();
        }
        return bankController;
    }

    public void login(String username, String password) throws Exception {
        connect();
        Message message = new Message("getToken");
        message.addToObjects(username);
        message.addToObjects(password);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        String ansTxt = answer.getText();
        if (ansTxt.equals("Error"))
            throw new BankException((String) answer.getObjects().get(0));
        else {
            client.setAuthToken(answer.getAuthToken());
            authToken = answer.getAuthToken();
        }
    }

    public String createUser(String username, String password, String name, String lastName, String repeatPass) throws Exception {
        connect();
        Message message = new Message("createAccount");
        message.addToObjects(name);
        message.addToObjects(lastName);
        message.addToObjects(username);
        message.addToObjects(password);
        message.addToObjects(repeatPass);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        String ansTxt = answer.getText();
        if (ansTxt.equals("Error"))
            throw new BankException((String) answer.getObjects().get(0));
        else
            return (String) answer.getObjects().get(0);
    }

    public double getBalance() {
        connect();
        Message message = new Message("getBalance");
        message.addToObjects(client.getAuthToken());
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        System.out.println(answer.getObjects().get(0));
        return (double) answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getDeposits() {
        connect();
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.DEPOSIT);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        return (ArrayList<BankReceipt>) answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getWithdraws() {
        connect();
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.WITHDRAW);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        return (ArrayList<BankReceipt>) answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getMoves() {
        connect();
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.MOVE);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        return (ArrayList<BankReceipt>) answer.getObjects().get(0);
    }

    public void handleCreateDeposit(String money, String description, String destId) throws Exception {
        connect();
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.DEPOSIT);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects("-1");
        message.addToObjects(destId);
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void handleCreateWithdraw(String money, String description, String sourceId) throws Exception {
        connect();
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.WITHDRAW);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects(sourceId);
        message.addToObjects("-1");
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void handleCreateMove(String money, String description, String sourceId, String destId) throws Exception {
        connect();
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.MOVE);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects(sourceId);
        message.addToObjects(destId);
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void handlePay(String receiptID) throws Exception {
        connect();
        Message message = new Message("pay");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(receiptID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void logout() {
        connect();
        Message message = new Message("logout");
        message.addToObjects(client.getAuthToken());
        client.writeMessage(message);
        client.readMessage();
        message = new Message("exit");
        client.writeMessage(message);
        client.setAuthToken(null);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static class BankException extends Exception {
        public BankException(String txt) {
            super(txt);
        }
    }

    private void connect() {
        Client client = new Client(9000);
        bankController.setClient(client);
        client.readMessage();
        client.setAuthToken(authToken);
    }
}
