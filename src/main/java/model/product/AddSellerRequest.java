package model.product;

import model.Requestable;
import model.account.Seller;

public class AddSellerRequest implements Requestable {
    private String productId;
    private String productName;
    private String sellerUsername;
    private int count;
    private double price;
    private RequestableState state;

    public AddSellerRequest(Product product, Seller seller, int count, double price) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.sellerUsername = seller.getUsername();
        this.count = count;
        this.price = price;
        this.state = RequestableState.CREATED;
    }

    @Override
    public void changeStateAccepted() {
        try {
            Product product = Product.getProductById(productId);
            Seller seller = Seller.getSellerWithUsername(sellerUsername);
            product.addSeller(seller, count, price);
            seller.addToProductsToSell(product, count);
            state = RequestableState.ACCEPTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            return Product.getProductById(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setProduct(Product product) {
        this.productId = product.getId();
    }

    public Seller getSeller() {
        try {
            return Seller.getSellerWithUsername(sellerUsername);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSeller(Seller seller) {
        this.sellerUsername = seller.getUsername();
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
        return RequestType.AddSellerRequest;
    }

    @Override
    public String toString() {
        String temp = "Seller wants to be added to this product: \n" + "\n";
        temp += "Seller username     : " + sellerUsername + "\n" + "\n";
        temp += "Product Id          : " + productId + "\n" + "\n";
        temp += "Name                : " + productName + "\n" + "\n";
        temp += "Count               : " + count + "\n" + "\n";
        temp += "Price               : " + price;
        return temp;
    }
}
