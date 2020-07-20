package client.controller.chat;

import client.controller.account.LoginController;
import client.model.account.Account;
import client.model.account.AccountType;
import client.model.account.Buyer;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;

import java.util.ArrayList;

public class ChatController {
    private static ChatController chatController = null;
    private Client client;

    private ChatController() {
    }

    public static ChatController getInstance() {
        if (chatController == null)
            chatController = new ChatController();
        return chatController;
    }

    private void connectWithTempAccount() {
        Message message = new Message("connectWithTempAccount");
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.setAuthToken(answer.getAuthToken());
    }

    private void connect() throws Exception {
        if (LoginController.getClient() == null) {
            throw new Account.AccountIsNotBuyerException();
        } else {
            client = new Client(572);
            client.readMessage();
            client.setAuthToken(LoginController.getClient().getAuthToken());
        }
    }

    public Buyer getBuyer(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("getBuyer");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Buyer) answer.getObjects().get(0);
    }

    public void writeNewMessage(String chatRoomId, String name, String contest, AccountType type) throws Exception {
        connect();
        ChatMessage chatMessage = new ChatMessage(name, contest, type);
        Message message = new Message("writeNewMessage");
        message.addToObjects(chatRoomId);
        message.addToObjects(chatMessage);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<ChatMessage> getAllMessages(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("getAllMessages");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        return (ArrayList<ChatMessage>) answer.getObjects().get(0);
    }

    public ArrayList<SupporterChatRoom> getAllChatRooms() throws Exception {
        connect();
        Message message = new Message("getAllChatRooms");
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        return (ArrayList<SupporterChatRoom>) answer.getObjects().get(0);
    }

    public void addToChatRoom(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("addToChatRoom");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void disconnect() {
        client.disconnect();
    }


}
