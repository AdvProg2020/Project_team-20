package server.controller.auction;

import client.controller.product.filter.AllProductsController;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.auction.Auction;
import client.model.product.Product;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;
import server.controller.Main;
import client.network.AuthToken;
import server.controller.account.user.BuyerController;
import server.controller.product.ProductController;
import server.network.server.Server;

import java.util.ArrayList;

public class AuctionController extends Server {
    private static AuctionController auctionController = null;

    private AuctionController() {
        super(7777);
        System.out.println("auction server run");
        setMethods();
    }

    public Message addBuyer(String auctionID, double price, AuthToken authToken) {
        Message message;
        Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
        if (buyer.getCredit()<price) {
            message = new Message("Error");
            message.addToObjects(new NotEnoughMoneyException());
            return message;
        }
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        auction.addBuyerPrice(buyer.getUsername(), price);
        message = new Message("Confirmation");
        return message;
    }

    public Message removeBuyer(String auctionID, AuthToken authToken) {
        Message message;
        Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
        try {
            Auction auction = getAuctionByID(auctionID);
            auction.removeBuyer(buyer.getUsername());
            message = new Message("Confirmation");
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message addMessage(String auctionID, String context, AuthToken authToken) {
        Message message;
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
        auction.addComment(context, buyer.getUsername());
        message = new Message("Confirmation");
        return message;
    }

    public Message increaseBuyerPrice(String auctionID, double amountToIncrease, AuthToken authToken) {
        Message message;
        Buyer buyer = (Buyer)Main.getAccountWithToken(authToken);
        String buyerID = buyer.getUsername();
        try {
            Auction auction = getAuctionByID(auctionID);
            double beforeMoney = auction.getBuyerPrice(buyerID);
            if (buyer.getCredit()<(beforeMoney+amountToIncrease)) {
                message = new Message("Error");
                message.addToObjects(new NotEnoughMoneyException());
                return message;
            }
            auction.buyerIncreaseMoney(buyer, amountToIncrease);
            message = new Message("Confirmation");
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message getBuyersPrices(String auctionID, AuthToken authToken) {
        Message message;
        message = new Message("Confirmation");
        try {
            Auction auction = getAuctionByID(auctionID);
            message.addToObjects(auction.getBuyersPrice());
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message getAllMessages(String auctionID, AuthToken authToken) {
        Message message = new Message("Confirmation");
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        message.addToObjects(auction.getSellerChatRoom().getChatMessages());
        return message;
    }

    public Message getAllAuctions(AuthToken authToken) {
        Message message = new Message("Confirmation");
        ArrayList<Auction> allAuctions = Auction.getAllAuctions();
        ArrayList<Auction> updatedAllAuctions = new ArrayList<>();
        for (Auction auction:allAuctions) {
            if (auction.isNotEnded())
                updatedAllAuctions.add(auction);
        }
        Auction.setAllAuctions(updatedAllAuctions);
        message.addToObjects(updatedAllAuctions);
        return message;
    }

    public Message getHighest(String auctionID, AuthToken authToken) {
        Message message = new Message("Confirmation");
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
            message.addToObjects(auction.getHighest());
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message getHighestPrice(String auctionID, AuthToken authToken) {
        Message message = new Message("Confirmation");
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
            message.addToObjects(auction.getHighestPrice());
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message getProduct(String auctionID, AuthToken authToken) {
        Message message = new Message("Confirmation");
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
            message.addToObjects(auction.getProduct());
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
    }

    public Message isEnded(String auctionID, AuthToken authToken) {
        Message message;
        Auction auction;
        try {
            auction = getAuctionByID(auctionID);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        if (auction.isNotEnded()) {
            message  = new Message("No");
            return message;
        }
        else {
            Buyer winner = auction.getHighest();
            Buyer buyer = (Buyer)Main.getAccountWithToken(authToken);
            double price = auction.getBuyerPrice(buyer.getUsername());
            Seller seller = null;
            Product product = auction.getProduct();
            try {
                seller = (Seller) Seller.getAccountWithUsername(auction.getSellerUsername());

                if (buyer.getUsername().equals(winner.getUsername())) {
                    message = new Message("Won");
                    product.setSellerPrice(seller, price);
                    buyer.addProductToCart(product, seller);
                    BuyerController.getInstance().purchase("", "", "", false,null, null, null, null, authToken);
                }
                else {
                    message = new Message("Lost");
                    message.addToObjects(winner);
                    message.addToObjects(price);
                }
                return message;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Auction getAuctionByID(String id) throws Exception{
        Auction auction = Auction.getAuctionsById(id);
        if (auction==null) {
            throw new Auction.AuctionsNotFoundException();
        }
        return auction;
    }

    public static AuctionController getInstance() {
        if (auctionController==null)
            auctionController = new AuctionController();
        return auctionController;
    }

    public static class NotEnoughMoneyException extends Exception {
        public NotEnoughMoneyException() {
            super("Not enough money");
        }
    }

    @Override
    protected void setMethods() {
        methods.add("addBuyer");
        methods.add("removeBuyer");
        methods.add("addMessage");
        methods.add("increaseBuyerPrice");
        methods.add("getBuyersPrices");
        methods.add("getAllMessages");
        methods.add("getAllAuctions");
        methods.add("getHighest");
        methods.add("isEnded");
        methods.add("getProduct");
        methods.add("getHighestPrice");
    }

}
