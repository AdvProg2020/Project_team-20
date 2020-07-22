package client.model.product;

import client.model.GraphicPackage;
import client.model.account.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    public ArrayList<SelectedProduct> selectedProducts;
    private GraphicPackage graphicPackage;

    public Cart() {
        this.selectedProducts = new ArrayList<>();
        this.graphicPackage = new GraphicPackage();
    }

    public void addProduct(Product product, Seller seller) {
        selectedProducts.add(new SelectedProduct(product, seller, 1));
    }

    public void increaseProduct(String id, int number) throws Exception {
        SelectedProduct selectedProduct = getSelectedProductByProductId(id);
        if (selectedProduct.getProduct() instanceof FileProduct)
            return;
        if (selectedProduct.getSeller().getProductCount(selectedProduct.getProduct()) < number)
            throw new notEnoughProductCountException();
        selectedProduct.increaseProduct(number);
    }

    public void decreaseProduct(String id, int number) throws Exception {
        SelectedProduct selectedProduct = getSelectedProductByProductId(id);
        if (selectedProduct.getProduct() instanceof FileProduct)
            return;
        if (selectedProduct.getCount() > number)
            selectedProduct.decreaseProduct(number);
        else
            selectedProducts.remove(selectedProduct);
    }

    public void resetCart() {
        ArrayList<SelectedProduct>  products = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                continue;
                products.add(selectedProduct);
        }
        selectedProducts.removeAll(products);
    }

    public void resetFileCart() {
        ArrayList<SelectedProduct>  products = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                products.add(selectedProduct);
        }
        selectedProducts.removeAll(products);
    }

    public SelectedProduct getSelectedProductByProductId(String id) throws Exception {
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(id)) {
                return selectedProduct;
            }
        }
        throw new ProductNotInCart();
    }

    public Product getProductById(String id) throws Exception {
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct().getId().equals(id)) {
                return selectedProduct.getProduct();
            }
        }
        throw new ProductNotInCart();
    }

    public ArrayList<SelectedProduct> getAllProductsOfSeller(Seller seller) {
        ArrayList<SelectedProduct> allSelectedProducts = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                continue;
            if (selectedProduct.getSeller().equals(seller))
                allSelectedProducts.add(selectedProduct);
        }
        return allSelectedProducts;
    }

    public ArrayList<SelectedProduct> getAllFileProductsOfSeller(Seller seller) {
        ArrayList<SelectedProduct> allSelectedProducts = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                if (selectedProduct.getSeller().equals(seller))
                    allSelectedProducts.add(selectedProduct);
        }
        return allSelectedProducts;
    }

    public HashMap<Product, Integer> getAllProducts() {
        HashMap<Product, Integer> allProduct = new HashMap<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                continue;
            allProduct.put(selectedProduct.getProduct(), selectedProduct.getCount());
        }
        return allProduct;
    }

    public HashMap<Product, Integer> getAllFileProducts() {
        HashMap<Product, Integer> allProduct = new HashMap<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                allProduct.put(selectedProduct.getProduct(), 1);
        }
        return allProduct;
    }

    public ArrayList<FileProduct> getAllFileProductsArrayList() {
        ArrayList<FileProduct> allProduct = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                allProduct.add((FileProduct) selectedProduct.getProduct());
        }
        return allProduct;
    }

    public HashMap<Product, Integer> getAllProductsSeller(Seller seller) {
        HashMap<Product, Integer> allProductSeller = new HashMap<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                continue;
            if (selectedProduct.getSeller().equals(seller))
                allProductSeller.put(selectedProduct.getProduct(), selectedProduct.getCount());
        }
        return allProductSeller;
    }

    public HashMap<Product, Integer> getAllFileProductsSeller(Seller seller) {
        HashMap<Product, Integer> allProductSeller = new HashMap<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                if (selectedProduct.getSeller().equals(seller))
                    allProductSeller.put(selectedProduct.getProduct(), selectedProduct.getCount());
        }
        return allProductSeller;
    }

    public ArrayList<Seller> getAllSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                continue;
            allSellers.add(selectedProduct.getSeller());
        }
        return allSellers;
    }

    public ArrayList<Seller> getAllFileSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (SelectedProduct selectedProduct : selectedProducts) {
            if (selectedProduct.getProduct() instanceof FileProduct)
                allSellers.add(selectedProduct.getSeller());
        }
        return allSellers;
    }

    public ArrayList<SelectedProduct> getSelectedProducts() {
        return selectedProducts;
    }


    public static class ProductNotInCart extends Exception {
        public ProductNotInCart() {
            super("Product is not in cart");
        }
    }

    public static class notEnoughProductCountException extends Exception {
        public notEnoughProductCountException() {
            super("Not enough Product count");
        }
    }

    public GraphicPackage getGraphicPackage() {
        return graphicPackage;
    }
}
