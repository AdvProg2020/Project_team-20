package controller.product.filter;

import model.filter.Filter;
import model.filter.NameFilter;
import model.filter.OptionalFilter;
import model.filter.RangeFilter;
import model.product.Category;
import model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static model.product.Product.getProductByAddingDate;

public abstract class Filterable {
    protected ArrayList<Filter> filters = new ArrayList<>();
    protected ArrayList<String> filterNames = new ArrayList<>();
    protected String currentSort = "ByNumberOfViews";
    protected ArrayList<Product> productsToShow;

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

    public void disAbleFilter(String filterName) throws Exception{
        Filter filter = getFilterByName(filterName);
        if (filter==null)
            throw new FilterNotFoundException();
        filters.remove(filter);
        filterNames.remove(filter.getName());
    }

    public ArrayList<Product> getProducts() {
        return productsToShow.stream()
                .filter( product -> {
                    for (Filter filter:filters)
                        if (!filter.validFilter(product))
                            return false;
                    return true;})
                .collect(Collectors
                .toCollection(ArrayList::new));
    }

    public ArrayList<Product> showProducts() throws Exception{
        switch (currentSort){
            case "ByScores":
                return sortByScores();
            case "ByDates":
                return sortByAddingDate();
            case "ByNumberOfViews":
                return sortByNUmberOfViews();
        }
        throw new SortNotFoundException();
    }

    public void changeSort(String newSort) throws Exception {
        this.currentSort = newSort;
        showProducts();
    }

    public void disableSort(){
        currentSort = null;
    }

    public void filterByCategory(ArrayList<String> details) throws Exception{
        String categoryName = details.get(0);
        Category category = Category.getCategoryByName(categoryName);
        addAllFieldsCategory(category);
    }

    public void addAllFieldsCategory(Category category) {
        for (String fieldName:category.getFields()) {
            if (!filterNames.contains(fieldName)) {
                filterNames.add(fieldName);
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

    private Filter getFilterByName(String name) {
        for (Filter filter:filters) {
            if (filter.getName().equals(name))
                return filter;
        }
        return null;
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

   private ArrayList<Product> sortByNUmberOfViews(){
        ArrayList<Product> products = getProducts();
        products.sort(new CompareNumberOfViews());
        return products;
    }

    public static class FilterNotFoundException extends Exception {
        public FilterNotFoundException() {
            super("Filter name is not correct.");
        }
    }

    public static class SortNotFoundException extends Exception {
        public SortNotFoundException() {
            super("you didn't select any sortType");
        }
    }

    private ArrayList<Product> sortByScores(){
        ArrayList<Product> products = getProducts();
        products.sort(new CompareScores());
        return products;
    }

    private ArrayList<Product> sortByAddingDate(){
        ArrayList<Product> products = getProducts();
        products.sort(new CompareDates());
        return products;
    }

    public String getCurrentSort() {
        return currentSort;
    }
}
