package client.controller.product.filter;

import client.controller.MainController;
import client.model.filter.Filter;
import client.model.product.Product;
import client.model.product.Sale;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class SaleController extends Filterable {
    private String sortElement;
    private MainController mainController;

    public SaleController() {
        this.mainController = MainController.getInstance();
    }

    public ArrayList<Sale> getAllSales() {
        connect();
        Message message = new Message("getAllSales");
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.writeMessage(new Message("buy"));
        return (ArrayList<Sale>) answer.getObjects().get(0);
    }

    public ArrayList<Product> getSaleProducts(Sale sale) {
        connect();
        Message message = new Message("getSaleProducts");
        message.addToObjects(sale);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.writeMessage(new Message("buy"));
        return (ArrayList<Product>) answer.getObjects().get(0);
    }

    @Override
    public void filter(String filterType, ArrayList<String> details) throws Exception {
        connect();
        super.filter(filterType, details);
    }

    @Override
    public void disAbleFilter(String filterName) throws Exception {
        connect();
        super.disAbleFilter(filterName);
    }

    @Override
    public ArrayList<Product> getProducts() {
        connect();
        return super.getProducts();
    }

    @Override
    public ArrayList<Product> getProductFilterByCategory() {
        connect();
        return super.getProductFilterByCategory();
    }

    @Override
    public ArrayList<Product> showProducts() throws Exception {
        connect();
        return super.showProducts();
    }

    @Override
    public void changeSort(String newSort) throws Exception {
        connect();
        super.changeSort(newSort);
    }

    @Override
    public void disableSort() {
        connect();
        super.disableSort();
    }

    @Override
    public void disableFilterByCategory() {
        connect();
        super.disableFilterByCategory();
    }

    @Override
    public void filterByCategory(ArrayList<String> details) throws Exception {
        connect();
        super.filterByCategory(details);
    }

    @Override
    public void filterByProductName(ArrayList<String> details) {
        connect();
        super.filterByProductName(details);
    }

    @Override
    public void filterByOptionalFilter(ArrayList<String> details) {
        connect();
        super.filterByOptionalFilter(details);
    }

    @Override
    public void filterByNumericalFilter(ArrayList<String> details) {
        connect();
        super.filterByNumericalFilter(details);
    }

    @Override
    public ArrayList<Filter> getFilters() {
        connect();
        return super.getFilters();
    }

    @Override
    public String getCurrentSort() {
        connect();
        return super.getCurrentSort();
    }

    private void connect() {
        client = new Client(2000);
        client.readMessage();
    }

}
