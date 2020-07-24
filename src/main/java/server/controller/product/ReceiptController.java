package server.controller.product;

import client.model.account.Seller;
import client.model.receipt.BuyerReceipt;
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
        methods.add("getSellers");
    }

    public Message getProducts(Receipt receipt) {
        Message message = new Message("get products");
        message.addToObjects(receipt.getProducts());
        return message;
    }

    public Message getSellers(BuyerReceipt receipt) {
        Message message = new Message("get sellers");
        message.addToObjects(receipt.getSellers());
        return message;
    }
}
