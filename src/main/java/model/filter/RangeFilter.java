package model.filter;

import model.product.Product;

public class RangeFilter implements Filter{
    private int min;
    private int max;

    public RangeFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean validFilter(Product product) {
        return false;
    }
}
