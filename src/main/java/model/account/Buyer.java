package model.account;

import model.Requestable;
import model.product.Cart;
import model.product.Discount;
import model.receipt.BuyerReceipt;
import model.product.Product;
import model.product.RequestableState;

import java.util.ArrayList;

public class Buyer extends Account implements Requestable {
    private Cart cart;
    private ArrayList<BuyerReceipt> purchaseHistory;
    private String address;
    private RequestableState state;
    private Buyer editedBuyer;

    public Buyer(String name, String lastName, String email, String phoneNumber, String username, String password, double credit) {
        super(name, lastName, email, phoneNumber, username, password, credit, AccountType.BUYER);
        this.purchaseHistory = new ArrayList<>();
    }

    public void changeStateEdited(String name, String lastName, String email, String phoneNumber, String password, double credit) {
        editedBuyer = new Buyer(name, lastName, email, phoneNumber, username, password, credit);
        state = RequestableState.EDITED;
    }

    public void editBuyer() {
        edit(editedBuyer);
        editedBuyer = null;
        state = RequestableState.ACCEPTED;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RequestableState getState() {
        return state;
    }

    public void addProductToCart(Product product, Seller seller) {
        cart.addProduct(product, seller);
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
}
