package server.controller.product.filter;

import client.model.product.Product;

import java.util.Comparator;

class CompareNumberOfViews implements Comparator<Product> {
    @Override
    public int compare(Product product, Product anotherProduct) {
        int numberOfViewsCompare = Double.compare(anotherProduct.getViews(), product.getViews());
        return numberOfViewsCompare;
    }
}
