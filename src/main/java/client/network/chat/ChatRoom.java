package client.network.chat;

import client.model.account.Buyer;
import client.model.account.Supporter;

import java.util.ArrayList;

public class ChatRoom {
    private Supporter supporter;
    private ArrayList<Buyer> buyers;
    private ArrayList<ChatMessage> chatMessages;

    public ChatRoom(Supporter supporter) {
        this.supporter = supporter;
        this.buyers = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public Supporter getSupporter() {
        return supporter;
    }

    public ArrayList<Buyer> getBuyers() {
        return buyers;
    }

    public void addToBuyers(Buyer buyer) {
        this.buyers.add(buyer);
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public ChatMessage getLastMessage() {
        return chatMessages.get(chatMessages.size() - 1);
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
