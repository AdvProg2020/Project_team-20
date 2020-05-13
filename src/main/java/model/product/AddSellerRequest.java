package model.product;

import model.Requestable;
import model.account.Seller;

public class AddSellerRequest implements Requestable {
    private Product product;
    private Seller seller;
    private int count;
    private double price;

    public AddSellerRequest(Product product, Seller seller, int count, double price) {
        this.product = product;
        this.seller = seller;
        this.count = count;
        this.price = price;
    }

    @Override
    public void changeStateAccepted() {
        product.addSeller(seller, count, price);
    }

    @Override
    public void changeStateRejected() {
    }

    @Override
    public void edit() {

    }

    @Override
    public RequestableState getState() {
        return null;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
