package model.filter;

import model.product.*;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;

public class RangeFilter extends Filter{
    private double min;
    private double max;

    public RangeFilter(String name, double min, double max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    public RangeFilter(String name) {
        super(name);
        this.min = -1;
        this.max = -1;
    }

    @Override
    public boolean validFilter(Product product) {
        for (Field field:product.getGeneralFields()) {
            if (field.getName().equals(name) && field.getType().equals(FieldType.NUMERICAL)) {
                return validNumericalFilter(((NumericalField)field).getNumericalField());
            }
        }
        return false;
    }

    public boolean validNumericalFilter(double numericalField){
        return (numericalField < max && numericalField > min) || (max==-1 && min==-1);
    }

    public double getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
