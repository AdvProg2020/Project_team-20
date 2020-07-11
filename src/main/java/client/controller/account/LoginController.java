package client.controller.account;

import client.controller.MainController;
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
        if (Account.hasThisAccount(username)) {
            throw new AccountIsAvailableException();
        }
        switch (type) {
            case "manager":
                createManagerAccount(username, details);
                break;
            case "buyer":
                createBuyerAccount(username, details);
                break;
            case "seller":
                createSellerAccount(username, details, detail);
        }
    }

    private void createManagerAccount(String username, ArrayList<String> details) throws Exception {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit;
        try {
            credit = Double.parseDouble(details.get(5));
        } catch (Exception e) {
            throw new CreditIsNotNumber();
        }
        if (Manager.isHasFirstManger()) {
            throw new ThereIsFirstManagerException();
        }
        Account.addAccount(new Manager(name, lastName, email, phoneNumber, username, password, credit, true));
        Manager.setHasFirstManger(true);
    }

    private void createBuyerAccount(String username, ArrayList<String> details) throws CreditIsNotNumber {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit;
        try {
            credit = Double.parseDouble(details.get(5));
        } catch (Exception e) {
            throw new CreditIsNotNumber();
        }
        Manager.addRequest(new Buyer(name, lastName, email, phoneNumber, username, password, credit));
    }

    private void createSellerAccount(String username, ArrayList<String> details, String detail) throws CreditIsNotNumber {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit;
        try {
            credit = Double.parseDouble(details.get(5));
        } catch (Exception e) {
            throw new CreditIsNotNumber();
        }
        Manager.addRequest(new Seller(name, lastName, email, phoneNumber, username, password, credit, detail));
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
        Object object = answer.getObjects().get(0);
        GeneralAccount account = (GeneralAccount) answer.getObjects().get(1);
        MainController mainController = MainController.getInstance();
        Cart cart = ((TempAccount) mainController.getAccount()).getCart();
        MainController.getInstance().setAccount(account);
        if (object.equals(AccountType.MANAGER))
            return AccountType.MANAGER;
        else if (object.equals(AccountType.BUYER)) {
            return AccountType.BUYER;
        }
        return AccountType.SELLER;
    }

    public void logout() {
        MainController.getInstance().setAccount(new TempAccount());
    }

    public static class AccountIsAvailableException extends Exception {
        public AccountIsAvailableException() {
            super("Account already exist");
        }
    }

    public static class AccountUnavailableException extends Exception {
        public AccountUnavailableException() {
            super("Account is unavailable");
        }
    }

    public static class IncorrectPasswordException extends Exception {
        public IncorrectPasswordException() {
            super("password is incorrect");
        }
    }

    public static class ThereIsFirstManagerException extends Exception {
        public ThereIsFirstManagerException() {
            super("there is a first manager");
        }
    }

    public static class CreditIsNotNumber extends Exception {
        public CreditIsNotNumber() {
            super("credit is number");
        }
    }
}
