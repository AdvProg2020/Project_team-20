package model.filter;

import model.product.Product;

public interface Filter {
    public boolean validFilter(Product product);
}
