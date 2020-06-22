package model.product;

import com.gilecode.yagson.YaGson;
import model.Requestable;
import model.account.Account;
import model.account.Seller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Sale implements Requestable {
    public static int allSalesCount = 1;
    private String id;
    private ArrayList<String> products = new ArrayList<>();
    private RequestableState state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Sale editedSale;
    private Seller seller;
    double salePercentage;

    public Sale(String id, ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate,
                double salePercentage, Seller seller) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        addToProducts(products);
        this.salePercentage = salePercentage;
        this.state = RequestableState.CREATED;
        this.seller = seller;
        allSalesCount++;
    }

    public void addToProducts(ArrayList<Product> allProducts) {
        for (Product product : allProducts) {
            this.products.add(product.getId());
        }
    }

    public Sale(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate, double salePercentage) {
        addToProducts(products);
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePercentage = salePercentage;
        this.state = RequestableState.CREATED;
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        seller.addSale(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Product> products, LocalDateTime startDate, LocalDateTime endDate,
                                  double salePercentage) {
        editedSale = new Sale(products, startDate, endDate, salePercentage);
        state = RequestableState.EDITED;
    }

    public void edit() {
        products = editedSale.getProductsStr();
        startDate = editedSale.getStartDate();
        endDate = editedSale.getEndDate();
        salePercentage = editedSale.getSalePercentage();
        editedSale = null;
        state = RequestableState.ACCEPTED;
    }

    public boolean hasProduct(Product productToFind) {
        for (String productId : products) {
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
        for (String productID : products) {
            try {
                newProducts.add(Product.getProductById(productID));
            } catch (Exception e) {
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
        for (Account account : Seller.getAllAccounts()) {
            if (account instanceof Seller) {
                Seller seller = (Seller) account;
                for (Sale sale : seller.getSales())
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
        for (String productID : products) {
            try {
                productStr.append(Product.getProductById(productID).getName());
                productStr.append('\n');
            } catch (Exception e) {
            }
        }
        String buyerString = "Sale Percentage: " + salePercentage*100 + "\n" + "\n" +
                "RequestType: Sale" + "\n" + "\n" +
                "Start date: " + startDate + "\n" + "\n" +
                "End date: " + endDate + "\n" + "\n" +
                "Products: " + productStr + "\n" + "\n";
        if (state.equals(RequestableState.EDITED)) {
            StringBuilder productEditStr = new StringBuilder();
            System.out.println(editedSale.getProducts().size());
            for (Product product : editedSale.getProducts()) {
                productEditStr.append(product.getName());
                productEditStr.append(' ');
            }
            buyerString += "Edited Fields:\n" + "\n";
            buyerString += "RequestType: Edit" + "\n" + "\n" +
                    "Sale Percentage: " + editedSale.getSalePercentage() *100  + "\n" + "\n" +
                    "Start date: " + editedSale.getStartDate() + "\n" + "\n" +
                    "End date: " + editedSale.getEndDate() + "\n" + "\n" +
                    "Products: \n" + productEditStr;
        }
        return buyerString;
    }

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutSale/sales.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(allSalesCount) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutSale/sales.txt");
            Scanner fileScanner = new Scanner(inputStream);
            allSalesCount = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }

    public RequestType getRequestType() {
        return RequestType.Sale;
    }

    public ArrayList<String> getProductsStr() {
        return products;
    }
}
