package model.account;

import com.gilecode.yagson.YaGson;
import model.Requestable;
import model.product.Product;
import model.product.RequestType;
import model.product.RequestableState;
import model.product.Sale;
import model.receipt.SellerReceipt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Seller extends Account implements Requestable {
    private ArrayList<SellerReceipt> saleHistory;
    private HashMap<String, Integer> productIDsToSell;
    private ArrayList<String> productIDs;
    private ArrayList<Sale> sales;
    private String details;
    private RequestableState state;
    private Seller editedSeller;
    private Wallet wallet;

    public Seller(String name, String lastName, String email, String phoneNumber, String username, String password,
                  double credit, String details) {
        super(name, lastName, email, phoneNumber, username, password, credit, AccountType.SELLER);
        this.wallet = new Wallet(0);
        state = RequestableState.CREATED;
        this.saleHistory = new ArrayList<>();
        this.productIDsToSell = new HashMap<>();
        this.productIDs = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.details = details;
    }

    public void changeStateEdited(String name, String lastName, String email, String phoneNumber, String password,
                                  double credit, String details) {
        editedSeller = new Seller(name, lastName, email, phoneNumber, username, password, credit, details);
        state = RequestableState.EDITED;
    }

    public void edit() {
        edit(editedSeller);
        details = editedSeller.getDetails();
        editedSeller = null;
        state = RequestableState.ACCEPTED;
    }

    public ArrayList<SellerReceipt> getSaleHistory() {
        return saleHistory;
    }

    public HashMap<Product, Integer> getProductsToSell() {
        HashMap<Product, Integer> productsToSell = new HashMap<>();
        for (String productID : productIDsToSell.keySet()) {
            try {
                Product product = Product.getProductById(productID);
                productsToSell.put(product, productIDsToSell.get(productID));
            } catch (Exception e) {
            }
        }
        return productsToSell;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void addToSaleHistory(SellerReceipt saleHistory) {
        this.saleHistory.add(saleHistory);
    }

    public void addToProductsToSell(Product product, int number) {
        this.productIDsToSell.put(product.getId(), number);
        this.productIDs.add(product.getId());
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
    }


    public void removeFromSaleHistory(SellerReceipt saleHistory) {
        this.saleHistory.remove(saleHistory);
    }

    public void removeFromProductsToSell(Product product) {
        this.productIDsToSell.remove(product.getId());
        this.productIDs.remove(product.getId());
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String id : productIDs) {
            try {
                Product product = Product.getProductById(id);
                products.add(product);
            } catch (Exception e) {
            }
        }
        return products;
    }


    public Wallet getWallet() {
        return wallet;
    }

    public void decreaseProduct(Product product, int number) {
        int firstNum = this.productIDsToSell.get(product.getId());
        this.productIDsToSell.replace(product.getId(), firstNum - number);
    }

    public void increaseProduct(Product product, int number) {
        int firstNum = this.productIDsToSell.get(product.getId());
        this.productIDsToSell.replace(product.getId(), firstNum + number);
    }

    public void increaseCredit(double amount) {
        credit += amount;
    }

    public void removeSale(Sale sale) {
        this.sales.remove(sale);
    }


    public RequestableState getState() {
        return state;
    }

    public boolean hasProduct(String id) {
        for (String productID : productIDs) {
            if (productID.equals(id))
                return true;
        }
        return false;
    }

    public Sale getSaleWithProduct(Product product) {
        for (Sale sale : getSales()) {
            if (sale.hasProduct(product))
                return sale;
        }
        return null;
    }

    public void addProduct(Product product) {
        productIDs.add(product.getId());
    }

    public int getProductCount(Product product) {
        return productIDsToSell.get(product.getId());
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

    @Override
    public String toString() {
        String sellerString = "Name: " + name + "\n" + "\n";
        if (state.equals(RequestableState.EDITED))
            sellerString += "RequestType: Edited" + "\n" + "\n";
        else
            sellerString += "RequestType: Seller" + "\n" + "\n";

        sellerString += "Username: " + username + "\n" + "\n" +
                "Email: " + email + "\n" + "\n" +
                "Credit: " + credit + "\n" + "\n" +
                "Phone number: " + phoneNumber + "\n" + "\n";
        if (state.equals(RequestableState.EDITED)) {
            sellerString += "Edited Fields:\n" + "\n";
            sellerString += "Name: " + editedSeller.getName() + "\n" + "\n" +
                    "RequestType: Seller" + "\n" + "\n" +
                    "Email: " + editedSeller.getEmail() + "\n" + "\n" +
                    "Credit: " + editedSeller.getCredit() + "\n" + "\n" +
                    "Phone number: " + editedSeller.getPhoneNumber() + "\n" + "\n" +
                    "Company info: " + editedSeller.getDetails();
        }
        return sellerString;
    }


    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutSeller/sellers.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Account account : allAccounts) {
                if (account.getAccountType().equals(AccountType.SELLER))
                    fileWriter.write(yaGson.toJson(account) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutSeller/sellers.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Seller seller = yaGson.fromJson(fileScanner.nextLine(), Seller.class);
                allAccounts.add(seller);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Seller;
    }


}
