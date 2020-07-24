package client.network.chat;

import client.model.account.Buyer;
import client.model.account.Seller;

import java.util.ArrayList;

public class SellerChatRoom {
    private static int chatRoomCount = 0;
    private static final ArrayList<SellerChatRoom> SELLER_CHAT_ROOMS = new ArrayList<>();

    private final String id;
    private final String sellerId;
    private final ArrayList<String> buyersId;
    private ArrayList<ChatMessage> chatMessages;

    public SellerChatRoom(String sellerId) {
        chatRoomCount += 1;
        this.id = String.valueOf(chatRoomCount);
        this.sellerId = sellerId;
        this.buyersId = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
        SELLER_CHAT_ROOMS.add(this);
    }

    public Seller getSeller() {
        try {
            return Seller.getSellerWithUsername(sellerId);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Buyer> getBuyers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (String buyerId:buyersId) {
            try {
                buyers.add(Buyer.getBuyerWithUsername(buyerId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buyers;
    }

    public void removeBuyer(String buyerId) {
        this.buyersId.remove(buyerId);
    }

    public void addToBuyers(String buyerId) {
        this.buyersId.add(buyerId);
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public ChatMessage getLastMessage() {
        return chatMessages.get(chatMessages.size() - 1);
    }

    public void addToChatMessages(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    public String getId() {
        return id;
    }

    public static ArrayList<SellerChatRoom> getSellerChatRooms() {
        return SELLER_CHAT_ROOMS;
    }

    public static SellerChatRoom getChatRoom(String id) throws Exception {
        for (SellerChatRoom supporterChatRoom : SELLER_CHAT_ROOMS) {
            if (supporterChatRoom.getId().equals(id))
                return supporterChatRoom;
        }
        throw new ThereIsNoChatRoom();
    }

    private static class ThereIsNoChatRoom extends Exception {
        private ThereIsNoChatRoom() {
            super("there is no chatroom with this id");
        }
    }
}
