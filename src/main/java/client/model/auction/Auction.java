package client.model.auction;

import client.model.account.AccountType;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.product.Product;
import client.network.AuthToken;
import client.network.chat.ChatMessage;
import client.network.chat.SellerChatRoom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction {
    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private static int auctionCounts = 0;
    private String id;
    private SellerChatRoom sellerChatRoom;
    private Product product;
    private LocalDateTime endDate;
    private HashMap<String, Double> buyersPrice;
    private String sellerUsername;

    public Auction(Product product, LocalDateTime endDate, String sellerUsername) {
        this.product = product;
        this.endDate = endDate;
        this.sellerUsername = sellerUsername;
        this.id = Integer.toString(auctionCounts);
        auctionCounts++;
        try {
            sellerChatRoom = new SellerChatRoom(sellerUsername);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buyersPrice = new HashMap<>();
        allAuctions.add(this);
    }

    public double getBuyerPrice(String buyerID) {
        return buyersPrice.get(buyerID);
    }

    public boolean isNotEnded() {
        LocalDateTime now = LocalDateTime.now();
        return endDate.isAfter(now);
    }

    public Buyer getHighest() {
        String highestBuyerID = null;
        double highest = -1;
        for (String buyerID : buyersPrice.keySet()) {
            if (buyersPrice.get(buyerID)>highest) {
                highestBuyerID = buyerID;
                highest = buyersPrice.get(buyerID);
            }
        }
        try {
            return Buyer.getBuyerWithUsername(highestBuyerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void buyerIncreaseMoney(Buyer buyer, double priceIncrease) {
        String buyerId = buyer.getUsername();
        double beforeMoney = buyersPrice.get(buyerId);
        buyersPrice.replace(buyerId, beforeMoney+priceIncrease);
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

    public String getId() {
        return id;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public static void setAllAuctions(ArrayList<Auction> allAuctions) {
        Auction.allAuctions = allAuctions;
    }

    public static Auction getAuctionsById(String id) {
        for (Auction auction:allAuctions) {
            if (auction.getId().equals(id))
                return auction;
        }
        return null;
    }

    public static void deleteAuction(String id) throws Exception{
        for (Auction auction:allAuctions) {
            if (auction.getId().equals(id)) {
                allAuctions.remove(auction);
                return;
            }
        }
        throw new AuctionsNotFoundException();
    }

    public static class AuctionsNotFoundException extends Exception {
        public AuctionsNotFoundException() {
            super("Auction not found");
        }
    }

}
