package controller.product.filter;

import model.filter.Filter;
import model.filter.NameFilter;
import model.filter.OptionalFilter;
import model.filter.RangeFilter;
import model.product.Category;
import model.product.Field.NumericalField;
import model.product.Product;

import java.util.ArrayList;

public abstract class Filterable {
    protected ArrayList<Filter> filters = new ArrayList<>();
    protected ArrayList<String> categoryFilters = new ArrayList<>();

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

    public void filterByCategory(ArrayList<String> details) throws Exception{
        String categoryName = details.get(0);
        Category category = Category.getCategoryByName(categoryName);
        addAllFieldsCategory(category);
    }

    public void addAllFieldsCategory(Category category) {
        for (String fieldName:category.getFields()) {
            if (!categoryFilters.contains(fieldName)) {
                categoryFilters.add(fieldName);
                filters.add(new OptionalFilter(fieldName));
            }
        }
        for (Category subCategory:category.getSubCategories()) {
            addAllFieldsCategory(subCategory);
        }
    }

    public void filterByProductName(ArrayList<String> details) {
        filters.add(new NameFilter(details.get(0)));
    }

    public void filterByOptionalFilter(ArrayList<String> details) {
        String name = details.get(0);
        details.remove(0);
        filters.add(new OptionalFilter(name, details));
    }

    public void filterByNumericalFilter(ArrayList<String> details) {
        filters.add(new RangeFilter(details.get(0), Double.parseDouble(details.get(1)), Double.parseDouble(details.get(2))));
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    public ArrayList<Product> sortByNUmberOfViews(ArrayList<Product> products){
       products.sort(new CompareNumberOfViews());
       return products;
    }

    public ArrayList<Product> sortByScores(ArrayList<Product> products){
        products.sort(new CompareScores());
        return products;
    }

}
