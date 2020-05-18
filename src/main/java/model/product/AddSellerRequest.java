package model.product;

import model.Requestable;
import model.account.Seller;

public class AddSellerRequest implements Requestable {
    private Product product;
    private Seller seller;
    private int count;
    private double price;
    private RequestableState state;
    private RequestType requestType = RequestType.AddSellerRequest;

    public AddSellerRequest(Product product, Seller seller, int count, double price) {
        this.product = product;
        this.seller = seller;
        this.count = count;
        this.price = price;
        this.state = RequestableState.CREATED;
    }

    @Override
    public void changeStateAccepted() {
        product.addSeller(seller, count, price);
        seller.addToProductsToSell(product, count);
        state = RequestableState.ACCEPTED;
    }

    @Override
    public void changeStateRejected() {
    }

    @Override
    public void edit() {

    }

    @Override
    public RequestableState getState() {
        return state;
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

    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        String temp = "Seller wants to be added to this product: \n";
        temp += "Seller username     : " + seller.getUsername() + "\n";
        temp += "Product Id          : " + product.getId() + "\n";
        temp += "Name                : " + product.getName() + "\n";
        temp += "Count               : " + count + "\n";
        temp += "Price               : " + price;
        return temp;
    }
}
