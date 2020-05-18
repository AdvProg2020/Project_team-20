package controller.product.filter;

import model.product.Product;

import java.util.Comparator;

class CompareScores implements Comparator<Product> {
    @Override
    public int compare(Product product, Product anotherProduct) {
        int scoresCompare = Double.compare(anotherProduct.getAverage(), product.getAverage());
        return scoresCompare;
    }
}

