package controller.product;

import model.product.Product;

public class ProductController {
    private Product currentProduct;

    private static ProductController productController = null;

    private ProductController(Product product) {
        this.currentProduct = product;
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
