package model.product;

import model.Requestable;
import model.account.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale implements Requestable {
    private String id;
    private ArrayList<Product> products;
    private RequestableState state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Sale editedSale;
    double salePercentage;

    public Sale(String id, ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        this.id = id;
        this.products = products;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercentage = salePercentage;
        this.state =  RequestableState.CREATED;
    }

    public Sale(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        this.products = products;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercentage = salePercentage;
        this.state =  RequestableState.CREATED;
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        editedSale = new Sale(products, startDate, endDate, salePercentage);
        state = RequestableState.EDITED;
        Manager.addRequest(editedSale);
    }

    public void edit() {
        products = editedSale.getProducts();
        startDate = editedSale.getStartDate();
        endDate = editedSale.getEndDate();
        editedSale = null;
        state = RequestableState.ACCEPTED;
    }

    public boolean hasProduct(Product productToFind) {
        for (Product product: products) {
            if (product.equals(productToFind))
                return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public RequestableState getState() {
        return state;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean validSaleTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(startDate) && currentTime.isBefore(endDate);
    }

    public Sale getEditedSale() {
        return editedSale;
    }

    public double getSalePercentage() {
        return salePercentage;
    }
}
