package model.product.Field;

public abstract class Field {
    protected String name;
    protected FieldType type;

    public Field(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }
}
