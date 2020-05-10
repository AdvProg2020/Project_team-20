package model.product.Field;

public class NumericalField extends Field{
    private int numericalField;

    public NumericalField(String name, FieldType type, int numericalField) {
        super(name, type);
        this.numericalField = numericalField;
    }

    public int getNumericalField() {
        return numericalField;
    }
}
