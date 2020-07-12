package client.controller.bank;


import client.network.AuthToken;
import client.network.Client;
import client.network.Message;

public class BankController {
    private static BankController bankController;
    private static Client client;

    public static BankController getInstance() {
        if (bankController==null)
            bankController = new BankController();
        client = new Client(9000);
        client.readMessage();
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
        else
            client.setAuthToken(answer.getAuthToken());
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

    public static Client getClient() {
        return client;
    }

    public static class BankException extends Exception {
        public BankException(String txt) {
            super(txt);
        }
    }
}
