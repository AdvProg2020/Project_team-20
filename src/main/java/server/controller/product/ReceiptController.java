package server.controller.product;

import client.model.receipt.Receipt;
import client.network.Message;
import server.network.server.Server;

public class ReceiptController extends Server {


    public ReceiptController() {
        super(500);
        setMethods();
        System.out.println("Receipt controller run");
    }

    @Override
    protected void setMethods() {
        methods.add("getProducts");
    }

    public Message getProducts(Receipt receipt) {
        Message message = new Message("get products");
        message.addToObjects(receipt.getProducts());
        return message;
    }
}
