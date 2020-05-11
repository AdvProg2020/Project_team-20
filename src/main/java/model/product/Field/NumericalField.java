package model.product.Field;

public class NumericalField extends Field{
    private double numericalField;

    public NumericalField(String name, FieldType type, double numericalField) {
        super(name, type);
        this.numericalField = numericalField;
    }

    public double getNumericalField() {
        return numericalField;
    }
}
