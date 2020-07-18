package client.model.product;

import client.model.account.Seller;

public class SelectedProduct {
    private Product product;
    private Seller seller;
    private int count;

    public SelectedProduct(Product product, Seller seller, int count) {
        this.product = product;
        this.seller = seller;
        this.count = count;
    }

    public void increaseProduct(int number) {
        count += number;
    }

    public void decreaseProduct(int number) {
        count -= number;
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

    public boolean isSold() {
        return product.isSold(seller.getUsername());
    }
}
