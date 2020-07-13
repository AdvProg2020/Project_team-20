package server.controller.product;

import client.model.account.Seller;
import client.model.product.Product;
import client.network.Message;
import server.network.server.Server;

public class AdvertisementController extends Server {

    public AdvertisementController() {
        super(696);
        setMethods();
        System.out.println("Advertisement controller run");
    }

    public Message getSeller(String sellerId) {
        Message message = new Message("seller");
        try {
            message.addToObjects(Seller.getSellerWithUsername(sellerId));
        } catch (Exception e) {
            message.addToObjects(null);
        }
        return message;
    }

    public Message getProduct(String productId) {
        Message message = new Message("product");
        try {
            message.addToObjects(Product.getProductById(productId));
        } catch (Exception e) {
            message.addToObjects(null);
        }
        return message;
    }

    @Override
    protected void setMethods() {
        methods.add("getSeller");
        methods.add("getProduct");
    }
}
