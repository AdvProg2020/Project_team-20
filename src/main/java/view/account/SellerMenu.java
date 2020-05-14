package view.account;

import controller.account.user.SellerController;
import model.account.Account;
import model.product.Product;
import model.receipt.SellerReceipt;
import view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SellerMenu extends Menu {
    SellerController sellerController;
    Scanner scanner;

    private static SellerMenu sellerMenu = null;

    private SellerMenu() {
        super("SellerMenu");
        sellerController = SellerController.getInstance();
        setRegex();
        setMethods();
        scanner = new Scanner(System.in);
    }

    public static SellerMenu getInstance() {
        if (sellerMenu == null)
            sellerMenu = new SellerMenu();
        return sellerMenu;
    }

    public void viewPersonalInfo() {
        Account account = sellerController.getAccountInfo();
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
            sellerController.editField(field, context);
        }
    }

    public void viewCompanyInfo() {
        System.out.println("Your company information:");
        System.out.println(sellerController.viewCompanyInformation());
    }

    public void viewSalesHistory() {
        ArrayList<SellerReceipt> sellerReceipts = sellerController.viewSalesHistory();
        for (SellerReceipt sellerReceipt:sellerReceipts) {
            showSellerReceipt(sellerReceipt);
        }
    }

    public void showSellerReceipt(SellerReceipt sellerReceipt) {
        System.out.println("____________________");
        System.out.println("Id:                    : " + sellerReceipt.getId());
        System.out.println("DiscountCode percentage: " + sellerReceipt.getDiscountPercentage());
        System.out.println("Time                   : " + sellerReceipt.getDateAndTime());
        System.out.println("Your discount amount   : " + sellerReceipt.getDiscountAmount());
        System.out.println("Received money         : " + sellerReceipt.getReceivedMoney());
        System.out.println("Buyer                  : " + sellerReceipt.getBuyer().getUsername());
        System.out.println("Products sold          : ");
        showProducts(sellerReceipt.getProducts());
    }

    private void showProducts(HashMap<Product, Integer> allProducts) {
        for (Product product:allProducts.keySet()) {
            System.out.format("%s%20d",product.getName(), allProducts.get(product));
        }
    }

    public void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view company information");
        regex.add("view sales history");
    }

    public void setMethods() {
        methods.add("viewPersonalInfo");
        methods.add("editField");
        methods.add("viewCompanyInfo");
        methods.add("viewSalesHistory");
    }
}
