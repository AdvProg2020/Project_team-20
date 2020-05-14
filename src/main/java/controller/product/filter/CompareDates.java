package controller.product.filter;

import model.product.Product;

import java.time.LocalDateTime;
import java.util.Comparator;


class  CompareDates implements Comparator<Product> {
    @Override
    public int compare(Product product, Product anotherProduct) {
        return anotherProduct.getAddingDate().compareTo(product.getAddingDate());
    }
}
