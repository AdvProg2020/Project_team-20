package view.account;

import controller.account.user.BuyerController;
import model.account.Account;
import view.Menu;

public class BuyerMenu extends Menu {
    BuyerController buyerController;

    public BuyerMenu() {
        super("BuyerMenu");
        setRegex();
        buyerController = BuyerController.getInstance();
    }

    @Override
    public void show() {
    }

    public void viewPersonalInfo() {
        Account account = buyerController.getAccountInfo();
        System.out.println("Name:         " + account.getName());
        System.out.println("LastName:     " + account.getLastName());
        System.out.println("Email:        " + account.getEmail());
        System.out.println("Phone number: " + account.getPhoneNumber());
        System.out.println("Username:     " + account.getUsername());
        System.out.println("Password:     " + account.getPassword());
        System.out.println("Credit:       " + account.getCredit());
    }

    private void setRegex() {
        regex.add("view personal info");
    }

    private void setMethods() {
        methods.add("viewPersonalInfo");
    }
}
