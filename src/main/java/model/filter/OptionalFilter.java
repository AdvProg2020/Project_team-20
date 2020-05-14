package model.filter;

import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.OptionalField;
import model.product.Product;

import java.util.ArrayList;

public class OptionalFilter extends Filter{
    private ArrayList<String> options;

    public OptionalFilter(String name, ArrayList<String> options) {
        super(name);
        this.options = options;
    }

    public OptionalFilter(String name) {
        super(name);
        this.options = null;
    }

    @Override
    public boolean validFilter(Product product) {
        for (Field field:product.getGeneralFields()) {
            if (field.getName().equals(name) && field.getType().equals(FieldType.OPTIONAL)) {
                if (options==null)
                    return true;
                return validOptionalFilter(((OptionalField)field).getOptionalFiled());
            }
        }
        return false;
    }

    public boolean validOptionalFilter(ArrayList<String> allFields){
        for (String option:options) {
            if (!allFields.contains(option))
                return false;
        }
        return true;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
