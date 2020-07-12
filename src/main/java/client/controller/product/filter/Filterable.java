package client.controller.product.filter;

import client.model.filter.Filter;
import client.model.product.Product;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;


public abstract class Filterable {
    protected ArrayList<Filter> filters = new ArrayList<>();
    protected ArrayList<String> filterNames = new ArrayList<>();
    protected String currentSort = "ByNumberOfViews";
    protected ArrayList<Product> productsToShow;
    protected ArrayList<Product> productsBeforeFiltering;
    protected static Client client;

    public void filter(String filterType, ArrayList<String> details) throws Exception {
        Message message = new Message("filter");
        message.addToObjects(filterType);
        message.addToObjects(details);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void disAbleFilter(String filterName) throws Exception {
        Message message = new Message("disAbleFilter");
        message.addToObjects(filterName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<Product> getProducts() {
        Message message = new Message("getProducts");
        client.writeMessage(message);
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<Product> getProductFilterByCategory() {
        Message message = new Message("getProductFilterByCategory");
        client.writeMessage(message);
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<Product> showProducts() throws Exception {
        Message message = new Message("showProducts");
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

    public void changeSort(String newSort) throws Exception {
        Message message = new Message("changeSort");
        message.addToObjects(newSort);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void disableSort() {
        Message message = new Message("disableSort");
        client.writeMessage(message);
        client.readMessage();
    }

    public void disableFilterByCategory() {
        Message message = new Message("disableFilterByCategory");
        client.writeMessage(message);
        client.readMessage();
    }

    public void filterByCategory(ArrayList<String> details) throws Exception {
        Message message = new Message("filterByCategory");
        message.addToObjects(details);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void filterByProductName(ArrayList<String> details) {
        Message message = new Message("filterByProductName");
        message.addToObjects(details);
        client.writeMessage(message);
        client.readMessage();
    }

    public void filterByOptionalFilter(ArrayList<String> details) {
        Message message = new Message("filterByOptionalFilter");
        message.addToObjects(details);
        client.writeMessage(message);
        client.readMessage();
    }

    public void filterByNumericalFilter(ArrayList<String> details) {
        Message message = new Message("filterByNumericalFilter");
        message.addToObjects(details);
        client.writeMessage(message);
        client.readMessage();
    }

    public ArrayList<Filter> getFilters() {
        Message message = new Message("getFilters");
        client.writeMessage(message);
        return (ArrayList<Filter>) client.readMessage().getObjects().get(0);
    }


    public static class FilterNotFoundException extends Exception {
        public FilterNotFoundException() {
            super("Filter name is not correct.");
        }
    }

    public String getCurrentSort() {
        Message message = new Message("getCurrentSort");
        client.writeMessage(message);
        return (String) client.readMessage().getObjects().get(0);
    }
}
