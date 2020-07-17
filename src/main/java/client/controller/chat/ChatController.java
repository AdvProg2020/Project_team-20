package client.controller.chat;

import client.controller.account.LoginController;
import client.model.account.Account;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.ChatRoom;

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
            client = new Client(572);
            client.readMessage();
            connectWithTempAccount();
        } else {
            throw new Account.AccountIsNotBuyerException();
        }
    }

    public ArrayList<String> getBuyers(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("getBuyers");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (ArrayList<String>) answer.getObjects().get(0);
    }

    public void writeNewMessage(String chatRoomId, String username, String contest) throws Exception {
        connect();
        ChatMessage chatMessage = new ChatMessage(username, contest);
        Message message = new Message("writeNewMessage");
        message.addToObjects(chatRoomId);
        message.addToObjects(chatMessage);
        client.writeMessage(message);
        client.disconnect();
        Message answer = client.readMessage();
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

    public ArrayList<ChatRoom> getAllChatRooms() {
        Message message = new Message("getAllChatRooms");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<ChatRoom>) answer.getObjects().get(0);
    }

    public void addToChatRoom(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("addToChatRoom");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        client.disconnect();
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }


}
