package client.view.console.account;

import client.controller.account.user.seller.SellerController;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.product.category.SubCategory;
import client.model.product.Field.Field;
import client.model.product.Field.NumericalField;
import client.model.product.Field.OptionalField;
import client.model.product.Product;
import client.model.product.Sale;
import client.model.receipt.SellerReceipt;
import client.view.console.Menu;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerMenu extends Menu {
    private static SellerController sellerController;

    private static SellerMenu sellerMenu = null;

    private SellerMenu() {
        super("SellerMenu");
        setRegex();
        setMethods();
        preProcess();
    }

    public static SellerMenu getInstance() {
        if (sellerMenu == null)
            sellerMenu = new SellerMenu();
        sellerController = SellerController.getInstance();
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
        try {
            System.out.println("Please insert the new field");
            String context = scanner.nextLine().trim();
            //sellerController.editField(field, context);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewCompanyInfo() {
        System.out.println("Your company information:");
        System.out.println(sellerController.viewCompanyInformation());
    }

    public void viewSalesHistory() {
        ArrayList<SellerReceipt> sellerReceipts = sellerController.viewSalesHistory();
        for (SellerReceipt sellerReceipt : sellerReceipts) {
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
        System.out.format("%-20s%-20s%-20s\n", "Name", "Count", "Id");
        for (Product product : allProducts.keySet()) {
            System.out.format("%-20s%-20d%-20s\n", product.getName(), allProducts.get(product), product.getId());
        }
    }

    public void manageProducts() {
        showProducts(sellerController.getProductsToSell());
    }

    public void viewProduct(double idDouble) {
        try {
            Product product = sellerController.viewProduct(Integer.toString((int) idDouble));
            System.out.println("Name                : " + product.getName());
            System.out.println("Count               : " + sellerController.getProductCount(product));
            System.out.println("Id                  : " + product.getId());
            System.out.println("Description         : " + product.getDescription());
            System.out.println("Fields              : ");
            for (Field field : product.getGeneralFields()) {
                if (field instanceof NumericalField)
                    System.out.format("%-20s: %f\n", field.getName(), ((NumericalField) field).getNumericalField());
                else {
                    System.out.format("%-20s: ", field.getName());
                    for (String fieldStr : ((OptionalField) field).getOptionalFiled()) {
                        System.out.print(fieldStr + " ");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewBuyers(double idDouble) {
        try {
            ArrayList<Buyer> buyers = sellerController.viewBuyers(Integer.toString((int) idDouble));
            System.out.println("All buyers of this product: ");
            for (Buyer buyer : buyers) {
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
        HashMap<String, Double> numericalFields = new HashMap<>();
        HashMap<String, ArrayList<String>> optionalFields = new HashMap<>();
        getDetails(details);
        System.out.println("Now you have to add numerical fields of your product.");
        System.out.println("Does your product has numerical fields? (yes/no)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes"))
            getNumericalFields(numericalFields);
        System.out.println("Now you have to add optional fields of your product.");
        System.out.println("Does your product has optional fields? (yes/no)");
        input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes"))
            getOptionalField(optionalFields);
        sellerController.createProduct(details, numericalFields, optionalFields,null);
        System.out.println("Thanks for adding product! :)");
        System.out.println("Your addingProduct request was sent to manager. Manager will accept or reject your request.");
    }

    private void getDetails(ArrayList<String> details) {
        System.out.println("Please insert the name of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the description of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the number of your product.");
        details.add(scanner.nextLine());
        System.out.println("Please insert the price of your product.");
        details.add(scanner.nextLine());
    }

    private void getNumericalFields(HashMap<String, Double> numericalFields) {
        String input;
        System.out.println("How many numerical fields do you want to add?");
        input = scanner.nextLine();
        String intField;
        int count = Integer.parseInt(input);
        for (int i = 0; i < count; i++) {
            System.out.println("Please insert the name of your field.");
            input = scanner.nextLine();
            System.out.println("Please insert the number of your numerical field.");
            intField = scanner.nextLine();
            numericalFields.put(input, Double.parseDouble(intField));
        }
    }

    private void getOptionalField(HashMap<String, ArrayList<String>> optionalFields) {
        String input;
        System.out.println("How many optional fields do you want to add?");
        input = scanner.nextLine();
        int count = Integer.parseInt(input);
        for (int i = 0; i < count; i++) {
            ArrayList<String> optionalFieldsOfField = new ArrayList<>();
            System.out.println("Please insert the name of your field.");
            input = scanner.nextLine();
            System.out.println("How many optional fields does " + input + " filed has?");
            int count2 = Integer.parseInt(scanner.nextLine());
            System.out.println("Please insert them line by line!");
            for (int j = 0; j < count2; j++) {
                optionalFieldsOfField.add(scanner.nextLine());
            }
            optionalFields.put(input, optionalFieldsOfField);
        }
    }

    public void removeProduct(double idDouble) {
        try {
            int id = (int) idDouble;
            sellerController.deleteProduct(Integer.toString(id));
            System.out.println("Product was removed successfully. :(");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCategories() {
        ArrayList<SubCategory> categories = sellerController.showCategories();
        System.out.println("All categories:");
        for (SubCategory subCategory : categories)
            System.out.println(subCategory.getName());
    }

    public void viewOffs() {
        ArrayList<Sale> sales = sellerController.viewOffs();
        System.out.format("%-20s%-20s\n", "Id", "Off");
        for (Sale sale : sales) {
            System.out.format("%-20s%s%s\n", sale.getId(), sale.getSalePercentage() * 100, "%");
        }
    }

    public void viewOff(double idDouble) {
        try {
            Sale sale = sellerController.viewOff(Integer.toString((int) idDouble));
            System.out.println("Id                  : " + sale.getId());
            System.out.println("Off                 : " + sale.getSalePercentage() * 100);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
            System.out.println("Start Date          : " + formatter.format(sale.getStartDate()));
            System.out.println("End Date            : " + formatter.format(sale.getEndDate()));
            showProducts(sale.getProducts());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showProducts(ArrayList<Product> products) {
        System.out.println("Products:");
        System.out.format("%-20s%-20s\n", "Name", "Id");
        for (Product product : products) {
            System.out.format("%-20s%-20s\n", product.getName(), product.getId());
        }
    }

    public void addOff() {
        try {
            ArrayList<String> products = new ArrayList<>();
            ArrayList<String> details = new ArrayList<>();
            System.out.println("Please insert the start date of your sale.\nNote than the pattern of your input must " +
                    "be [MM/dd/yyyy at hh:mm (AM|PM)]. ( Otherwise I will sent you an error:( )");
            details.add(scanner.nextLine());
            System.out.println("Now please insert the end date of your sale.\nNote than the pattern of your input " +
                    "must be [MM/dd/yyyy at hh:mm (AM|PM)]. ( Otherwise I will sent you an error:( )");
            details.add(scanner.nextLine());
            System.out.println("Please insert the percentage of your sale.");
            details.add(scanner.nextLine());
            addProductOff(products);
            //sellerController.addOff(details, products);
            //sellerController.addOff(details, products);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProductOff(ArrayList<String> products) {
        System.out.println("How many products do you want to add to this sale?");
        int count = Integer.parseInt(scanner.nextLine());
        System.out.println("Please insert their id.");
        for (int i = 0; i < count; i++) {
            products.add(scanner.nextLine());
        }
    }

    public void editProduct(double idDouble) {
        System.out.println("Welcome to edit product menu!");
        System.out.println("How many fields do you want to edit?");
        int count = Integer.parseInt(scanner.nextLine());
        ArrayList<String> details = new ArrayList<>();
        details.add("");
        details.add("");
        details.add("");
        ArrayList<String> numericalFieldsToRemove = new ArrayList<>();
        HashMap<String, Double> numericalFieldsToAdd = new HashMap<>();
        ArrayList<String> optionalFieldsToRemove = new ArrayList<>();
        HashMap<String, ArrayList<String>> optionalFieldsToAdd = new HashMap<>();
        String input;
        for (int i = 0; i < count; i++) {
            System.out.println("What is your type of editing? [description| count | price | (remove|add) a" +
                    " (numerical|optional) filed]");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("description"))
                editDescription(details);
            else if (input.equalsIgnoreCase("count"))
                editCount(details);
            else if (input.equalsIgnoreCase("price"))
                editPrice(details);
            else if (input.equalsIgnoreCase("remove a numerical filed"))
                removeNumericalFields(numericalFieldsToRemove);
            else if (input.equalsIgnoreCase("add a numerical field"))
                getNumericalFields(numericalFieldsToAdd);
            else if (input.equalsIgnoreCase("remove an optional filed"))
                removeOptionalFields(optionalFieldsToRemove);
            else if (input.equalsIgnoreCase("add an optional field"))
                getOptionalField(optionalFieldsToAdd);
            else {
                System.out.println("I'm clever:) input a correct type!");
                i--;
            }
        }
        try {
            sellerController.editProduct(Integer.toString((int) idDouble), details, numericalFieldsToRemove,
                    numericalFieldsToAdd, optionalFieldsToRemove, optionalFieldsToAdd);
            System.out.println("Your edit request was sent to manager successfully. I hope they will accept that:)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeOptionalFields(ArrayList<String> optionalFieldsToRemove) {
        System.out.println("How many optional fields do you want to remove?");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            System.out.println("Please insert the name of your field which you want to remove.");
            optionalFieldsToRemove.add(scanner.nextLine());
        }
    }

    private void removeNumericalFields(ArrayList<String> numericalFieldsToRemove) {
        System.out.println("How many numerical fields do you want to remove?");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            System.out.println("Please insert the name of your field which you want to remove.");
            numericalFieldsToRemove.add(scanner.nextLine());
        }
    }

    private void editPrice(ArrayList<String> details) {
        System.out.println("Please insert you edited price:");
        details.set(2, scanner.nextLine());
    }

    private void editCount(ArrayList<String> details) {
        System.out.println("Please insert you edited count:");
        details.set(1, scanner.nextLine());
    }

    private void editDescription(ArrayList<String> details) {
        System.out.println("Please insert you edited description:");
        details.set(0, scanner.nextLine());
    }

    public void editOff(double idDouble) {
        System.out.println("Welcome to edit off menu!");
        System.out.println("How many fields do you want to edit?");
        int count = Integer.parseInt(scanner.nextLine());
        ArrayList<String> details = new ArrayList<>();
        details.add("");
        details.add("");
        details.add("");
        ArrayList<String> productIdsToRemove = new ArrayList<>();
        ArrayList<String> productIdsToAdd = new ArrayList<>();
        String input;
        for (int i = 0; i < count; i++) {
            System.out.println("What is your type of editing? [ start date | end date | sale percentage | " +
                    "(add|remove) product]");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("start date"))
                editStartDate(details);
            else if (input.equalsIgnoreCase("end date"))
                editEndDate(details);
            else if (input.equalsIgnoreCase("sale percentage"))
                editSalePercentage(details);
            else if (input.equalsIgnoreCase("add product"))
                addProductSeller(productIdsToAdd);
            else if (input.equalsIgnoreCase("remove product"))
                removeProductSeller(productIdsToRemove);
            else {
                System.out.println("I'm clever:) input a correct type!");
                i--;
            }
        }
        try {
            //sellerController.editOff(Integer.toString((int) idDouble), details, productIdsToRemove, productIdsToAdd);
            System.out.println("Your edit request was sent to manager successfully. I hope they will accept that:)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeProductSeller(ArrayList<String> productIdsToRemove) {
        System.out.println("How many products do you want to remove?");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            System.out.println("Please insert the Id of the product which you want to remove.");
            productIdsToRemove.add(scanner.nextLine());
        }
    }

    private void addProductSeller(ArrayList<String> productIdsToAdd) {
        System.out.println("How many products do you want to add?");
        int count = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < count; i++) {
            System.out.println("Please insert the Id of the product which you want to add.");
            productIdsToAdd.add(scanner.nextLine());
        }
    }

    private void editSalePercentage(ArrayList<String> details) {
        System.out.println("Please insert you edited sale percentage.");
        details.set(2, scanner.nextLine());
    }

    private void editEndDate(ArrayList<String> details) {
        System.out.println("Please insert you edited end date.\nNote than the pattern of your input must be " +
                "[MM/dd/yyyy at hh:mm (AM|PM)]. ( Otherwise I will sent you an error:( ");
        details.set(1, scanner.nextLine());
    }

    private void editStartDate(ArrayList<String> details) {
        System.out.println("Please insert you edited start date.\nNote than the pattern of your input must be " +
                "[MM/dd/yyyy at hh:mm (AM|PM)]. ( Otherwise I will sent you an error:( ");
        details.set(0, scanner.nextLine());
    }

    public void showAllProducts() {
        ArrayList<Product> products = Product.getAllProducts();
        System.out.format("%-20s%-20s\n", "Name", "id");
        for (Product product : products) {
            System.out.format("%-20s%-20s\n", product.getName()
                    , product.getId());
        }
    }

    public void addToProduct(double idDouble) {
        System.out.println("Please insert the number of this product that you want to add.");
        int count = Integer.parseInt(scanner.nextLine());
        System.out.println("Please insert your price for this product.");
        double price = Double.parseDouble(scanner.nextLine());
        try {
            sellerController.addToProduct(Integer.toString((int) idDouble), count, price);
            System.out.println("Your add request was sent to manager successfully. I hope they will accept that:)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setRegex() {
        regex.add("client.view personal info");
        regex.add("edit (\\d+)");
        regex.add("client.view company information");
        regex.add("client.view sales history");
        regex.add("manage products");
        regex.add("client.view product (\\d+)");
        regex.add("client.view buyers (\\d+)");
        regex.add("edit product (\\d+)");
        regex.add("add product");
        regex.add("remove product (\\d+)");
        regex.add("show categories");
        regex.add("client.view offs");
        regex.add("client.view off (\\d+)");
        regex.add("edit off (\\d+)");
        regex.add("add off");
        regex.add("show all products");
        regex.add("add me to product (\\d+)");
        regex.add("logout");
        regex.add("client.view balance");
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
        methods.add("removeProduct");
        methods.add("showCategories");
        methods.add("viewOffs");
        methods.add("viewOff");
        methods.add("editOff");
        methods.add("addOff");
        methods.add("showAllProducts");
        methods.add("addToProduct");
        methods.add("logoutSeller");
        methods.add("viewBalance");
    }

    public void logoutSeller() {
        sellerController.logout();
        System.out.println("Logout Successful. GoodBye!");
    }

    public void viewBalance() {
        System.out.println("Your credit: " + sellerController.viewBalance());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void help() {
        super.help();
    }

    @Override
    public void enterWithName(String subMenuName) {
        super.enterWithName(subMenuName);
    }

    @Override
    public void back() {
        sellerController.logout();
        System.out.println("Logout Successful. GoodBye!");
        ;
    }

}
