package controller.account.user;

import controller.MainController;
import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.Category;
import model.product.Field.Field;
import model.product.Field.NumericalField;
import model.product.Product;
import model.product.Sale;
import model.receipt.SellerReceipt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerController implements AccountController {
    private static SellerController sellerController = null;
    private MainController mainController = MainController.getInstance();
    private Seller seller;

    private SellerController() {
        this.seller = (Seller) mainController.getAccount();
    }

    public static SellerController getInstance() {
        if (sellerController == null)
            return new SellerController();
        return sellerController;
    }

    public String viewCompanyInformation() {
        HashMap<String, String> details = seller.getDetails();
        return details.get("CompanyInformation");
    }

    public ArrayList<SellerReceipt> viewSalesHistory() {
        return seller.getSaleHistory();
    }

    public Product viewProduct(String productId) throws Exception {
        return Product.getProductById(productId);
    }

    public ArrayList<Buyer> viewBuyers(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        return product.getBuyers();
    }

    //TODO nemidonam che konam kase che konam gereftam dastam
    public void editProduct(String productId) {

    }

    public void addProduct(ArrayList<String> details) {
        Product product = new Product();
        Manager.addRequest(product);
    }

    public void increaseProduct(String productId, int quantity) throws Exception {
        Product product = Product.getProductById(productId);
        seller.increaseProduct(product, quantity);
    }

    public void decreaseProduct(String productId, int quantity) throws Exception {
        Product product = Product.getProductById(productId);
        seller.decreaseProduct(product, quantity);
    }

    public void deleteProduct(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        seller.removeFromProductsToSell(product);
    }

    public ArrayList<Category> showCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<Sale> viewOffs() {
        return seller.getSales();
    }

    public Sale viewOff(String offId) throws SaleUnavailableException {
        return getSaleWithId(offId);
    }

    public void editOff(String offId, ArrayList<String> details, ArrayList<String> productIdsToRemove,
                        ArrayList<String> productIdsToAdd) throws Exception {
        Sale sale = getSaleWithId(offId);
        LocalDateTime startDate, endDate;
        double salePercentage;
        ArrayList<Product> productsToRemove = Product.getProductsWithIds(productIdsToRemove),
                productsToAdd = Product.getProductsWithIds(productIdsToAdd), newProducts = getSaleProducts(offId);
        if (!details.get(0).isEmpty()) {
            startDate = LocalDateTime.parse(details.get(0));
        } else startDate = sale.getStartDate();
        if (!details.get(1).isEmpty()) {
            endDate = LocalDateTime.parse(details.get(1));
        } else endDate = sale.getEndDate();
        if (!details.get(2).isEmpty()) {
            salePercentage = Double.parseDouble(details.get(2));
        } else salePercentage = sale.getSalePercentage();
        //momkene hamchin producty tosh nabashe onvaght chi
        for (Product product : productsToRemove) {
            newProducts.remove(product);
        }
        newProducts.addAll(productsToAdd);
        sale.changeStateEdited(newProducts, startDate, endDate, salePercentage);
    }

    private ArrayList<Product> getSaleProducts(String offId) throws Exception {
        Sale sale = getSaleWithId(offId);
        return new ArrayList<>(sale.getProducts());
    }

    public void addOff(ArrayList<String> details, ArrayList<String> productIds) throws Exception {
        String id = details.get(0);
        LocalDateTime startDate = LocalDateTime.parse(details.get(1)), endDate = LocalDateTime.parse(details.get(2));
        double salePercentage = Double.parseDouble(details.get(3));
        ArrayList<Product> products = Product.getProductsWithIds(productIds);
        Sale sale = new Sale(id, products, startDate, endDate, salePercentage);
        Manager.addRequest(sale);
    }

    public double viewBalance() {
        return seller.getCredit();
    }

    private Sale getSaleWithId(String offId) throws SaleUnavailableException {
        for (Sale sale : seller.getSales()) {
            if (sale.getId().equals(offId))
                return sale;
        }
        throw new SaleUnavailableException();
    }

    public static class SaleUnavailableException extends Exception {
        public SaleUnavailableException() {
            super("sale(off) unavailable");
        }
    }


    @Override

    public Account getAccountInfo() {
        return (Account) mainController.getAccount();
    }

    @Override
    public void editField(String filed, String context) {
    }

    @Override
    public void logout() {
        mainController.setAccount(null);
    }
}
