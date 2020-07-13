package client.controller.product;

import client.model.product.Product;
import client.model.receipt.Receipt;
import client.network.Client;
import client.network.Message;

import java.util.HashMap;

public class ReceiptController {

    Client client;

    public ReceiptController() {
        client = new Client(500);
        client.readMessage();
    }

    public HashMap<Product, Integer> getProducts(Receipt receipt) {
        Message message = new Message("getProducts");
        message.addToObjects(receipt);
        client.writeMessage(message);
        return (HashMap<Product, Integer>) client.readMessage().getObjects().get(0);

    }

}
