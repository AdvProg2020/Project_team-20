package controller.product.filter;

import controller.MainController;
import controller.account.user.SellerController;
import model.account.GeneralAccount;
import model.account.TempAccount;
import model.product.Category;
import model.product.Product;

import java.util.ArrayList;

public class AllProductsController implements Filterable {
    private String sortElement;
    MainController mainController;
    GeneralAccount generalAccount;

    public static AllProductsController allProductsController = null;

    private AllProductsController() {
        this.mainController = MainController.getInstance();
        generalAccount = mainController.getAccount();
    }
    
    public static AllProductsController getInstance() {
        if (allProductsController == null)
            return new AllProductsController();
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
