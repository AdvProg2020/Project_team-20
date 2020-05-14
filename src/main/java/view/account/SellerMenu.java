package view.account;

import controller.account.user.SellerController;
import model.account.Account;
import model.account.Buyer;
import model.product.Product;
import model.receipt.SellerReceipt;
import view.Menu;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerMenu extends Menu {
    SellerController sellerController;

    private static SellerMenu sellerMenu = null;

    private SellerMenu() {
        super("SellerMenu");
        sellerController = SellerController.getInstance();
        setRegex();
        setMethods();
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
        System.out.println("Name                Count               Id");
        for (Product product:allProducts.keySet()) {
            System.out.format("%s%20d%40s",product.getName(), allProducts.get(product), product.getId());
        }
    }

    public void manageProducts() {
        showProducts(sellerController.getProductsToSell());
    }

    public void viewProduct(String id) {
        try {
            Product product = sellerController.viewProduct(id);
            System.out.println("Name                : " + product.getName());
            System.out.println("Count               : " + sellerController.getProductCount(product));
            System.out.println("Id                  : " + product.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewBuyers(String id) {
        try {
            ArrayList<Buyer> buyers = sellerController.viewBuyers(id);
            System.out.println("All buyers of this product: ");
            for (Buyer buyer:buyers) {
                System.out.println("____________________");
                System.out.println("Name                : " + buyer.getName());
                System.out.println("Username            : " + buyer.getUsername());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProduct() {
        ArrayList<String> details = new ArrayList<>();
        HashMap<String, Integer> numericalFields = new HashMap<>();
        HashMap<String, ArrayList<String>> optionalFields = new HashMap<>();
        getDetails(details);
        getNumericalFields(numericalFields);
        getOptionalField(optionalFields);
        System.out.println("Thanks for adding product!");
        System.out.println("Your addingProduct request was sent to manager. Manager will accept or reject your request.");
    }

    private void getDetails(ArrayList<String> details) {
        System.out.println("Please insert the name of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the details of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the number of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the price of your product.");
        details.add(scanner.nextLine());
    }

    private void getNumericalFields(HashMap<String, Integer> numericalFields) {
        System.out.println("Now you have to add numerical fields of your product.");
        System.out.println("Does your product has numerical fields? (yes/no)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            System.out.println("How many numerical fields do you want to add?");
            input = scanner.nextLine();
            String intField;
            int count = Integer.parseInt(input);
            for (int i=0; i<count; i++) {
                System.out.println("Please insert the name of your field.");
                input = scanner.nextLine();
                System.out.println("Please insert the number of your numerical field.");
                intField = scanner.nextLine();
                numericalFields.put(input, Integer.parseInt(intField));
            }
        }
    }

    private void getOptionalField(HashMap<String, ArrayList<String>> optionalFields) {
        System.out.println("Now you have to add optional fields of your product.");
        System.out.println("Does your product has numerical fields? (yes/no)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            System.out.println("How many optional fields do you want to add?");
            input = scanner.nextLine();
            int count = Integer.parseInt(input);
            ArrayList<String> optionalFieldsOfField = new ArrayList<>();
            for (int i=0; i<count; i++) {
                System.out.println("Please insert the name of your field.");
                input = scanner.nextLine();
                System.out.println("How many optional fields does " + input + " filed has?");
                int count2 = Integer.parseInt(scanner.nextLine());
                System.out.println("Please insert them line by line!");
                for (int j=0; j<count2; j++) {
                    optionalFieldsOfField.add(scanner.nextLine());
                }
                optionalFields.put(input, optionalFieldsOfField);
                optionalFieldsOfField.clear();
            }
        }
    }

    public void editProduct(String id) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view company information");
        regex.add("view sales history");
        regex.add("manage products");
        regex.add("view (\\w+)");
        regex.add("view buyers (\\w+)");
        regex.add("edit (\\w+)");
        regex.add("add product");
    }

    public void setMethods() {
        methods.add("viewPersonalInfo");
        methods.add("editField");
        methods.add("viewCompanyInfo");
        methods.add("viewSalesHistory");
        methods.add("manageProducts");
        methods.add("viewProduct");
        methods.add("viewBuyers");
        methods.add("editProduct");
        methods.add("addProduct");
    }
}
