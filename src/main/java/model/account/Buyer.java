package model.account;

import model.Requestable;
import model.product.Cart;
import model.product.Discount;
import model.receipt.BuyerReceipt;

import java.util.ArrayList;

public class Buyer extends Account implements Requestable {
    private Cart cart;
    private ArrayList<BuyerReceipt> purchaseHistory;
    private ArrayList<Discount> discountCodes;
    private String address;

    public Buyer(String name, String lastName, String email, String phoneNumber, String username, double credit) {
        super(name, lastName, email, phoneNumber, username, credit);
        this.purchaseHistory = new ArrayList<>();
        this.discountCodes = new ArrayList<>();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ArrayList<BuyerReceipt> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void addReceipt(BuyerReceipt receipt) {
        this.purchaseHistory.add(receipt);
    }

    public ArrayList<Discount> getDiscountCodes() {
        return discountCodes;
    }

    public void addDiscountCodes(Discount discountCode) {
        this.discountCodes.add(discountCode);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void changeStateAccepted() {

    }

    @Override
    public void changeStateRejected() {

    }
}
