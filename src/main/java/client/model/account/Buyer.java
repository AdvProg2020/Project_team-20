package client.model.account;

import com.gilecode.yagson.YaGson;
import client.model.Requestable;
import client.model.product.Cart;
import client.model.product.Product;
import client.model.product.RequestType;
import client.model.product.RequestableState;
import client.model.receipt.BuyerReceipt;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Buyer extends Account implements Requestable {
    private Cart cart;
    private ArrayList<BuyerReceipt> purchaseHistory;
    private String address;
    private RequestableState state;
    private Buyer editedBuyer;

    public Buyer(String name, String lastName, String email, String phoneNumber, String username, String password) {
        super(name, lastName, email, phoneNumber, username, password, AccountType.BUYER);
        state = RequestableState.CREATED;
        this.purchaseHistory = new ArrayList<>();
    }

    public void changeStateEdited(String name, String lastName, String email, String phoneNumber, String password) {
        editedBuyer = new Buyer(name, lastName, email, phoneNumber, username, password);
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

    public BuyerReceipt getReceiptById(String id) throws Exception {
        for (BuyerReceipt buyerReceipt : purchaseHistory) {
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
        for (BuyerReceipt buyerReceipt : purchaseHistory) {
            for (Product product : buyerReceipt.getProducts().keySet())
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
        String buyerString = "Name: " + name + "\n" + "\n";
        if (state.equals(RequestableState.EDITED))
            buyerString += "RequestType: Edited" + "\n" + "\n";
        else
            buyerString += "RequestType: Buyer" + "\n" + "\n";

        buyerString += "Username: " + username + "\n" + "\n" +
                "Email: " + email + "\n" + "\n" +
                "Address: " + address + "\n" + "\n" +
                "Credit: " + credit + "\n" + "\n" +
                "Phone numbers: " + phoneNumber + "\n" + "\n" +
                "network state: " + networkState;
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
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutBuyer/buyers.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Buyer buyer = yaGson.fromJson(fileScanner.nextLine(), Buyer.class);
                buyer.setNetworkState(NetworkState.OFFLINE);
                allAccounts.add(buyer);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Buyer;
    }
}
