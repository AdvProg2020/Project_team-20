package model.account;

import com.gilecode.yagson.YaGson;
import model.Requestable;
import model.product.Cart;
import model.product.Product;
import model.product.RequestType;
import model.product.RequestableState;
import model.receipt.BuyerReceipt;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Buyer extends Account implements Requestable {
    private Cart cart;
    private ArrayList<BuyerReceipt> purchaseHistory;
    private String address;
    private RequestableState state;
    private Buyer editedBuyer;

    public Buyer(String name, String lastName, String email, String phoneNumber, String username, String password,
                 double credit) {
        super(name, lastName, email, phoneNumber, username, password, credit, AccountType.BUYER);
        state = RequestableState.CREATED;
        this.purchaseHistory = new ArrayList<>();
    }

    public void changeStateEdited(String name, String lastName, String email, String phoneNumber, String password,
                                  double credit) {
        editedBuyer = new Buyer(name, lastName, email, phoneNumber, username, password, credit);
        state = RequestableState.EDITED;
    }

    public void edit() {
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

    public BuyerReceipt getReceiptById(String id) throws Exception{
        for (BuyerReceipt buyerReceipt:purchaseHistory) {
            if (buyerReceipt.getId().equals(id))
                return buyerReceipt;
        }
        throw new orderNotFoundException();
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

    public static class orderNotFoundException extends Exception {
        public orderNotFoundException() {
            super("Order not found");
        }
    }

    public boolean hasBoughtProduct(String productId) {
        for (BuyerReceipt buyerReceipt:purchaseHistory) {
            for (Product product:buyerReceipt.getProducts().keySet())
                if (product.getId().equals(productId))
                    return true;
        }
        return false;
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
        String buyerString = "Name                : " + name + "\n" +
                             "RequestType         : Buyer" + "\n" +
                             "Username            : " + username + "\n" +
                             "Email               : " + email + "\n" +
                             "Address             : " + address + "\n" +
                             "Credit              : " + credit + "\n" +
                             "Phone number        : " + phoneNumber;
        if (state.equals(RequestableState.EDITED))
            buyerString = "<Edited>\n" + buyerString;
        return buyerString;
    }


    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutBuyer/buyers.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Account account : allAccounts) {
                if (account.getAccountType().equals(AccountType.BUYER))
                    fileWriter.write(yaGson.toJson(account) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutBuyer/buyers.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Buyer buyer = yaGson.fromJson(fileScanner.nextLine(), Buyer.class);
                allAccounts.add(buyer);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Buyer;
    }
}
