package client.model.bank;

import com.gilecode.yagson.YaGson;

import java.io.*;
import java.util.Scanner;

public class BankReceipt {
    private static int bankReceiptCount = 0;
    private BankReceiptType bankReceiptType;
    private double money;
    private String sourceID;
    private String destID;
    private String description;
    private String ID;
    private boolean receiptState;

    public BankReceipt(BankReceiptType bankReceiptType, double money, String sourceID, String destID, String description) {
        this.bankReceiptType = bankReceiptType;
        this.money = money;
        this.sourceID = sourceID;
        this.destID = destID;
        this.description = description;
        this.ID = Integer.toString(bankReceiptCount);
        bankReceiptCount++;
        receiptState = false;
    }

    public BankReceiptType getBankReceiptType() {
        return bankReceiptType;
    }

    public double getMoney() {
        return money;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getDestID() {
        return destID;
    }

    public String getDescription() {
        return description;
    }

    public boolean wasPaid() {
        return receiptState;
    }

    public void setReceiptState(boolean receiptState) {
        this.receiptState = receiptState;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        if (bankReceiptType.equals(BankReceiptType.MOVE))
            return "money= " + money + "\n" +
                    "sourceID= " + sourceID + "\n" +
                    "destID= " + destID + "\n" +
                    "description= " + description + "\n" +
                    "ID= " + ID + "\n" +
                    "payment state= " + receiptState + "\n" +
                    "__________" + "\n";

        else if (bankReceiptType.equals(BankReceiptType.DEPOSIT))
            return "money= " + money + "\n" +
                    "destID= " + destID + "\n" +
                    "description= " + description + "\n" +
                    "ID= " + ID + "\n" +
                    "payment state= " + receiptState + "\n" +
                    "__________" + "\n";
        else
            return "money= " + money + "\n" +
                    "sourceID= " + sourceID + "\n" +
                    "description= " + description + "\n" +
                    "ID= " + ID + "\n" +
                    "payment state= " + receiptState + "\n" +
                    "__________" + "\n";
    }

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutBank/bankReceiptCount.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(bankReceiptCount) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutBank/bankReceiptCount.txt");
            Scanner fileScanner = new Scanner(inputStream);
            if (fileScanner.hasNextLine())
                bankReceiptCount = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            else bankReceiptCount = 0;
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }
}
