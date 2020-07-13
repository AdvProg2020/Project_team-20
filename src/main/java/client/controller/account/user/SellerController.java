package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.product.Product;
import client.model.product.Sale;
import client.model.product.category.SubCategory;
import client.model.receipt.SellerReceipt;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerController implements AccountController {
    private static SellerController sellerController = null;
    private MainController mainController = MainController.getInstance();
    private static Seller seller;
    private static Client client;

    private SellerController() {
    }

    public static SellerController getInstance() {
        if (sellerController == null)
            sellerController = new SellerController();
        seller = (Seller) MainController.getInstance().getAccount();
        client = new Client(4000);
        client.setAuthToken(LoginController.getClient().getAuthToken());
        client.readMessage();
        System.out.println(client.getAuthToken());
        return sellerController;
    }

    public void addAdvertisement(Product product, Seller seller, String text) throws Exception {
        Message message = new Message("addAdvertisement");
        message.addToObjects(product.getId());
        message.addToObjects(seller.getUsername());
        message.addToObjects(text);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public String viewCompanyInformation() {
        Message message = new Message("viewCompanyInformation");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (String) answer.getObjects().get(0);
    }

    public ArrayList<SellerReceipt> viewSalesHistory() {
        Message message = new Message("viewSalesHistory");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<SellerReceipt>) answer.getObjects().get(0);
    }

    public ArrayList<Product> getAllProducts() {
        Message message = new Message("getAllProducts");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Product>) answer.getObjects().get(0);
    }

    public ArrayList<Product> getSellerProducts() {
        Message message = new Message("getSellerProducts");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Product>) answer.getObjects().get(0);
    }

    public Product viewProduct(String productId) throws Exception {
        Message message = new Message("viewProduct");
        message.addToObjects(productId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Product) answer.getObjects().get(0);
    }

    public ArrayList<Buyer> viewBuyers(String productId) throws Exception {
        Message message = new Message("viewBuyers");
        message.addToObjects(productId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (ArrayList<Buyer>) answer.getObjects().get(0);
    }

    public void editProduct(String productId, ArrayList<String> details, ArrayList<String> numericalFieldsToRemove,
                            HashMap<String, Double> numericalFieldsToAdd,
                            ArrayList<String> optionalFieldsTORemove,
                            HashMap<String, ArrayList<String>> optionalFieldsToAdd) throws Exception {
        Message message = new Message("editProduct");
        message.addToObjects(productId);
        message.addToObjects(details);
        message.addToObjects(numericalFieldsToRemove);
        message.addToObjects(numericalFieldsToAdd);
        message.addToObjects(optionalFieldsTORemove);
        message.addToObjects(optionalFieldsToAdd);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addToProduct(String id, int count, double price) throws Exception {
        Message message = new Message("addToProduct");
        message.addToObjects(id);
        message.addToObjects(count);
        message.addToObjects(price);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void createProduct(ArrayList<String> details, HashMap<String, Double> numericalFields,
                              HashMap<String, ArrayList<String>> optionalFields, String path) {
        Message message = new Message("createProduct");
        message.addToObjects(details);
        message.addToObjects(numericalFields);
        message.addToObjects(optionalFields);
        message.addToObjects(path);
        client.writeMessage(message);
        client.readMessage();
    }

    public void deleteProduct(String productId) throws Exception {
        Message message = new Message("deleteProduct");
        message.addToObjects(productId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<SubCategory> showCategories() {
        Message message = new Message("showCategories");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<SubCategory>) answer.getObjects().get(0);
    }

    public ArrayList<Sale> viewOffs() {
        Message message = new Message("viewOffs");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Sale>) answer.getObjects().get(0);
    }

    public Sale viewOff(String offId) throws Exception {
        Message message = new Message("viewOff");
        message.addToObjects(offId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Sale) answer.getObjects().get(0);
    }

    public void editOff(String offId, LocalDateTime startDate, LocalDateTime endDate, String salePercentageStr, ArrayList<String> productIdsToRemove,
                        ArrayList<String> productIdsToAdd) throws Exception {
        Message message = new Message("editOff");
        message.addToObjects(offId);
        message.addToObjects(startDate);
        message.addToObjects(endDate);
        message.addToObjects(salePercentageStr);
        message.addToObjects(productIdsToAdd);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addOff(LocalDateTime startDate, LocalDateTime endDate, double percentage, ArrayList<String> productIds) throws Exception {
        Message message = new Message("addOff");
        message.addToObjects(startDate);
        message.addToObjects(endDate);
        message.addToObjects(percentage);
        message.addToObjects(productIds);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public double viewBalance() {
        Message message = new Message("viewBalance");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (double) answer.getObjects().get(0);
    }

    public static class SaleUnavailableException extends Exception {
        public SaleUnavailableException() {
            super("sale(off) unavailable");
        }
    }

    public HashMap<Product, Integer> getProductsToSell() {
        Message message = new Message("getProductsToSell");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (HashMap<Product, Integer>) answer.getObjects().get(0);
    }

    public int getProductCount(Product product) {
        Message message = new Message("getProductCount");
        message.addToObjects(product.getId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (int) answer.getObjects().get(0);
    }

    public static Seller getSeller() {
        client.writeMessage(new Message("getSeller"));
        return (Seller) client.readMessage().getObjects().get(0);
    }

    @Override
    public Account getAccountInfo() {
        client.writeMessage(new Message("getAccountInfo"));
        return (Account) client.readMessage().getObjects().get(0);
    }

    @Override
    public void editField(String field, String context) throws Exception {
        Message message = new Message("editField");
        message.addToObjects(field);
        message.addToObjects(context);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void setProfileImage(String path) {
        Message message = new Message("setProfileImage");
        message.addToObjects(path);
        client.writeMessage(message);
        seller.setImagePath(path);
        client.readMessage();
    }

    @Override
    public void changeMainImage(Image image) {
        Message message = new Message("changeMainImage");
        message.addToObjects(image);
        client.writeMessage(message);
        seller.getGraphicPackage().setMainImage(image);
        client.readMessage();
    }

    @Override
    public void logout() {
        client.writeMessage(new Message("logout"));
        mainController.logout();
        client.readMessage();
        client.writeMessage(new Message("buy"));
        client.disconnect();
    }
}
