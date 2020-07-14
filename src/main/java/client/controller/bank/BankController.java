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

    public static BankController getInstance() {
        if (bankController==null) {
            bankController = new BankController();
            Client client = new Client(9000);
            bankController.setClient(client);
            client.readMessage();
        }
        return bankController;
    }

    public void login(String username, String password) throws Exception{
        Message message = new Message("getToken");
        message.addToObjects(username);
        message.addToObjects(password);
        client.writeMessage(message);
        Message answer = client.readMessage();
        String ansTxt = answer.getText();
        if (ansTxt.equals("Error"))
            throw new BankException((String)answer.getObjects().get(0));
        else {
            System.out.println("hi " + answer.getAuthToken());
            client.setAuthToken(answer.getAuthToken());
        }
    }

    public String createUser(String username, String password, String name, String lastName, String repeatPass) throws Exception{
        Message message = new Message("createAccount");
        message.addToObjects(name);
        message.addToObjects(lastName);
        message.addToObjects(username);
        message.addToObjects(password);
        message.addToObjects(repeatPass);
        client.writeMessage(message);
        Message answer = client.readMessage();
        String ansTxt = answer.getText();
        if (ansTxt.equals("Error"))
            throw new BankException((String)answer.getObjects().get(0));
        else
            return (String)answer.getObjects().get(0);
    }

    public double getBalance() {
        Message message = new Message("getBalance");
        message.addToObjects(client.getAuthToken());
        client.writeMessage(message);
        Message answer = client.readMessage();
        System.out.println(answer.getObjects().get(0));
        return (double)answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getDeposits() {
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.DEPOSIT);
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<BankReceipt>)answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getWithdraws() {
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.WITHDRAW);
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<BankReceipt>)answer.getObjects().get(0);
    }

    public ArrayList<BankReceipt> getMoves() {
        Message message = new Message("getTransactions");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.MOVE);
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<BankReceipt>)answer.getObjects().get(0);
    }

    public void handleCreateDeposit(String money, String description, String destId) throws Exception{
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.DEPOSIT);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects("-1");
        message.addToObjects(destId);
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void handleCreateWithdraw(String money, String description, String sourceId) throws Exception{
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.WITHDRAW);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects(sourceId);
        message.addToObjects("-1");
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
    }

    public void handleCreateMove(String money, String description, String sourceId, String destId) throws Exception{
        Message message = new Message("createReceipt");
        message.addToObjects(client.getAuthToken());
        message.addToObjects(BankReceiptType.MOVE);
        message.addToObjects(Double.parseDouble(money));
        message.addToObjects(sourceId);
        message.addToObjects(destId);
        message.addToObjects(description);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw new BankException(answer.getObjects().get(0).toString());
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
}
