package model.receipt;
import model.account.Seller;

import java.util.ArrayList;

public class BuyerReceipt extends Receipt {
    private double paidMoney;
    private ArrayList<model.account.Seller> sellers;

    public BuyerReceipt(String id, double discountPercentage, ArrayList<model.product.Product > products, Boolean hasItSent,double paidMoney, ArrayList<Seller> sellers) {
        super(id,discountPercentage, products, hasItSent);
        this.paidMoney = paidMoney;
        this.sellers = sellers;
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
