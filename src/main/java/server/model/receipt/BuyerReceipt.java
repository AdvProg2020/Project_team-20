package server.model.receipt;

import com.gilecode.yagson.YaGson;
import server.model.account.Account;
import server.model.account.Seller;
import server.model.product.Product;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BuyerReceipt extends Receipt {
    static int buyerReceiptCount = 1;
    private double paidMoney;
    private ArrayList<String> sellersUsername;

    public BuyerReceipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent,
                        double paidMoney, ArrayList<Seller> sellers) {
        super(id, discountPercentage, products, hasItSent);
        this.paidMoney = paidMoney;
        addToSellersUsername(sellers);
        buyerReceiptCount += 1;
        dateAndTime = LocalDateTime.now();
    }

    private void addToSellersUsername(ArrayList<Seller> sellers) {
        ArrayList<String> username = new ArrayList<>();
        for (Seller seller : sellers) {
            username.add(seller.getUsername());
        }
        this.sellersUsername = username;
    }

    public static int getBuyerReceiptCount() {
        return buyerReceiptCount;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public void setSellers(ArrayList<Seller> sellers) {
        addToSellersUsername(sellers);
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public ArrayList<Seller> getSellers() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (String name : sellersUsername) {
            try {
                Seller seller = (Seller) Account.getAccountWithUsername(name);
                sellers.add(seller);
            } catch (Exception e) {
            }
        }
        return sellers;
    }

    public static void store() {
        storeNumber();
    }

    public static void load() {
        loadNumber();
    }

    public static void storeNumber() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutReceipt/numberOfBuyerReceipt.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(buyerReceiptCount) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadNumber() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutReceipt/numberOfBuyerReceipt.txt");
            Scanner fileScanner = new Scanner(inputStream);
            buyerReceiptCount = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }
}
