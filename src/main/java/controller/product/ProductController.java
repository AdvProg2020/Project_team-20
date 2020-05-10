package controller.product;

import controller.MainController;
import model.product.Product;

public class ProductController {
    private Product currentProduct;
    MainController mainController;

    private static ProductController productController = null;

    private ProductController(Product product) {
        this.currentProduct = product;
        mainController = MainController.getInstance();
    }

    private void addProductToCart (String sellerId) {

    }

    public static ProductController getInstance(Product product) {
        if (productController == null)
            productController = new ProductController(product);
        return productController;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }
}
