package server.controller.chat;

import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.GeneralAccount;
import client.model.account.TempAccount;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;
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

    public Message getBuyer(String chatRoomId, AuthToken authToken) {
        Message message = new Message("getBuyersUsernames");
        try {
            Buyer buyer = (Buyer) Main.getAccountWithToken(authToken);
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            message.addToObjects(supporterChatRoom.getBuyer());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message writeNewMessage(String chatRoomId, ChatMessage chatMessage, AuthToken authToken) {
        Message message = new Message("writeNewMessage");
        try {
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            supporterChatRoom.addToChatMessages(chatMessage);
            sendMessagesToClients(chatMessage);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private synchronized void sendMessagesToClients(ChatMessage chatMessage) {
        for (Client client : clients) {
            Message message = new Message("new message");
            message.addToObjects(chatMessage);
            client.writeMessage(message);
        }
    }

    public Message getAllMessages(String chatRoomId, AuthToken authToken) {
        Message message = new Message("getAllMessages");
        try {
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            message.addToObjects(supporterChatRoom.getChatMessages());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllChatRooms(AuthToken authToken) {
        Message message = new Message("getAllChatRooms");
        message.addToObjects(SupporterChatRoom.getSupporterChatRooms());
        for (SupporterChatRoom supporterChatRoom : SupporterChatRoom.getSupporterChatRooms()) {
            System.out.println(supporterChatRoom);
        }
        return message;
    }

    public Message addToChatRoom(String chatRoomId, AuthToken authToken) {
        Message message = new Message("account added to chatroom");
        GeneralAccount generalAccount = Main.getAccountWithToken(authToken);
        try {
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            if (supporterChatRoom.isBusy())
                throw new SupporterChatRoom.ChatRoomIsBusy();
            if (generalAccount instanceof Buyer) {
                supporterChatRoom.prepareToAcceptNewBuyer();
            supporterChatRoom.setBuyer((Buyer) generalAccount);
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

    public Message prepareChatRoomForNewClient(String chatRoomId, AuthToken authToken) {
        Message message = new Message("prepareChatRoomForNewClient");
        try {
            SupporterChatRoom supporterChatRoom =  SupporterChatRoom.getChatRoom(chatRoomId);
            supporterChatRoom.prepareToAcceptNewBuyer();
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }


    @Override
    protected void setMethods() {
        methods.add("getAllMessages");
        methods.add("writeNewMessage");
        methods.add("getBuyer");
        methods.add("connectWithTempAccount");
        methods.add("getAllChatRooms");
        methods.add("addToChatRoom");
        methods.add("prepareChatRoomForNewClient");
    }
}
