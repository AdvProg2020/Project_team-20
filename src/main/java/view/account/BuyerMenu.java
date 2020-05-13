package view.account;

import controller.account.user.BuyerController;
import controller.product.ProductController;
import model.account.Account;
import model.product.Cart;
import model.product.Product;
import model.product.SelectedProduct;
import model.receipt.BuyerReceipt;
import view.Menu;
import view.product.ProductMenu;

import java.util.ArrayList;
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
            System.out.println("Name                 Count               Buyer               id\n");
            System.out.format("%s%20s%40s%s60", selectedProduct.getProduct().getName(), selectedProduct.getCount()
                    , selectedProduct.getSeller().getName(), selectedProduct.getProduct().getId());
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

    public void decreaseProduct(String id, int number) {
        try {
            buyerController.decreaseProduct(id, number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showTotalPrice() {
        System.out.println("Total Price is: " + buyerController.getTotalPrice());
    }

    public void purchase() {
        System.out.println("Please insert your address");
        String address = scanner.nextLine();
        System.out.println("Please insert your phone number");
        String phoneNumber = scanner.nextLine();
        System.out.println("Please insert your discount code otherwise insert enter!");
        String discountCode = scanner.nextLine();
        System.out.println("You are buying all these products:");
        viewCart();
        showTotalPrice();
        System.out.println("Are you sure you want to buy? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("no"))
            return;
        try {
            buyerController.purchase(address, phoneNumber, discountCode);
            viewCart();
            System.out.println("Thanks for your buying!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewOrders() {
        ArrayList<BuyerReceipt> buyerReceipts = buyerController.viewOrders();
        for (BuyerReceipt buyerReceipt:buyerReceipts) {
            System.out.println("____________________");
            System.out.println("id:                :" + buyerReceipt.getId());
            System.out.println("discount percentage:" + buyerReceipt.getDiscountPercentage());
            System.out.println("Time               :" + buyerReceipt.getDateAndTime());
        }
    }

    private void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view cart");
        regex.add("show products");
        regex.add("view (\\w+)");
        regex.add("increase (\\w+) (\\d+)");
        regex.add("decrease (\\w+) (\\d+)");
        regex.add("show total price");
        regex.add("purchase");
        regex.add("view orders");
    }

    private void setMethods() {
        methods.add("viewPersonalInfo");
        methods.add("editField");
        methods.add("viewCart");
        methods.add("showProducts");
        methods.add("viewProduct");
        methods.add("increaseProduct");
        methods.add("decreaseProduct");
        methods.add("showTotalPrice");
        methods.add("purchase");
        methods.add("viewOrders");
    }
}
