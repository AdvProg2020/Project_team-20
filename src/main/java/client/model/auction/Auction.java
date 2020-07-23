package client.model.auction;

import client.model.account.AccountType;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.product.Product;
import client.network.chat.ChatMessage;
import client.network.chat.SellerChatRoom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction {
    private SellerChatRoom sellerChatRoom;
    private Product product;
    private LocalDateTime endDate;
    private HashMap<String, Double> buyersPrice;
    private String sellerUsername;

    public Auction(Product product, LocalDateTime endDate, String sellerUsername) {
        this.product = product;
        this.endDate = endDate;
        this.sellerUsername = sellerUsername;
        try {
            sellerChatRoom = new SellerChatRoom(sellerUsername);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buyersPrice = new HashMap<>();
    }

    public boolean isEnded() {
        LocalDateTime now = LocalDateTime.now();
        return endDate.isEqual(now);
    }

    public void addComment(String comment, String buyerID) {
        sellerChatRoom.addToChatMessages(new ChatMessage(buyerID, comment, AccountType.BUYER));
    }

    public void removeBuyer(String buyerId) {
        buyersPrice.remove(buyerId);
        sellerChatRoom.removeBuyer(buyerId);
    }

    public void addBuyerPrice(String buyerId, double price) {
        buyersPrice.put(buyerId, price);
        sellerChatRoom.addToBuyers(buyerId);
    }

    public HashMap<String, Double> getBuyersPrice() {
        return buyersPrice;
    }

    public SellerChatRoom getSellerChatRoom() {
        return sellerChatRoom;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }
}
