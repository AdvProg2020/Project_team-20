package model.product;

import model.account.Buyer;

public class SelectedProduct {
    private Product product;
    private Buyer buyer;
    private int count;

    public SelectedProduct(Product product, Buyer buyer, int count) {
        this.product = product;
        this.buyer = buyer;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public int getCount() {
        return count;
    }
}
