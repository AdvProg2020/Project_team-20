package controller.account;

import controller.MainController;
import model.account.*;
import model.product.Cart;

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
        if (!Account.hasThisAccount(username)) {
            throw new AccountUnavailableException();
        }
        Account account = Account.getAccountWithUsername(username);
        if (!account.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }
        MainController mainController = MainController.getInstance();
        Cart cart = ((TempAccount) mainController.getAccount()).getCart();
        MainController.getInstance().setAccount(account);
        if (account instanceof Manager)
            return AccountType.MANAGER;
        else if (account instanceof Buyer) {
            ((Buyer) account).setCart(cart);
            return AccountType.BUYER;
        }
        return AccountType.SELLER;
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
