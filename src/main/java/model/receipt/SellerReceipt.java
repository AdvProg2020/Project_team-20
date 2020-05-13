package model.receipt;
import model.account.Buyer;
import model.product.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerReceipt extends Receipt {
    static int sellerReceiptCount = 0;
    private double receivedMoney ;
    private model.account.Buyer buyer;
    private double discountAmount ;

    public SellerReceipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent, double receivedMoney, Buyer buyer, double discountAmount) {
        super(id,discountPercentage, products,hasItSent);
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
}
