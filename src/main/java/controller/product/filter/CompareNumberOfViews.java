package controller.product.filter;

import model.product.Product;

import java.util.Comparator;

class CompareNumberOfViews implements Comparator<Product> {
    @Override
    public int compare(Product product, Product anotherProduct) {
        int numberOfViewsCompare = Double.compare(anotherProduct.getNumberVisited(), product.getNumberVisited());
        return numberOfViewsCompare;
    }
}
