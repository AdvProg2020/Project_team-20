package client.model.product.Field;

import java.util.ArrayList;

public class OptionalField extends Field {
    private ArrayList<String> optionalFiled;

    public OptionalField(String name, FieldType type, ArrayList<String> optionalFiled) {
        super(name, type);
        this.optionalFiled = optionalFiled;
    }

    public ArrayList<String> getOptionalFiled() {
        return optionalFiled;
    }
}
