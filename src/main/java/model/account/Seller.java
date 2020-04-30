package model.account;

import model.Requestable;
import model.product.Product;
import model.product.Sale;
import model.receipt.SellerReceipt;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account implements Requestable {
    private ArrayList<SellerReceipt> saleHistory;
    private HashMap<Product, Integer> productsToSell;
    private ArrayList<Sale> sales;
    private ArrayList<String> details;
    //private HashMap<String, String> details;


    public Seller(String name, String lastName, String email, String phoneNumber, String username, double credit) {
        super(name, lastName, email, phoneNumber, username, credit);
        this.saleHistory = new ArrayList<>();
        this.productsToSell = new HashMap<>();
        this.sales = new ArrayList<>();
        this.details = new ArrayList<>();
        //this.details = new HashMap<>();
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

    public ArrayList<String> getDetails() {
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

    public void addDetail(String detail) {
        this.details.add(detail);
    }

    /*public void addDetail(String topic, String details) {
        this.details.put(topic, details);
    }*/

    public void removeFromSaleHistory(SellerReceipt saleHistory) {
        this.saleHistory.remove(saleHistory);
    }

    public void removeFromProductsToSell(Product product, int number) {
        this.productsToSell.remove(product, number);
    }

    public void removeSale(Sale sale) {
        this.sales.remove(sale);
    }

    //TODO behtar nist ke in ham hashmap bashe
    public void removeDetail(String detail) {
        this.details.remove(detail);
    }

    /*public void removeDetail(String topic) {
        this.details.remove(topic);
    }*/

    //TODO write
    @Override
    public void changeStateAccepted() {

    }

    @Override
    public void changeStateRejected() {

    }

}
