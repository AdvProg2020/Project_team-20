package view.account;

import controller.account.user.BuyerController;
import model.account.Account;
import model.product.Cart;
import model.product.Discount;
import model.product.Product;
import model.product.SelectedProduct;
import model.receipt.BuyerReceipt;
import view.Menu;
import view.product.ProductMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class   BuyerMenu extends Menu {
    BuyerController buyerController;

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
            Product product = selectedProduct.getProduct();
            System.out.println("Name                 Count               Buyer               Price               Id\n");
            System.out.format("%s%20s%40s%s60%80s", product.getName(), selectedProduct.getCount()
                    , selectedProduct.getSeller().getName(), product.getPrice(), product.getId());
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
            showGeneralDescriptionBuyerReceipt(buyerReceipt);
        }
    }

    public void showOrder(String id) {
        try {
            BuyerReceipt buyerReceipt = buyerController.getBuyerReceiptById(id);
            showGeneralDescriptionBuyerReceipt(buyerReceipt);
            System.out.println("Products you bought: ( Name, Count)");
            showProductsOrder(buyerReceipt.getProducts());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void showProductsOrder(HashMap<Product, Integer> allProducts) {
        for (Product product:allProducts.keySet()) {
            System.out.format("%s%20d",product.getName(), allProducts.get(product));
        }
    }

    private void showGeneralDescriptionBuyerReceipt(BuyerReceipt buyerReceipt) {
        System.out.println("____________________");
        System.out.println("Id:                : " + buyerReceipt.getId());
        System.out.println("Discount percentage: " + buyerReceipt.getDiscountPercentage());
        System.out.println("Paid money         : " + buyerReceipt.getPaidMoney());
        System.out.println("Time               : " + buyerReceipt.getDateAndTime());
    }

    private void rate(String id, int score) {
        try {
            buyerController.rate(id, score);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBalance() {
        System.out.println(buyerController.getCredit());
    }

    private void viewDiscountCodes() {
        ArrayList<Discount> allDiscounts = buyerController.getAllDiscounts();
        System.out.println("Discount Code            Discount Percentage      Max number of usage      Number of usage");
        for (Discount discount:allDiscounts) {
            System.out.format("%s%20f%40s%60s", discount.getDiscountCode(), discount.getDiscountPercentage()
                    , discount.getMaxNumberOfUsage(), discount.getNumberOfUsageBuyer(buyerController.getCurrentBuyer()));
        }
    }

    private void logout() {
        buyerController.logout();
        System.out.println("Logout Successful. GoodBye!");

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
        regex.add("show order (\\w+)");
        regex.add("rate (\\w+) (\\d+)");
        regex.add("view balance");
        regex.add("view discount codes");
        regex.add("logout");
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
        methods.add("showOrder");
        methods.add("rate");
        methods.add("viewBalance");
        methods.add("viewDiscountCodes");
        methods.add("logout");
    }
}
