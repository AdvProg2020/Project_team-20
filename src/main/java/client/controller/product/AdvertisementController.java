package client.controller.product;

import client.model.account.Seller;
import client.model.product.Advertisement;
import client.model.product.Product;
import client.network.Client;
import client.network.Message;

public class AdvertisementController {
    private Advertisement advertisement;
    private Client client;

    public AdvertisementController(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Seller getSeller() {
        connect();
        Message message = new Message("getSeller");
        message.addToObjects(advertisement.getSellerId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.writeMessage(new Message("buy"));
        return (Seller) answer.getObjects().get(0);
    }

    public Product getProduct() {
        connect();
        Message message = new Message("getProduct");
        message.addToObjects(advertisement.getProductId());
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.writeMessage(new Message("buy"));
        return (Product) answer.getObjects().get(0);
    }

    private void connect() {
        client = new Client(696);
        client.readMessage();
    }

}
