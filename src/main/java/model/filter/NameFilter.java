package model.filter;

import model.product.Product;

public class NameFilter extends Filter {
    public NameFilter(String name) {
        super(name);
    }

    @Override
    public boolean validFilter(Product product) {
        return product.getName().equalsIgnoreCase(name);
    }
}
