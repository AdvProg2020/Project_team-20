package server.controller.product.filter;

import client.model.product.Sale;
import client.network.Message;
import server.controller.MainController;

import java.util.ArrayList;

public class SaleController extends Filterable {
    private String sortElement;
    private MainController mainController;

    public SaleController() {
        super(2000);
        this.mainController = MainController.getInstance();
        setMethods();
        System.out.println("sale controller run");
    }

    public Message getAllSales() {
        Message message = new Message("getAllSales");
        try {
            message.addToObjects(Sale.getAllSales());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getSaleProducts(Sale sale) {
        Message message = new Message("getSaleProducts");
        message.addToObjects(sale.getProducts());
        return message;
    }


    @Override
    protected void setMethods() {
        super.setMethods();
        methods.add("getAllSales");
        methods.add("getSaleProducts");
    }

    @Override
    public Message filter(String filterType, ArrayList<String> details) {
        return super.filter(filterType, details);
    }

    @Override
    public Message disAbleFilter(String filterName) {
        return super.disAbleFilter(filterName);
    }

    @Override
    public Message getProducts() {
        return super.getProducts();
    }

    @Override
    public Message getProductFilterByCategory() {
        return super.getProductFilterByCategory();
    }

    @Override
    public Message showProducts() {
        return super.showProducts();
    }

    @Override
    public Message changeSort(String newSort) {
        return super.changeSort(newSort);
    }

    @Override
    public Message disableSort() {
        return super.disableSort();
    }

    @Override
    public Message disableFilterByCategory() {
        return super.disableFilterByCategory();
    }

    @Override
    public Message filterByCategory(ArrayList<String> details) {
        return super.filterByCategory(details);
    }

    @Override
    public Message filterByProductName(ArrayList<String> details) {
        return super.filterByProductName(details);
    }

    @Override
    public Message filterByOptionalFilter(ArrayList<String> details) {
        return super.filterByOptionalFilter(details);
    }

    @Override
    public Message filterByNumericalFilter(ArrayList<String> details) {
        return super.filterByNumericalFilter(details);
    }

    @Override
    public Message getFilters() {
        return super.getFilters();
    }

    @Override
    public Message getCurrentSort() {
        return super.getCurrentSort();
    }
}
