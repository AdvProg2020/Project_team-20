package view.account;

import controller.account.user.BuyerController;
import controller.product.ProductController;
import model.account.Account;
import model.product.Cart;
import model.product.Product;
import model.product.SelectedProduct;
import view.Menu;
import view.product.ProductMenu;

import java.util.Scanner;

public class BuyerMenu extends Menu {
    BuyerController buyerController;
    Scanner scanner;

    private static BuyerMenu buyerMenu = null;

    public static BuyerMenu getInstance() {
        if (buyerMenu == null)
            buyerMenu = new BuyerMenu();
        return buyerMenu;
    }

    private BuyerMenu() {
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

    public void showProducts() {
        viewCart();
    }

    public void viewProduct(String id) {
        try {
            Product product = buyerController.getProductById(id);
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void increaseProduct(String id, int number) {
        try {
            buyerController.increaseProduct(id, number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view cart");
        regex.add("show products");
        regex.add("view (\\w+)");
        regex.add("increase (\\w+) (\\d+)");
    }

    private void setMethods() {
        methods.add("viewPersonalInfo");
        methods.add("editField");
        methods.add("viewCart");
        methods.add("showProducts");
        methods.add("viewProduct");
        methods.add("increaseProduct");
    }
}
