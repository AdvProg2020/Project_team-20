package server.controller.product.filter;

import server.model.filter.Filter;
import server.model.filter.NameFilter;
import server.model.filter.OptionalFilter;
import server.model.filter.RangeFilter;
import server.model.product.category.Category;
import server.model.product.Product;

import java.util.ArrayList;
import java.util.stream.Collectors;


public abstract class Filterable {
    protected ArrayList<Filter> filters = new ArrayList<>();
    protected ArrayList<String> filterNames = new ArrayList<>();
    protected String currentSort = "ByNumberOfViews";
    protected ArrayList<Product> productsToShow;
    protected ArrayList<Product> productsBeforeFiltering;

    public void filter(String filterType, ArrayList<String> details) throws Exception {
        if (filterType.equalsIgnoreCase("category")) {
            filterByCategory(details);
        } else if (filterType.equalsIgnoreCase("product name")) {
            filterByProductName(details);
        } else if (filterType.equalsIgnoreCase("optional field")) {
            filterByOptionalFilter(details);
        } else if (filterType.equalsIgnoreCase("numerical field")) {
            filterByNumericalFilter(details);
        }
    }

    public void disAbleFilter(String filterName) throws Exception {
        Filter filter = getFilterByName(filterName);
        if (filter == null)
            throw new FilterNotFoundException();
        filters.remove(filter);
        filterNames.remove(filter.getName());
    }

    public ArrayList<Product> getProducts() {
        return productsToShow.stream()
                .filter(product -> {
                    for (Filter filter : filters)
                        if (!filter.validFilter(product))
                            return false;
                    return true;
                })
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }

    public ArrayList<Product> getProductFilterByCategory(){
        return productsToShow;
    }

    public ArrayList<Product> showProducts() throws Exception {
        switch (currentSort) {
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

    public void disableSort() {
        currentSort = null;
    }

    public void disableFilterByCategory(){
        productsToShow = productsBeforeFiltering;
    }

    public void filterByCategory(ArrayList<String> details) throws Exception {
        String name = details.get(0);
        productsBeforeFiltering = productsToShow;
        ArrayList<Category> categories = Category.getAllCategories();
        Category category1;
        for (Category category : categories){
            if(category.getName().equals(name)){
                category1 = category;
                productsToShow = category1.getProducts();
                break;
            }
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
        filters.add(new RangeFilter(details.get(0), Double.parseDouble(details.get(1)),
                Double.parseDouble(details.get(2))));
    }

    private Filter getFilterByName(String name) {
        for (Filter filter : filters) {
            if (filter.getName().equals(name))
                return filter;
        }
        return null;
    }

    public ArrayList<Filter> getFilters() {
        return filters;
    }

    private ArrayList<Product> sortByNUmberOfViews() {
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

    private ArrayList<Product> sortByScores() {
        ArrayList<Product> products = getProducts();
        products.sort(new CompareScores());
        return products;
    }

    private ArrayList<Product> sortByAddingDate() {
        ArrayList<Product> products = getProducts();
        products.sort(new CompareDates());
        return products;
    }

    public String getCurrentSort() {
        return currentSort;
    }
}
