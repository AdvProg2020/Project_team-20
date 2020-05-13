package view.product;

import controller.product.filter.AllProductsController;
import model.product.Category;
import model.product.Product;
import model.product.SelectedProduct;
import view.Menu;
import view.account.BuyerMenu;

import java.util.ArrayList;

public class AllProductsMenu extends Menu {
    AllProductsController allProductsController;

   private static AllProductsMenu allProductsMenu = null;

    private AllProductsMenu() {
        super("allProductsMenu");
        allProductsController = AllProductsController.getInstance();
    }

    public static AllProductsMenu getInstance() {
        if (allProductsMenu == null)
            allProductsMenu = new AllProductsMenu();
        return allProductsMenu;
    }

    //NOT SURE!!!!!!!!!!
    public void viewCategories() {
        ArrayList<Category> categories = allProductsController.getAllCategories();
        System.out.println("All categories:");
        for (Category category:categories)
            System.out.println(category.getName());
    }

    public void showProducts() {
        ArrayList<Product> products = allProductsController.getAllProducts();
        for (Product product:products) {
            System.out.println("Name                id\n");
            System.out.format("%s%20s", product.getName()
                    , product.getId());
        }
    }

    public void showProduct(String id) {
        try {
            Product product = Product.getProductById(id);
            ProductMenu productMenu = new ProductMenu(product);
            enter(productMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void setRegex() {
        regex.add("view categories");
        regex.add("show products");
        regex.add("show products (\\w+)");
    }

    private void setMethods() {
        methods.add("viewCategories");
        methods.add("showProducts");
        methods.add("showProducts");
    }

}
