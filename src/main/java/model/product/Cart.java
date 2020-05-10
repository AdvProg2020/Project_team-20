package model.product;

import model.account.Buyer;
import model.account.Seller;

import java.util.ArrayList;

public class Cart {
    public ArrayList<SelectedProduct> selectedProducts;

    public Cart() {
        this.selectedProducts = new ArrayList<>();
    }

    public void addProduct(Product product, Seller seller)  {
        selectedProducts.add(new SelectedProduct(product, seller, 1));
    }

    public void increaseProduct(String productId, int number) throws Exception {
        for (SelectedProduct selectedProduct:selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(productId)) {
                selectedProduct.increaseProduct();
                return;
            }
        }
        throw new ProductNotInCart();
    }

    public void decreaseProduct(String productId, int number) throws Exception {
        for (SelectedProduct selectedProduct:selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(productId)) {
                if (selectedProduct.getCount()>1)
                    selectedProduct.decreaseProduct();
                else
                    selectedProducts.remove(selectedProduct);
                return;
            }
        }
        throw new ProductNotInCart();
    }

    public static class ProductNotInCart extends Exception {
        public ProductNotInCart() { super("product unavailable"); }
    }
}
