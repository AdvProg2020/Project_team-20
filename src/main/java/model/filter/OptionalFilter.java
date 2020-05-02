package model.filter;

import model.product.Product;

import java.util.ArrayList;

public class OptionalFilter implements Filter{
    private ArrayList<String> options;

    public OptionalFilter() {
        this.options = new ArrayList<>();
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    @Override
    public boolean validFilter(Product product) {
        return false;
    }
}
