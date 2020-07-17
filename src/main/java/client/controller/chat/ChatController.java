package client.controller.chat;

import client.controller.account.LoginController;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;

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

    private void connect() {
        if (LoginController.getClient() == null) {
            // client = new Client();
            client.readMessage();
            connectWithTempAccount();
        } else {
            // client = new Client();
            client.readMessage();
            client.setAuthToken(LoginController.getClient().getAuthToken());
        }
    }

    public ArrayList<String> getBuyersUsernames(String chatRoomId) {
        connect();
        Message message = new Message("getBuyersUsernames");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        return (ArrayList<String>) answer.getObjects().get(0);
    }

    public void writeNewMessage(String username, String contest) {
        connect();
        ChatMessage chatMessage = new ChatMessage(username, contest);
        Message message = new Message("writeNewMessage");
        message.addToObjects(chatMessage);
        client.writeMessage(message);
        client.readMessage();
        client.disconnect();
    }

    public ArrayList<ChatMessage> getAllMessages() {
        connect();
        Message message = new Message("getAllMessages");
        client.writeMessage(message);
        Message answer = client.readMessage();
        client.disconnect();
        return (ArrayList<ChatMessage>) answer.getObjects().get(0);
    }


}
