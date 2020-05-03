package model.filter;

import model.product.*;

public class RangeFilter extends Filter{
    private int min;
    private int max;

    public RangeFilter(String name, int min, int max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validFilter(Product product) {
        for (Field field:product.getGeneralFields()) {
            if (field.getName().equals(name) && field.getType().equals(FieldType.Numerical)) {
                return validOptionalFilter(((NumericalField)field).getNumericalField());
            }
        }
        return false;
    }

    public boolean validOptionalFilter(int numericalField){
        return numericalField < max && numericalField > min;
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
}
