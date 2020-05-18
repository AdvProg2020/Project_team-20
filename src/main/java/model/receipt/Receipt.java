package model.receipt;
import model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Receipt {
    protected String id;
    protected double discountPercentage;
    protected HashMap<String, Integer> productsWithIds;
    protected Boolean hasItSent;
    protected LocalDateTime dateAndTime;

    Receipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent) {
        this.id = id;
        this.discountPercentage = discountPercentage;
        addToProducts(products);
        this.hasItSent = hasItSent;
        dateAndTime = LocalDateTime.now();
    }

    public void addToProducts(HashMap<Product, Integer> products){
        HashMap<String , Integer> productName = new HashMap<>();
        for(Product product : products.keySet()){
            productName.put(product.getId(),products.get(product));
        }
        this.productsWithIds = productName;
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
        HashMap<Product,Integer> products = new HashMap<>();
        for(String name : productsWithIds.keySet()){
            try {
                Product product = Product.getProductById(name);
                products.put(product,productsWithIds.get(name));
            }
            catch (Exception e){
            }
        }
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
