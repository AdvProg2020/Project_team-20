package client.controller.product;

import client.model.account.Seller;
import client.model.product.Product;
import client.model.receipt.BuyerReceipt;
import client.model.receipt.Receipt;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptController {

    Client client;

    public ReceiptController() {
    }

    public HashMap<Product, Integer> getProducts(Receipt receipt) {
        connect();
        Message message = new Message("getProducts");
        message.addToObjects(receipt);
        client.writeMessage(message);
        HashMap<Product, Integer> productsReceipt = (HashMap<Product, Integer>) client.readMessage().getObjects().get(0);
        client.disconnect();
        return productsReceipt;
    }

    public ArrayList<Seller> getSellers(BuyerReceipt receipt) {
        connect();
        Message message = new Message("getSellers");
        message.addToObjects(receipt);
        client.writeMessage(message);
        ArrayList<Seller> sellersReceipt = (ArrayList<Seller>) client.readMessage().getObjects().get(0);
        client.disconnect();
        return sellersReceipt;
    }

    private void connect() {
        client = new Client(500);
        client.readMessage();
    }

}
