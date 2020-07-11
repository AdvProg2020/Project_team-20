package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.account.*;
import client.model.product.*;
import client.model.receipt.BuyerReceipt;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class BuyerController implements AccountController {
    private MainController mainController;
    private static Buyer currentBuyer;
    private static Client client;

    private static BuyerController buyerController = null;

    private BuyerController() {
        this.mainController = MainController.getInstance();
    }

    public static BuyerController getInstance() {
        if (buyerController == null)
            buyerController = new BuyerController();
        currentBuyer = (Buyer) MainController.getInstance().getAccount();
        client = new Client(6000);
        client.setAuthToken(LoginController.getClient().getAuthToken());
        System.out.println(client.getAuthToken());
        return buyerController;
    }

    public static Client getClient() {
        return client;
    }

    public ArrayList<Product> getAllProducts() {
        client.writeMessage(new Message("getAllProducts"));
        // todo check for arraylist
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<Discount> getAllDiscounts() {
        client.writeMessage(new Message("getAllDiscounts"));
        //todo check for array list
        return (ArrayList<Discount>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<BuyerReceipt> viewOrders() {
        System.out.println("in view Orders");
        client.writeMessage(new Message("viewOrders"));
        //todo check for arraylist
        return (ArrayList<BuyerReceipt>) client.readMessage().getObjects().get(0);
    }

    public void rate(String productId, double score) throws Exception {
        Message message = new Message("rate");
        message.addToObjects(productId);
        message.addToObjects(score);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public BuyerReceipt getBuyerReceiptById(String id) throws Exception {
        Message message = new Message("getBuyerReceiptById");
        message.addToObjects(id);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (BuyerReceipt) answer.getObjects().get(0);
    }

    public void purchase(String address, String phoneNumber, String discountCode) throws Exception {
        Message message = new Message("purchase");
        message.addToObjects(address);
        message.addToObjects(phoneNumber);
        message.addToObjects(discountCode);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public double getTotalPrice() {
        client.writeMessage(new Message("getTotalPrice"));
        return (double) client.readMessage().getObjects().get(0);
    }

    public double getDiscount() {
        client.writeMessage(new Message("getDiscount"));
        return (double) client.readMessage().getObjects().get(0);
    }

    public Cart viewCart() {
        client.writeMessage(new Message("viewCart"));
        return (Cart) client.readMessage().getObjects().get(0);
    }

    public Product getProductById(String id) throws Exception {
        Message message = new Message("getProductById");
        message.addToObjects(id);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Product) answer.getObjects().get(0);
    }

    public void increaseProduct(String id, int number) throws Exception {
        Message message = new Message("increaseProduct");
        message.addToObjects(id);
        message.addToObjects(number);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void decreaseProduct(String id, int number) throws Exception {
        Message message = new Message("decreaseProduct");
        message.addToObjects(id);
        message.addToObjects(number);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public double getCredit() {
        client.writeMessage(new Message("getCredit"));
        return (double) client.readMessage().getObjects().get(0);
    }

    public Buyer getCurrentBuyer() {
        return currentBuyer;
    }

    public static class buyerHasNotBought extends Exception {
        public buyerHasNotBought() {
            super("Buyer has not bought product");
        }
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
        currentBuyer.setImagePath(path);
        client.readMessage();
    }

    @Override
    public void changeMainImage(Image image) {
        Message message = new Message("changeMainImage");
        message.addToObjects(image);
        client.writeMessage(message);
        currentBuyer.getGraphicPackage().setMainImage(image);
        client.readMessage();
    }

    @Override
    public void logout() {
        client.writeMessage(new Message("logout"));
        mainController.logout();
        client.readMessage();
    }

    //todo complete connect to supporter
    public void connectToSupporter(String supporterUsername) throws Exception {
        Supporter supporter = (Supporter) Account.getAccountWithUsername(supporterUsername);

    }

    public void sendMessageToSupporter(String message) {

    }

    public String showSupporterMessage() {

        return "";
    }

}
