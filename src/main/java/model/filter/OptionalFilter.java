package model.filter;

import model.product.Field;
import model.product.FieldType;
import model.product.OptionalField;
import model.product.Product;

import java.util.ArrayList;

public class OptionalFilter extends Filter{
    private ArrayList<String> options;

    public OptionalFilter(String name, ArrayList<String> options) {
        super(name);
        this.options = options;
    }

    @Override
    public boolean validFilter(Product product) {
        for (Field field:product.getGeneralFields()) {
            if (field.getName().equals(name) && field.getType().equals(FieldType.OPTIONAL)) {
                return validOptionalFilter(((OptionalField)field).getOptionalFiled());
            }
        }
        return false;
    }

    public boolean validOptionalFilter(ArrayList<String> allFields){
        for (String option:options) {
            for (String field:allFields) {
                if (option.equals(field))
                    return true;
            }
        }
        return false;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
