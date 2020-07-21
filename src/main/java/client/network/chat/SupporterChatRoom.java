package client.network.chat;

import client.model.account.Buyer;
import client.model.account.Supporter;

import java.util.ArrayList;

public class SupporterChatRoom {
    private static int chatRoomCount = 0;
    private static final ArrayList<SupporterChatRoom> SUPPORTER_CHAT_ROOMS = new ArrayList<>();

    private final String id;
    private final Supporter supporter;
    private Buyer buyer;
    private ArrayList<ChatMessage> chatMessages;
    private boolean busy;

    public SupporterChatRoom(Supporter supporter) {
        chatRoomCount += 1;
        System.out.println("chat room count " + chatRoomCount);
        this.id = String.valueOf(chatRoomCount);
        System.out.println("id " + id);
        this.supporter = supporter;
        this.chatMessages = new ArrayList<>();
        this.busy = false;
        supporter.addToChatRooms(this);
        SUPPORTER_CHAT_ROOMS.add(this);
    }

    public static void removeChatRoom(SupporterChatRoom supporterChatRoom) {
        SUPPORTER_CHAT_ROOMS.remove(supporterChatRoom);
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void clearChatRoom() {
        chatMessages = new ArrayList<>();
    }

    public void prepareToAcceptNewBuyer() {
        clearChatRoom();
        this.busy = false;
        this.buyer = null;
    }

    public Supporter getSupporter() {
        return supporter;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
        this.busy = true;
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

    public static ArrayList<SupporterChatRoom> getSupporterChatRooms() {
        return SUPPORTER_CHAT_ROOMS;
    }

    public static SupporterChatRoom getChatRoom(String id) throws Exception {
        for (SupporterChatRoom supporterChatRoom : SUPPORTER_CHAT_ROOMS) {
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

    public static class ChatRoomIsBusy extends Exception {
        public ChatRoomIsBusy() {
            super("chatroom is busy");
        }
    }
}
