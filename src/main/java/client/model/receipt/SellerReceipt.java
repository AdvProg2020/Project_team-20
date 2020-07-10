package client.model.receipt;

import com.gilecode.yagson.YaGson;
import client.model.account.Buyer;
import client.model.product.Product;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class SellerReceipt extends Receipt {
    static int sellerReceiptCount = 1;
    private double receivedMoney;
    private client.model.account.Buyer buyer;
    private double discountAmount;

    public SellerReceipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent,
                         double receivedMoney, Buyer buyer, double discountAmount) {
        super(id, discountPercentage, products, hasItSent);
        this.receivedMoney = receivedMoney;
        this.buyer = buyer;
        this.discountAmount = discountAmount;
        sellerReceiptCount += 1;
    }

    public void setReceivedMoney(double receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getReceivedMoney() {
        return receivedMoney;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public static int getSellerReceiptCount() {
        return sellerReceiptCount;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void store() {
        storeNumber();
    }

    public static void load() {
        loadNumber();
    }

    public static void storeNumber() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutReceipt/numberOfSellerReceipt.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(sellerReceiptCount) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadNumber() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutReceipt/numberOfSellerReceipt.txt");
            Scanner fileScanner = new Scanner(inputStream);
            sellerReceiptCount = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }
}
