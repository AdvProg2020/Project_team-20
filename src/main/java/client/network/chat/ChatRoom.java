package client.network.chat;

import client.model.account.Buyer;
import client.model.account.Supporter;

import java.util.ArrayList;

public class ChatRoom {
    private static int chatRoomCount = 0;
    private static final ArrayList<ChatRoom> chatRooms = new ArrayList<>();

    private final String id;
    private final Supporter supporter;
    private final ArrayList<Buyer> buyers;
    private ArrayList<ChatMessage> chatMessages;

    public ChatRoom(Supporter supporter) {
        chatRoomCount += 1;
        this.id = String.valueOf(chatRoomCount);
        this.supporter = supporter;
        this.buyers = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
        chatRooms.add(this);
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

    public void addToChatMessages(ChatMessage chatMessage) {
        this.chatMessages.add(chatMessage);
    }

    public String getId() {
        return id;
    }

    public static ArrayList<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public static ChatRoom getChatRoom(String id) throws Exception {
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getId().equals(id))
                return chatRoom;
        }
        throw new ThereIsNoChatRoom();
    }

    private static class ThereIsNoChatRoom extends Exception {
        private ThereIsNoChatRoom() {
            super("there is no chatroom with this id");
        }
    }
}
