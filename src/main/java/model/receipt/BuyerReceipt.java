package model.receipt;
import model.account.Account;
import model.account.Seller;
import model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyerReceipt extends Receipt {
    static int buyerReceiptCount = 1;
    private double paidMoney;
    private ArrayList<String> sellersUsername;

    public BuyerReceipt(String id, double discountPercentage, HashMap<Product, Integer> products, Boolean hasItSent, double paidMoney, ArrayList<Seller> sellers) {
        super(id,discountPercentage, products, hasItSent);
        this.paidMoney = paidMoney;
        addToSellersUsername(sellers);
        buyerReceiptCount += 1;
        dateAndTime = LocalDateTime.now();
    }

    private void addToSellersUsername(ArrayList<Seller> sellers){
        ArrayList<String> username = new ArrayList<>();
        for(Seller seller:sellers){
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
        for (String name : sellersUsername){
            try {
                Seller seller = (Seller) Account.getAccountWithUsername(name);
                sellers.add(seller);
            }
            catch (Exception e){
            }
        }
        return sellers;
    }

}
