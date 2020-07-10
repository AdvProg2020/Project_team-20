package server.model.filter;

import server.model.product.*;
import server.model.product.Field.Field;
import server.model.product.Field.FieldType;
import server.model.product.Field.NumericalField;

public class RangeFilter extends Filter {
    private double min;
    private double max;

    public RangeFilter(String name, double min, double max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validFilter(Product product) {
        for (Field field : product.getGeneralFields()) {
            if (field.getName().equals(name) && field.getType().equals(FieldType.NUMERICAL)) {
                return validNumericalFilter(((NumericalField) field).getNumericalField());
            }
        }
        return false;
    }

    public boolean validNumericalFilter(double numericalField) {
        return (numericalField < max && numericalField > min);
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
