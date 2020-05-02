package model.product;

import java.util.HashMap;

public class Cart {
    public HashMap<Product, Integer> products;

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(String id) throws Exception {
        products.put(Product.getProductById(id), 1);
    }

    public void increaseProduct(String productId, int number) throws Exception {
        Product product = Product.getProductById(productId);
        if(products.containsKey(product)) {
            int currentNumber = products.get(product);
            products.replace(product, currentNumber + number);
        }
        throw new ProductNotInCart();
    }

    public static class ProductNotInCart extends Exception {
        public ProductNotInCart() { super("product unavailable"); }
    }
}
