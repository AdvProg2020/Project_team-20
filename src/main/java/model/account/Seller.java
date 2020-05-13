package model.account;

import model.Requestable;
import model.product.Product;
import model.product.RequestableState;
import model.product.Sale;
import model.receipt.SellerReceipt;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Account implements Requestable {
    private ArrayList<SellerReceipt> saleHistory;
    private HashMap<Product, Integer> productsToSell;
    private ArrayList<Product> products;
    private ArrayList<Sale> sales;
    private HashMap<String, String> details;
    private RequestableState state;
    private Seller editedSeller;


    public Seller(String name, String lastName, String email, String phoneNumber, String username, String password, double credit) {
        super(name, lastName, email, phoneNumber, username, password, credit, AccountType.SELLER);
        this.saleHistory = new ArrayList<>();
        this.productsToSell = new HashMap<>();
        this.products = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.details = new HashMap<>();
    }

    public void changeStateEdited(String name, String lastName, String email, String phoneNumber, String password, double credit) {
        editedSeller = new Seller(name, lastName, email, phoneNumber, username, password, credit);
        state = RequestableState.EDITED;
    }

    public void edit() {
        edit(editedSeller);
        editedSeller = null;
        state = RequestableState.ACCEPTED;
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
        this.products.add(product);
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

    public void removeFromProductsToSell(Product product) {
        this.productsToSell.remove(product);
        this.products.remove(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void decreaseProduct(Product product, int number) {
        int firstNum = this.productsToSell.get(product);
        this.productsToSell.replace(product,firstNum - number);
    }

    public void increaseProduct(Product product, int number) {
        int firstNum = this.productsToSell.get(product);
        this.productsToSell.replace(product,firstNum + number);
    }

    public void increaseCredit(double amount) {
        credit += amount;
    }

    public void removeSale(Sale sale) {
        this.sales.remove(sale);
    }

    public void removeDetail(String topic) {
        this.details.remove(topic);
    }

    public RequestableState getState() {
        return state;
    }

    public boolean hasProduct(String id) {
        for (Product product : products) {
            if(product.getId().equals(id))
                return true;
        }
        return false;
    }

    public int getProductCount(Product product) {
        return productsToSell.get(product);
    }

    @Override
    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        addAccount(this);
    }

    @Override
    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }


}
