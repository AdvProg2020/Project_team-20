package model.product;

import model.account.Buyer;
import model.account.Seller;

public class SelectedProduct {
    private Product product;
    private Seller seller;
    private int count;

    public SelectedProduct(Product product, Seller seller, int count) {
        this.product = product;
        this.seller = seller;
        this.count = count;
    }

    public void increaseProduct() {
        count += 1;
    }

    public void decreaseProduct() {
        count -= 1;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getCount() {
        return count;
    }
}
