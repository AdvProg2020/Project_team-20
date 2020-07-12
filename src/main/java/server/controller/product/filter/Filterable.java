package server.controller.product.filter;

import client.model.filter.Filter;
import client.model.filter.NameFilter;
import client.model.filter.OptionalFilter;
import client.model.filter.RangeFilter;
import client.model.product.Product;
import client.model.product.category.Category;
import client.network.Message;
import server.network.server.Server;

import java.util.ArrayList;
import java.util.stream.Collectors;


public abstract class Filterable extends Server {
    protected ArrayList<Filter> filters = new ArrayList<>();
    protected ArrayList<String> filterNames = new ArrayList<>();
    protected String currentSort = "ByNumberOfViews";
    protected ArrayList<Product> productsToShow;
    protected ArrayList<Product> productsBeforeFiltering;

    public Filterable(int port) {
        super(port);
    }

    @Override
    protected void setMethods() {
        methods.add("filter");
        methods.add("disAbleFilter");
        methods.add("getProducts");
        methods.add("getProductFilterByCategory");
        methods.add("showProducts");
        methods.add("changeSort");
        methods.add("disableSort");
        methods.add("disableFilterByCategory");
        methods.add("filterByCategory");
        methods.add("filterByProductName");
        methods.add("filterByOptionalFilter");
        methods.add("filterByNumericalFilter");
        methods.add("getFilters");
        methods.add("getCurrentSort");
    }

    public Message filter(String filterType, ArrayList<String> details) {
        Message message = new Message("filter");
        try {
            if (filterType.equalsIgnoreCase("category")) {
                filterByCategory(details);
            } else if (filterType.equalsIgnoreCase("product name")) {
                filterByProductName(details);
            } else if (filterType.equalsIgnoreCase("optional field")) {
                filterByOptionalFilter(details);
            } else if (filterType.equalsIgnoreCase("numerical field")) {
                filterByNumericalFilter(details);
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message disAbleFilter(String filterName) {
        Message message = new Message("disableFilter");
        try {
            Filter filter = getFilterByName(filterName);
            if (filter == null)
                throw new FilterNotFoundException();
            filters.remove(filter);
            filterNames.remove(filter.getName());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getProducts() {
        Message message = new Message("getProducts");
        try {
            message.addToObjects(productsToShow.stream()
                    .filter(product -> {
                        for (Filter filter : filters)
                            if (!filter.validFilter(product))
                                return false;
                        return true;
                    })
                    .collect(Collectors
                            .toCollection(ArrayList::new)));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getProductFilterByCategory() {
        Message message = new Message("getProductFilterByCategory");
        try {
            message.addToObjects(productsToShow);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message showProducts() {
        Message message = new Message("showProducts");
        try {
            switch (currentSort) {
                case "ByScores":
                    message.addToObjects(sortByScores());
                    return message;
                case "ByDates":
                    message.addToObjects(sortByAddingDate());
                    return message;
                case "ByNumberOfViews":
                    message.addToObjects(sortByNUmberOfViews());
                    return message;
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(new SortNotFoundException());
        }
        return message;
    }

    public Message changeSort(String newSort) {
        Message message = new Message("change sort");
        try {
            this.currentSort = newSort;
            showProducts();
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message disableSort() {
        Message message = new Message("disable sort");
        try {
            currentSort = null;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message disableFilterByCategory() {
        Message message = new Message("disableFilterByCategory");
        try {
            productsToShow = productsBeforeFiltering;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message filterByCategory(ArrayList<String> details) {
        Message message = new Message("filterByCategory");
        try {
            String name = details.get(0);
            productsBeforeFiltering = productsToShow;
            ArrayList<Category> categories = Category.getAllCategories();
            Category category1;
            for (Category category : categories) {
                if (category.getName().equals(name)) {
                    category1 = category;
                    productsToShow = category1.getProducts();
                    break;
                }
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message filterByProductName(ArrayList<String> details) {
        Message message = new Message("filter By products name");
        try {
            filters.add(new NameFilter(details.get(0)));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message filterByOptionalFilter(ArrayList<String> details) {
        Message message = new Message("filterByOptionalFilter");
        try {
            String name = details.get(0);
            details.remove(0);
            filters.add(new OptionalFilter(name, details));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message filterByNumericalFilter(ArrayList<String> details) {
        Message message = new Message("filterByNumericalFilter");
        try {
            filters.add(new RangeFilter(details.get(0), Double.parseDouble(details.get(1)),
                    Double.parseDouble(details.get(2))));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private Filter getFilterByName(String name) {
        for (Filter filter : filters) {
            if (filter.getName().equals(name))
                return filter;
        }
        return null;
    }

    public Message getFilters() {
        Message message = new Message("filterByNumericalFilter");
        try {
            message.addToObjects(filters);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private ArrayList<Product> sortByNUmberOfViews() {
        ArrayList<Product> products = (ArrayList<Product>) getProducts().getObjects().get(0);
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
        ArrayList<Product> products = (ArrayList<Product>) getProducts().getObjects().get(0);
        products.sort(new CompareScores());
        return products;
    }

    private ArrayList<Product> sortByAddingDate() {
        ArrayList<Product> products = (ArrayList<Product>) getProducts().getObjects().get(0);
        products.sort(new CompareDates());
        return products;
    }

    public Message getCurrentSort() {
        Message message = new Message("getCurrentSort");
        try {
            message.addToObjects(currentSort);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }
}
