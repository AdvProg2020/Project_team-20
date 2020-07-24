package client.controller.account;

import client.controller.MainController;
import client.controller.account.user.BuyerController;
import client.model.account.*;
import client.model.product.Cart;
import client.network.Client;
import client.network.Message;

import java.io.File;
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
        client.disconnect();
    }

    public AccountType login(String username, String password) throws Exception {
        Message message = new Message("login");
        message.addToObjects(username);
        message.addToObjects(password);
        client.writeMessage(message);
        Message answer1 = client.readMessage();
        if (answer1.getText().equals("Error")) {
            throw (Exception) answer1.getObjects().get(0);
        }


        Message receiveImageMessage = new Message("giveProductsImage");
        client.writeMessage(receiveImageMessage);
        String path = client.readMessage().getText();
        if (hasImage(path)) {
            System.out.println("have image for account");
            client.writeMessage(new Message("has file"));
            client.readMessage();
        } else {
            client.writeMessage(new Message("give me that image"));
            client.receiveImage(path);
        }
        client.writeMessage(new Message("now log me in"));

        Message answer = client.readMessage();
        System.out.println(answer.getText());
        client.setAuthToken(answer.getAuthToken());
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
        } else if (object.equals(AccountType.SUPPORTER)) {
            return AccountType.SUPPORTER;
        }
        return AccountType.SELLER;
    }


    private static boolean hasImage(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }


    public static class CreditIsNotNumber extends Exception {
        public CreditIsNotNumber() {
            super("credit is number");
        }
    }
}
