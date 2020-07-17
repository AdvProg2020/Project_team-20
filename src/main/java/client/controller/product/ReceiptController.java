package client.controller.product;

import client.model.product.Product;
import client.model.receipt.Receipt;
import client.network.Client;
import client.network.Message;

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
        client.disconnect();
        return (HashMap<Product, Integer>) client.readMessage().getObjects().get(0);

    }

    private void connect() {
        client = new Client(500);
        client.readMessage();
    }

}
