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
        if (selectedProduct.getSeller().getProductCount(selectedProduct.getProduct())<number)
            throw new notEnoughProductCountException();
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

    public ArrayList<SelectedProduct> getAllProductsOfSeller(Seller seller) {
        ArrayList<SelectedProduct> allSelectedProducts= new ArrayList<>();
        for (SelectedProduct selectedProduct:selectedProducts) {
            if (selectedProduct.getSeller().equals(seller))
                allSelectedProducts.add(selectedProduct);
        }
        return allSelectedProducts;
    }

    public ArrayList<Seller> getAllSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (SelectedProduct selectedProduct:selectedProducts) {
            allSellers.add(selectedProduct.getSeller());
        }
        return allSellers;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> allProducts = new ArrayList<>();
        for (SelectedProduct selectedProduct:selectedProducts) {
            allProducts.add(selectedProduct.getProduct());
        }
        return allProducts;
    }

    public ArrayList<SelectedProduct> getSelectedProducts() {
        return selectedProducts;
    }

    public static class ProductNotInCart extends Exception {
        public ProductNotInCart() { super("Product unavailable"); }
    }

    public static class notEnoughProductCountException extends Exception {
        public notEnoughProductCountException() {
            super("Not enough Product count");
        }
    }
}
