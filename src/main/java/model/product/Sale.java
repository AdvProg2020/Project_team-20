package model.product;

import model.Requestable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale implements Requestable {
    private String id;
    private ArrayList<Product> products;
    private RequestableState state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    double salePercentage;

    public Sale(String id, ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        this.id = id;
        this.products = products;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercentage = salePercentage;
        this.state =  RequestableState.Created;
    }

    public void changeStateAccepted() {
        state = RequestableState.Accepted;
    }

    public void changeStateRejected() {
        state = RequestableState.Rejected;
    }

    // Kolan edit ro naneveshtim!!!!!!!!!!!!!!!!!!!!


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
}
