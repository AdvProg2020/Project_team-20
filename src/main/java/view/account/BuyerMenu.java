package view.account;

import controller.account.user.BuyerController;
import model.account.Account;
import model.product.Cart;
import model.product.Product;
import model.product.SelectedProduct;
import view.Menu;

import java.util.Scanner;

public class BuyerMenu extends Menu {
    BuyerController buyerController;
    Scanner scanner;

    public BuyerMenu() {
        super("BuyerMenu");
        setRegex();
        setMethods();
        buyerController = BuyerController.getInstance();
        scanner = new Scanner(System.in);
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

    public void editField(String field) {
        if (!field.equalsIgnoreCase("name") && !field.equalsIgnoreCase("last name") &&
            !field.equalsIgnoreCase("email") && !field.equalsIgnoreCase("phone number") &&
            !field.equalsIgnoreCase("password") && !field.equalsIgnoreCase("username") &&
            !field.equalsIgnoreCase("field")) {
            System.out.println("Field is not valid!");
        }
        else {
            System.out.println("Please insert the new field");
            String context = scanner.nextLine().trim();
            buyerController.editField(field, context);
        }
    }

    public void viewCart() {
        Cart cart = buyerController.viewCart();
        for (SelectedProduct selectedProduct:cart.getSelectedProducts()) {
            System.out.println("Name                 Count               Buyer\n");
            System.out.format("%s%20s %40s", selectedProduct.getProduct().getName(), selectedProduct.getCount(), selectedProduct.getSeller().getName());
        }
    }

    private void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view cart");
    }

    private void setMethods() {
        methods.add("viewPersonalInfo");
        methods.add("editField");
        regex.add("viewCart");
    }
}
