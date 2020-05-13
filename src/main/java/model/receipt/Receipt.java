package model.receipt;
import model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Receipt {
    protected String id;
    protected double discountPercentage;
    protected HashMap<Product, Integer> products;
    protected Boolean hasItSent;
    protected LocalDateTime dateAndTime;

    Receipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent) {
        this.id = id;
        this.discountPercentage = discountPercentage;
        this.products = products;
        this.hasItSent = hasItSent;
        dateAndTime = LocalDateTime.now();
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getId() {
        return id;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public Boolean getHasItSent() {
        return hasItSent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setHasItSent(Boolean hasItSent) {
        this.hasItSent = hasItSent;
    }
}
