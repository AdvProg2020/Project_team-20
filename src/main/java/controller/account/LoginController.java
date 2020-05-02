package controller.account;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;

import java.util.ArrayList;

public class LoginController {
    private static LoginController loginController = null;

    private LoginController() {
    }

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }


    public void createAccount(String username, String type, ArrayList<String> details) throws Exception {
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
                createSellerAccount(username, details);
        }
    }

    private void createManagerAccount(String username, ArrayList<String> details) {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit = Double.parseDouble(details.get(5));
        boolean firstManager = Boolean.parseBoolean(details.get(6));
        Account.addAccount(new Manager(name, lastName, email, phoneNumber, username, password, credit, firstManager));
    }

    private void createBuyerAccount(String username, ArrayList<String> details) {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit = Double.parseDouble(details.get(5));
        Manager.addRequest(new Buyer(name, lastName, email, phoneNumber, username, password, credit));
    }

    private void createSellerAccount(String username, ArrayList<String> details) {
        String name = details.get(0), lastName = details.get(1), email = details.get(2), phoneNumber = details.get(3),
                password = details.get(4);
        double credit = Double.parseDouble(details.get(5));
        Manager.addRequest(new Seller(name, lastName, email, phoneNumber, username, password, credit));
    }

    public void login(String username, String password) throws Exception {
        if (!Account.hasThisAccount(username)) {
            throw new AccountUnavailableException();
        }
        Account account = Account.getAccountWithUsername(username);
        if(!account.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }
        MainController.getInstance().setAccount(account);
    }

    //hosele nadaram check konam ke in baiad inja bashe ya na
    public void logout() {
        MainController.getInstance().setAccount(null);
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
}
