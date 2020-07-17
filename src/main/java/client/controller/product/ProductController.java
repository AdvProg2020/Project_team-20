package client.controller.product;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.account.Seller;
import client.model.product.Product;
import client.model.product.comment.Comment;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class ProductController {
    private Product currentProduct;
    private MainController mainController;
    private Client client;

    public ProductController(Product product) {
        this.currentProduct = product;
        mainController = MainController.getInstance();
    }

    private void connectWithTempAccount() {
        Message message = new Message("connectWithTempAccount");
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.setAuthToken(answer.getAuthToken());
    }

    private void connect() {
        if (LoginController.getClient() == null) {
            client = new Client(1000);
            client.readMessage();
            connectWithTempAccount();
        } else {
            client = new Client(1000);
            client.readMessage();
            client.setAuthToken(LoginController.getClient().getAuthToken());
        }
    }

    public Seller getSellerByUsername(String sellerId) throws Exception {
        connect();
        Message message = new Message("getSellerByUsername");
        message.addToObjects(currentProduct.getId());
        message.addToObjects(sellerId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Seller) answer.getObjects().get(0);
    }

    public double getFirstPrice() {
        try {
            return currentProduct.getPrice(getSellerByUsername(currentProduct.getSellerUsernames().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addProductToCart(String sellerId) throws Exception {
        connect();
        Message message = new Message("addProductToCart");
        message.addToObjects(sellerId);
        message.addToObjects(currentProduct.getId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addComment(Product product, String title, String content) throws Exception {
        connect();
        Message message = new Message("addComment");
        message.addToObjects(product.getId());
        message.addToObjects(title);
        message.addToObjects(content);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addScore(double score, Product product) throws Exception {
        connect();
        Message message = new Message("addScore");
        message.addToObjects(score);
        message.addToObjects(product.getId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addReplyToComment(Comment comment, String title, String content) throws Exception {
        connect();
        Message message = new Message("addReplyToComment");
        message.addToObjects(comment);
        message.addToObjects(title);
        message.addToObjects(content);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public Product getAnotherProduct(String id, ArrayList<String> othersIds) throws Exception {
        connect();
        Message message = new Message("getAnotherProduct");
        message.addToObjects(id);
        message.addToObjects(othersIds);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Product) answer.getObjects().get(0);
    }

    public ArrayList<Seller> getSellers() {
        connect();
        Message message = new Message("getSellers");
        message.addToObjects(currentProduct.getId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        return (ArrayList<Seller>) answer.getObjects().get(0);
    }


}
