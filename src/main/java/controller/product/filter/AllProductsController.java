package controller.product.filter;

import controller.MainController;
import model.account.GeneralAccount;
import model.account.TempAccount;
import model.product.Category;
import model.product.Product;

import java.util.ArrayList;

public class AllProductsController extends Filterable {
    private String sortElement;
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
            allProductsController =  new AllProductsController();
        return allProductsController;
    }

    public void purchase() throws Exception{
        if (generalAccount instanceof TempAccount)
            throw new NotLoggedInException();
        else
            throw new InvalidCommandException();
    }

    public ArrayList<Category> getAllCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<Product> getProductsToShow() {
        return productsToShow;
    }

    public static class NotLoggedInException extends Exception{
        public NotLoggedInException() {
            super("not logged in");
        }
    }

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("invalid command");
        }
    }
}
