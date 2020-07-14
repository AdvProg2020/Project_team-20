package client.controller.account;

import client.controller.MainController;
import client.controller.account.user.BuyerController;
import client.model.account.*;
import client.model.product.Cart;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class LoginController {
    private static LoginController loginController = null;

    private static Client client;

    private LoginController() {
    }

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        client = new Client(1100);
        client.readMessage();
        return loginController;
    }

    public static Client getClient() {
        return client;
    }

    public void createAccount(String username, String type, ArrayList<String> details, String detail) throws Exception {
        Message message = new Message("createAccount");
        message.addToObjects(username);
        message.addToObjects(type);
        message.addToObjects(details);
        message.addToObjects(detail);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        client.writeMessage(new Message("buy"));
    }

    public AccountType login(String username, String password) throws Exception {
        Message message = new Message("login");
        message.addToObjects(username);
        message.addToObjects(password);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        client.setAuthToken(answer.getAuthToken());

        client.writeMessage(new Message("buy"));

        Object object = answer.getObjects().get(0);
        GeneralAccount account = (GeneralAccount) answer.getObjects().get(1);
        MainController mainController = MainController.getInstance();
        mainController.setAccount(new TempAccount());
        Cart cart = ((TempAccount) mainController.getAccount()).getCart();
        MainController.getInstance().setAccount(account);
        if (object.equals(AccountType.MANAGER))
            return AccountType.MANAGER;
        else if (object.equals(AccountType.BUYER)) {
            BuyerController.getInstance().getCurrentBuyer().setCart(cart);
            return AccountType.BUYER;
        }
        return AccountType.SELLER;
    }

    public static class CreditIsNotNumber extends Exception {
        public CreditIsNotNumber() {
            super("credit is number");
        }
    }
}
