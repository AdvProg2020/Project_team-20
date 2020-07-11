package server.controller.product.filter;

import server.controller.MainController;
import client.model.account.GeneralAccount;
import client.model.account.TempAccount;
import client.model.product.Advertisement;
import client.model.product.category.Category;
import client.model.product.category.SubCategory;
import client.model.product.Product;
import client.model.product.category.CategorySet;

import java.util.ArrayList;

public class AllProductsController extends Filterable {
    MainController mainController;
    GeneralAccount generalAccount;

    public static AllProductsController allProductsController = null;

    private AllProductsController() {
        this.mainController = MainController.getInstance();
        generalAccount = mainController.getAccount();
        productsToShow = Product.getAllProducts();
    }

    public static AllProductsController getInstance() {
        if (allProductsController == null)
            allProductsController = new AllProductsController();
        return allProductsController;
    }

    public void purchase() throws Exception {
        if (generalAccount instanceof TempAccount)
            throw new NotLoggedInException();
        else
            throw new InvalidCommandException();
    }

    public ArrayList<SubCategory> getAllSubCategories() {
        return SubCategory.getAllSubCategories();
    }

    public ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<CategorySet> getAllCategorySets() {
        return CategorySet.getAllCategorySets();
    }

    public ArrayList<Product> getProductsToShow() {
        return productsToShow;
    }

    public static class NotLoggedInException extends Exception {
        public NotLoggedInException() {
            super("You have not logged in. Please go to LoginMenu and log in!:)");
        }
    }

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("invalid command");
        }
    }

    public Product getProduct(String id) throws Exception {
        Product product = Product.getProductById(id);
        product.addToNumberOfViews();
        return product;
    }

    public ArrayList<Advertisement> getAdvertisement() {
        return Advertisement.getAdds();
    }

    public void disableCategoryFields() throws Exception {
        disableFilterByCategory();
    }
}
