package server.controller.account;

import server.controller.Main;
import server.controller.MainController;
import client.model.account.*;
import client.model.product.Cart;
import client.network.AuthToken;
import client.network.Message;
import server.network.server.Server;

import java.util.ArrayList;

public class LoginController extends Server {
    private static LoginController loginController = null;

    private LoginController() {
        super(1100);
        setMethods();
        System.out.println("login server run");
    }

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }


    public Message createAccount(String username, String type, ArrayList<String> details, String detail) {
        Message message = new Message("create account was successful");
        try {
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
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
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

    public Message login(String username, String password) throws Exception {
        System.out.println("in login");
        logout();
        if (!client.model.account.Account.hasThisAccount(username)) {
            Message message = new Message("Error");
            message.addToObjects(new LoginController.AccountUnavailableException());
            return message;
        }
       Account account = Account.getAccountWithUsername(username);
        if (!account.getPassword().equals(password)) {
            Message message = new Message("Error");
            message.addToObjects(new LoginController.IncorrectPasswordException());
            return message;
        }
        MainController mainController = MainController.getInstance();
        Cart cart = ((TempAccount) mainController.getAccount()).getCart();
        MainController.getInstance().setAccount(account);
        Message message = new Message("AccountType");
        if (account instanceof Manager) {
            message.addToObjects(AccountType.MANAGER);
            message.addToObjects(account);
            message.setAuth(AuthToken.generateAuth(account.getUsername()));
            Main.addToTokenHashMap(message.getAuthToken(), account);
            return message;
        }
        else if (account instanceof Buyer) {
            ((Buyer) account).setCart(cart);
            message.addToObjects(AccountType.BUYER);
            message.addToObjects(account);
            message.setAuth(AuthToken.generateAuth(account.getUsername()));
            Main.addToTokenHashMap(message.getAuthToken(), account);
            return message;
        }
        message.addToObjects(AccountType.SELLER);
        message.addToObjects(account);
        message.setAuth(AuthToken.generateAuth(account.getUsername()));
        Main.addToTokenHashMap(message.getAuthToken(), account);
        return message;
    }

    public void logout() {
        MainController.getInstance().setAccount(new TempAccount());
    }

    @Override
    protected void setMethods() {
        methods.add("login");
        methods.add("createAccount");
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
