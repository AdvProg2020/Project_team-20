package client.controller.auction;

import client.controller.account.LoginController;
import client.controller.chat.ChatController;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.auction.Auction;
import client.model.product.Product;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;
import server.controller.Main;
import server.controller.account.user.BuyerController;

import java.util.ArrayList;
import java.util.HashMap;

public class AuctionController {
    private static AuctionController auctionController = null;
    private Client client;

    private AuctionController() {
    }

    public static AuctionController getInstance() {
        if (auctionController == null)
            auctionController = new AuctionController();
        return auctionController;
    }

    private void connect() {
        client = new Client(7777);
        client.readMessage();
        client.setAuthToken(LoginController.getClient().getAuthToken());
    }

    public void addBuyer(String auctionID, double price) throws Exception{
        connect();
        Message message = new Message("addBuyer");
        message.addToObjects(auctionID);
        message.addToObjects(price);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);
        client.disconnect();
    }

    public void removeBuyer(String auctionID) throws Exception{
        connect();
        Message message = new Message("removeBuyer");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
    }

    public void addMessage(String auctionID, String context) throws Exception{
        connect();
        Message message = new Message("addMessage");
        message.addToObjects(auctionID);
        message.addToObjects(context);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();

    }

    public void increaseBuyerPrice(String auctionID, double amountToIncrease) throws Exception{
        connect();
        Message message = new Message("increaseBuyerPrice");
        message.addToObjects(auctionID);
        message.addToObjects(amountToIncrease);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();

    }

    public HashMap<String, Double> getBuyersPrices(String auctionID) throws Exception{
        connect();
        Message message = new Message("getBuyersPrices");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (HashMap<String, Double>)answer.getObjects().get(0);
    }

    public ArrayList<ChatMessage> getAllMessages(String auctionID) throws Exception{
        connect();
        Message message = new Message("getAllMessages");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (ArrayList<ChatMessage>)answer.getObjects().get(0);

    }

    public ArrayList<Auction> getAllAuctions() throws Exception{
        connect();
        Message message = new Message("getAllAuctions");
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (ArrayList<Auction>)answer.getObjects().get(0);

    }

    public Buyer getHighest(String auctionID) throws Exception{
        connect();
        Message message = new Message("getHighest");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (Buyer)answer.getObjects().get(0);

    }

    public Message isEnded(String auctionID) throws Exception{
        connect();
        Message message = new Message("isEnded");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return answer;

    }

    public Product getProduct(String auctionID) throws Exception{
        connect();
        Message message = new Message("getProduct");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (Product) answer.getObjects().get(0);

    }

    public double getHighestPrice(String auctionID) throws Exception{
        connect();
        Message message = new Message("getHighestPrice");
        message.addToObjects(auctionID);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error"))
            throw (Exception)answer.getObjects().get(0);;
        client.disconnect();
        return (double) answer.getObjects().get(0);

    }


}
