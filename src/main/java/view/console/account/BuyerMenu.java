package view.console.account;

import controller.account.user.BuyerController;
import model.account.Account;
import model.product.Cart;
import model.product.Discount;
import model.product.Product;
import model.product.SelectedProduct;
import model.receipt.BuyerReceipt;
import view.console.Menu;
import view.console.product.ProductMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyerMenu extends Menu {
    private static BuyerController buyerController;

    private static BuyerMenu buyerMenu = null;

    public static BuyerMenu getInstance() {
        if (buyerMenu == null)
            buyerMenu = new BuyerMenu();
        buyerController = BuyerController.getInstance();
        return buyerMenu;
    }

    private BuyerMenu() {
        super("BuyerMenu");
        setRegex();
        setMethods();
        preProcess();
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
        try {
            System.out.println("Please insert the new field");
            String context = scanner.nextLine().trim();
            if (context.equalsIgnoreCase("undo"))
                return;
            buyerController.editField(field, context);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewCart() {
        Cart cart = buyerController.viewCart();
        System.out.format("%-20s%-20s%-20s%-20s%-20s\n", "Name", "Count", "Buyer", "Price", "Id");
        for (SelectedProduct selectedProduct : cart.getSelectedProducts()) {
            Product product = selectedProduct.getProduct();
            System.out.format("%-20s%-20s%-20s%-20s%-20s\n", product.getName(), selectedProduct.getCount()
                    , (selectedProduct.getSeller()).getUsername(), product.getPrice(selectedProduct.getSeller()),
                    product.getId());
        }
    }

    public void showProducts() {
        viewCart();
    }

    public void viewProduct(double idDouble) {
        try {
            Product product = buyerController.getProductById(Integer.toString((int) idDouble));
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void increaseProduct(double idDouble, double number) {
        try {
            buyerController.increaseProduct(Integer.toString((int) idDouble), (int) number);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void decreaseProduct(double idDouble, double number) {
        try {
            buyerController.decreaseProduct(Integer.toString((int) idDouble), (int) number);
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
        System.out.println("Are you sure you want to buy (If you have inserted a discount code it will be added! Don't "
                + "worry and just buy:) )? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("no"))
            return;
        try {
            buyerController.purchase(address, phoneNumber, discountCode);
            System.out.println("Thanks for your buying!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewOrders() {
        ArrayList<BuyerReceipt> buyerReceipts = buyerController.viewOrders();
        for (BuyerReceipt buyerReceipt : buyerReceipts) {
            showGeneralDescriptionBuyerReceipt(buyerReceipt);
        }
    }

    public void showOrder(double idDouble) {
        try {
            BuyerReceipt buyerReceipt = buyerController.getBuyerReceiptById(Integer.toString((int) idDouble));
            showGeneralDescriptionBuyerReceipt(buyerReceipt);
            System.out.println("Products you bought: ( Name, Count)");
            showProductsOrder(buyerReceipt.getProducts());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void showProductsOrder(HashMap<Product, Integer> allProducts) {
        for (Product product : allProducts.keySet()) {
            System.out.format("%-20s%-20d\n", product.getName(), allProducts.get(product));
        }
    }

    private void showGeneralDescriptionBuyerReceipt(BuyerReceipt buyerReceipt) {
        System.out.println("____________________");
        System.out.println("Id:                : " + buyerReceipt.getId());
        System.out.println("Discount percentage: " + buyerReceipt.getDiscountPercentage());
        System.out.println("Paid money         : " + buyerReceipt.getPaidMoney());
        System.out.println("Time               : " + buyerReceipt.getDateAndTime());
    }

    public void rate(double idDouble, double score) {
        try {
            buyerController.rate(Integer.toString((int) idDouble), (int) score);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewDiscountCodes() {
        ArrayList<Discount> allDiscounts = buyerController.getAllDiscounts();
        System.out.format("%-20s%-20s%-20s%-20s\n", "Discount Code", "Discount Percentage", "Max number of usage",
                "Number of usage");
        for (Discount discount : allDiscounts) {
            System.out.format("%-20s%-20f%-20s%-20s\n", discount.getDiscountCode(), discount.getDiscountPercentage()
                    , discount.getMaxNumberOfUsage(), discount.getNumberOfUsageBuyer(buyerController.getCurrentBuyer()));
        }
    }

    public void logout() {
        buyerController.logout();
        System.out.println("Logout Successful. GoodBye!");

    }

    private void setRegex() {
        regex.add("view personal info");
        regex.add("edit (\\w+)");
        regex.add("view cart");
        regex.add("show products");
        regex.add("view product (\\w+)");
        regex.add("increase (\\d+) (\\d+)");
        regex.add("decrease (\\d+) (\\d+)");
        regex.add("show total price");
        regex.add("purchase");
        regex.add("view orders");
        regex.add("show order (\\w+)");
        regex.add("rate (\\d+) (\\d+)");
        regex.add("view discount codes");
        regex.add("logout");
        regex.add("view balance");
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
        methods.add("viewDiscountCodes");
        methods.add("logoutBuyer");
        methods.add("viewBalance");
    }

    public void viewBalance() {
        System.out.println("Your credit: " + buyerController.getCredit());
    }

    public void logoutBuyer() {
        buyerController.logout();
        System.out.println("Logout Successful. GoodBye!");
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
        buyerController.logout();
        System.out.println("Logout Successful. GoodBye!");
    }
}
