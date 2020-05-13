package view.product;

import controller.product.ProductController;
import model.product.Product;
import view.Menu;

public class ProductMenu extends Menu {
    ProductController productController;

    public ProductMenu(Product product) {
        super("ProductMenu");
        productController = ProductController.getInstance(product);
    }
}
