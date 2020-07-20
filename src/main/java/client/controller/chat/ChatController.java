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
    private ArrayList<ChatMessage> chatMessages;

    private ChatController() {
    }

    public static ChatController getInstance() {
        if (chatController == null)
            chatController = new ChatController();
        return chatController;
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
        Message message = new Message("getBuyer");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Buyer) answer.getObjects().get(0);
    }

    public void writeNewMessage(String chatRoomId, String name, String contest, AccountType type) throws Exception {
        ChatMessage chatMessage = new ChatMessage(name, contest, type);
        Message message = new Message("writeNewMessage");
        message.addToObjects(chatRoomId);
        message.addToObjects(chatMessage);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    private ArrayList<ChatMessage> getAllMessages(String chatRoomId) {
        Message message = new Message("getAllMessages");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<ChatMessage>) answer.getObjects().get(0);
    }

    public ArrayList<SupporterChatRoom> getAllChatRooms() throws Exception {
        connect();
        Message message = new Message("getAllChatRooms");
        client.writeMessage(message);
        Message answer = client.readMessage();
        disconnect();
        return (ArrayList<SupporterChatRoom>) answer.getObjects().get(0);
    }

    public void addToChatRoom(String chatRoomId) throws Exception {
        connect();
        Message message = new Message("addToChatRoom");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        chatMessages = new ArrayList<>();
        new Thread(this::updateChatMessages).start();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    private void updateChatMessages() {
        while (true) {
            Message message = client.readMessage();
            //chatMessages.add();
            chatMessages.add(chatMessages.size(), (ChatMessage) message.getObjects().get(0));
            System.out.println(chatMessages.get(chatMessages.size() - 1));
        }
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void disconnect() {
        client.disconnect();
    }


}
