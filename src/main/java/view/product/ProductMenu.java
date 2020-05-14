package view.product;

import controller.product.ProductController;
import model.product.Product;
import view.Menu;

public class ProductMenu extends Menu {
    ProductController productController;

    public ProductMenu(Product product) {
        super("ProductMenu");
        productController = ProductController.getInstance(product);
        this.methods.add("enter");
        this.methods.add("back");
        this.methods.add("help");
        this.regex.add("enter [RegistrationMenu|AllProductsMenu]");
        this.regex.add("back");
        this.regex.add("help");
    }


}
