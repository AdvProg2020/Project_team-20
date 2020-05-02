package model.account;

import model.Requestable;
import model.product.Product;
import model.product.RequestableState;
import model.product.Sale;
import main.java.model.receipt.SellerReceipt;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account implements Requestable {
    private ArrayList<SellerReceipt> saleHistory;
    private HashMap<Product, Integer> productsToSell;
    private ArrayList<Sale> sales;
    private HashMap<String, String> details;
    private RequestableState state;


    public Seller(String name, String lastName, String email, String phoneNumber, String username, double credit) {
        super(name, lastName, email, phoneNumber, username, credit);
        this.saleHistory = new ArrayList<>();
        this.productsToSell = new HashMap<>();
        this.sales = new ArrayList<>();
        this.details = new HashMap<>();
    }

    public ArrayList<SellerReceipt> getSaleHistory() {
        return saleHistory;
    }

    public HashMap<Product, Integer> getProductsToSell() {
        return productsToSell;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void addToSaleHistory(SellerReceipt saleHistory) {
        this.saleHistory.add(saleHistory);
    }

    public void AddToProductsToSell(Product product, int number) {
        this.productsToSell.put(product, number);
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
    }

    public void addDetail(String topic, String details) {
        this.details.put(topic, details);
    }

    public void removeFromSaleHistory(SellerReceipt saleHistory) {
        this.saleHistory.remove(saleHistory);
    }

    public void removeFromProductsToSell(Product product, int number) {
        this.productsToSell.remove(product, number);
    }

    public void removeSale(Sale sale) {
        this.sales.remove(sale);
    }

    public void removeDetail(String topic) {
        this.details.remove(topic);
    }

    @Override
    public void changeStateAccepted() {
        state = RequestableState.Accepted;
        addAccount(this);
    }

    @Override
    public void changeStateRejected() {
        state = RequestableState.Rejected;
    }

}
