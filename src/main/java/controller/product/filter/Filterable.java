package controller.product.filter;

import model.filter.Filter;
import model.filter.OptionalFilter;
import model.filter.RangeFilter;
import model.product.Field.NumericalField;
import model.product.Product;

import java.util.ArrayList;

public abstract class Filterable {
    protected ArrayList<Filter> filters = new ArrayList<>();

    public void filter(String filterType, ArrayList<String> details) throws Exception{
        if (filterType.equalsIgnoreCase("category")) {
            filterByCategory(details);
        }
        else if (filterType.equalsIgnoreCase("product name")) {
            filterByProductName(details);
        }
        else if (filterType.equalsIgnoreCase("optional field")) {
            filterByOptionalFilter(details);
        }
        else if (filterType.equalsIgnoreCase("numerical field")) {
            filterByNumericalFilter(details);
        }
    }

    public void filterByCategory(ArrayList<String> details) {

    }

    public void filterByProductName(ArrayList<String> details) {
        filters.add(new OptionalFilter(details.get(0)));
    }

    public void filterByOptionalFilter(ArrayList<String> details) {
        String name = details.get(0);
        details.remove(0);
        filters.add(new OptionalFilter(name, details));
    }

    public void filterByNumericalFilter(ArrayList<String> details) {
        filters.add(new RangeFilter(details.get(0), Double.parseDouble(details.get(1)), Double.parseDouble(details.get(2))))
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public ArrayList<Product> SortByNUmberOfViews(ArrayList<Product> products){

    }
}
