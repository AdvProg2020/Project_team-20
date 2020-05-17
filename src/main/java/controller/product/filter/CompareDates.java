package controller.product.filter;

import model.product.Product;

import java.time.LocalDateTime;
import java.util.Comparator;


class  CompareDates implements Comparator<Product> {
    @Override
    public int compare(Product date1, Product date2) {
        return date2.getAddingDate().compareTo(date1.getAddingDate());
    }
}
