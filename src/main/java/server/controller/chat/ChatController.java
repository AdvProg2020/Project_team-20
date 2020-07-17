package server.controller.chat;

import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.GeneralAccount;
import client.model.account.TempAccount;
import client.network.AuthToken;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.ChatRoom;
import server.controller.Main;
import server.network.server.Server;

public class ChatController extends Server {
    private static ChatController chatController = null;

    private ChatController() {
        super(572);
        System.out.println("chat server run");
        setMethods();
    }

    public static ChatController getInstance() {
        if (chatController == null)
            chatController = new ChatController();
        return chatController;
    }

    public Message connectWithTempAccount() {
        AuthToken authToken = AuthToken.generateAuth();
        Main.addToTokenHashMap(authToken, new TempAccount());
        Message answer = new Message("token for tempAccount");
        answer.setAuth(authToken);
        return answer;
    }

    public Message getBuyers(String chatRoomId, AuthToken authToken) {
        Message message = new Message("getBuyersUsernames");
        try {
            Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
            ChatRoom chatRoom = ChatRoom.getChatRoom(chatRoomId);
            message.addToObjects(chatRoom.getBuyers());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message writeNewMessage(String chatRoomId, ChatMessage chatMessage, AuthToken authToken) {
        Message message = new Message("writeNewMessage");
        try {
            Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
            ChatRoom chatRoom = ChatRoom.getChatRoom(chatRoomId);
            chatRoom.addToChatMessages(chatMessage);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllMessages(String chatRoomId, AuthToken authToken) {
        Message message = new Message("getAllMessages");
        try {
            ChatRoom chatRoom = ChatRoom.getChatRoom(chatRoomId);
            message.addToObjects(chatRoom.getChatMessages());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllChatRooms(AuthToken authToken) {
        Message message = new Message("getAllChatRooms");
        message.addToObjects(ChatRoom.getChatRooms());
        return message;
    }

    public Message addToChatRoom(String chatRoomId, AuthToken authToken) {
        Message message = new Message("account added to chatroom");
        GeneralAccount generalAccount = Main.getAccountWithToken(authToken);
        try {
            ChatRoom chatRoom = ChatRoom.getChatRoom(chatRoomId);
            if (generalAccount instanceof Buyer) {
            chatRoom.addToBuyers((Buyer) generalAccount);
            } else {
                message = new Message("Error");
                message.addToObjects(new Account.AccountIsNotBuyerException());
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(new Account.AccountIsNotBuyerException());
        }
        return message;
    }

    @Override
    protected void setMethods() {
        methods.add("getAllMessages");
        methods.add("writeNewMessage");
        methods.add("getBuyers");
        methods.add("connectWithTempAccount");
        methods.add("getAllChatRooms");
        methods.add("addToChatRoom");
    }
}
