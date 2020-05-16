package model.product;

import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale implements Requestable {
    static public int allSalesCount = 1;
    private String id;
    private ArrayList<Product> products;
    private RequestableState state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Sale editedSale;
    private Seller seller;
    double salePercentage;

    public Sale(String id, ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage, Seller seller) {
        this.id = id;
        this.products = products;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercentage = salePercentage;
        this.state =  RequestableState.CREATED;
        this.seller = seller;
        allSalesCount++;
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
        seller.addSale(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        editedSale = new Sale(products, startDate, endDate, salePercentage);
        state = RequestableState.EDITED;
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
        return (salePercentage*100);
    }

    public static ArrayList<Sale> getAllSales() {
        ArrayList<Sale> allSales = new ArrayList<>();
        for (Account account:Seller.getAllAccounts()) {
            if (account instanceof Seller) {
                Seller seller = (Seller)account;
                allSales.addAll(seller.getSales());
            }
        }
        return allSales;
    }

    public Seller getSeller() {
        return seller;
    }

    @Override
    public String toString() {
        StringBuilder productStr = new StringBuilder();
        for (Product product:products) {
            productStr.append(product.getName());
            productStr.append(' ');
        }
        String buyerString = "RequestType         : Sale" + "\n" +
                             "Products            : " + productStr + "\n" +
                             "Sale Percentage     : " + salePercentage + "\n";
        if (state.equals(RequestableState.EDITED)) {
            StringBuilder productEditStr = new StringBuilder();
            for (Product product:editedSale.getProducts()) {
                productEditStr.append(product.getName());
                productEditStr.append(' ');
            }
            buyerString = "<Edited>\n" + buyerString;
            buyerString += "Edited Fields:\n";
            buyerString += "RequestType         : Sale" + "\n" +
                            "Products            : " + productEditStr + "\n" +
                            "Sale Percentage     : " + editedSale.getSalePercentage() + "\n";
        }
        return buyerString;
    }
}
