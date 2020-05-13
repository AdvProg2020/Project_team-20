package controller.product.filter;

import controller.MainController;
import model.account.GeneralAccount;
import model.account.TempAccount;
import model.product.Category;
import model.product.Product;

import java.util.ArrayList;

public class AllProductsController implements Filterable {
    private String sortElement;
    MainController mainController;
    GeneralAccount generalAccount;

    public AllProductsController(MainController mainController) {
        this.mainController = mainController;
        generalAccount = mainController.getAccount();
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

    public ArrayList<Product> getAllProducts() {
        return Product.getAllProducts();
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
