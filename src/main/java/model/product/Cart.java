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

    public void increaseProduct(String id, int number) throws Exception {
        SelectedProduct selectedProduct = getSelectedProductByProductId(id);
        selectedProduct.increaseProduct(number);
    }

    public void decreaseProduct(String id, int number) throws Exception {
        SelectedProduct selectedProduct = getSelectedProductByProductId(id);
        if (selectedProduct.getCount()>number)
            selectedProduct.decreaseProduct(number);
        else
            selectedProducts.remove(selectedProduct);
    }

    public SelectedProduct getSelectedProductByProductId(String id) throws Exception{
        for (SelectedProduct selectedProduct:selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(id)) {
                return selectedProduct;
            }
        }
        throw new ProductNotInCart();
    }

    public Product getProductById(String id) throws Exception{
        for (SelectedProduct selectedProduct:selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(id)) {
                return selectedProduct.getProduct();
            }
        }
        throw new ProductNotInCart();
    }

    public static class ProductNotInCart extends Exception {
        public ProductNotInCart() { super("product unavailable"); }
    }
}
