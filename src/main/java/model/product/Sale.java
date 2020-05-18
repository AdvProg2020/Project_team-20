package model.product;

import com.gilecode.yagson.YaGson;
import model.Requestable;
import model.account.Account;
import model.account.Seller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Sale implements Requestable {
    static public int allSalesCount = 1;
    private String id;
    private ArrayList<String> products = new ArrayList<>();
    private RequestableState state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Sale editedSale;
    private Seller seller;
    double salePercentage;
    private RequestType requestType = RequestType.Sale;

    public Sale(String id, ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage, Seller seller) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        addToProducts(products);
        this.salePercentage = salePercentage;
        this.state =  RequestableState.CREATED;
        this.seller = seller;
        allSalesCount++;
    }

    public void addToProducts(ArrayList<Product> allProducts){
        for (Product product:allProducts){
            this.products.add(product.getId());
        }
    }

    public Sale(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        addToProducts(products);
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
        addToProducts(editedSale.getProducts());
        startDate = editedSale.getStartDate();
        endDate = editedSale.getEndDate();
        editedSale = null;
        state = RequestableState.ACCEPTED;
    }

    public boolean hasProduct(Product productToFind) {
        for (String productId: products) {
            if (productId.equals(productToFind.getId()))
                return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> newProducts = new ArrayList<>();
        for(String productID:products){
            try {
                newProducts.add(Product.getProductById(productID));
            }
           catch (Exception e){
           }
        }
        return newProducts;
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

    public static ArrayList<Sale> getAllSales() {
        ArrayList<Sale> allSales = new ArrayList<>();
        for (Account account:Seller.getAllAccounts()) {
            if (account instanceof Seller) {
                Seller seller = (Seller)account;
                for (Sale sale:seller.getSales())
                    if (sale.validSaleTime())
                        allSales.add(sale);
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
        for (String productID:products) {
            try {
                productStr.append(Product.getProductById(productID).getName());
                productStr.append(' ');
            }
            catch (Exception e){

            }
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

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("Sale.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String saleStr = scanner.nextLine();
                Sale sale = yaGson.fromJson(saleStr, Sale.class);

            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
