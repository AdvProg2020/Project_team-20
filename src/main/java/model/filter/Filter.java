package model.filter;

import model.product.Product;

public abstract class Filter {
    protected String name;

    public Filter(String name) {
        this.name = name;
    }

    public abstract boolean validFilter(Product product);

    public String getName() {
        return name;
    }
}
