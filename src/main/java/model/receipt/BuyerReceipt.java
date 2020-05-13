package model.receipt;
import model.account.Seller;
import model.product.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyerReceipt extends Receipt {
    static int buyerReceiptCount = 0;
    private double paidMoney;
    private ArrayList<model.account.Seller> sellers;

    public BuyerReceipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent, double paidMoney, ArrayList<Seller> sellers) {
        super(id,discountPercentage, products, hasItSent);
        this.paidMoney = paidMoney;
        this.sellers = sellers;
        buyerReceiptCount += 1;
    }

    public static int getBuyerReceiptCount() {
        return buyerReceiptCount;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

}
