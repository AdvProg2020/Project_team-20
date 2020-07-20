package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Supporter;
import client.network.Client;
import client.network.Message;
import client.network.chat.ChatMessage;
import client.network.chat.SupporterChatRoom;
import javafx.scene.image.Image;
import server.network.server.Server;

import java.util.ArrayList;

public class SupporterController implements AccountController {

    private static SupporterController supporterController = null;
    private static Supporter currentSupporter;
    private MainController mainController;
    private static Client client;
    private ArrayList<SupporterChatRoom> supporterChatRooms = new ArrayList<>();
    private static boolean isRequestSent;

    private ArrayList<Buyer> buyers;

    private SupporterController() {
        this.mainController = MainController.getInstance();
        this.buyers = new ArrayList<>();
    }

    public static SupporterController getInstance() {
        if (supporterController == null)
            supporterController = new SupporterController();
        currentSupporter = (Supporter) MainController.getInstance().getAccount();
        client = new Client(3000);
        client.setAuthToken(LoginController.getClient().getAuthToken());
        client.readMessage();
        isRequestSent = false;
        System.out.println(client.getAuthToken());
        return supporterController;
    }

    @Override
    public Account getAccountInfo() {
        client.writeMessage(new Message("getAccountInfo"));
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        return (Account) answer.getObjects().get(0);
    }

    @Override
    public void editField(String field, String context) throws Exception {
        Message message = new Message("editField");
        message.addToObjects(field);
        message.addToObjects(context);
        client.writeMessage(message);
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void setProfileImage(String path) {
        Message message = new Message("setProfileImage");
        message.addToObjects(path);
        client.writeMessage(message);
        isRequestSent = true;
        currentSupporter.setImagePath(path);
        client.readMessage();
        isRequestSent = false;
    }

    @Override
    public void changeMainImage(Image image) {
        Message message = new Message("changeMainImage");
        message.addToObjects(image);
        client.writeMessage(message);
        isRequestSent = true;
        currentSupporter.getGraphicPackage().setMainImage(image);
        client.readMessage();
        isRequestSent = false;
    }

    @Override
    public void logout() {
        client.writeMessage(new Message("logout"));
        mainController.logout();
        client.readMessage();
        client.disconnect();
    }

    public void createChatRoom() throws Exception {
        Message message = new Message("createChatRoom");
        message.addToObjects(currentSupporter.getUsername());
        client.writeMessage(message);
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        new Thread(this::updateChatRooms).start();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        System.out.println("end create message in client");
    }

    public void updateChatRooms() {
        while (true) {
            if (isRequestSent) {
                continue;
            }
            Message message = client.readMessage();
            //chatMessages.add();
            System.out.println(message.getText());
            if (message.getText().equals("createChatRoom server side"))
                continue;
            System.out.println("this is update");
            supporterChatRooms = (ArrayList<SupporterChatRoom>) message.getObjects().get(0);
            return;
        }
    }

    public void removeChatRoom(String chatRoomId) throws Exception {
        Message message = new Message("removeChatRoom");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<SupporterChatRoom> getAllChatRooms() {
        Message message = new Message("getAllChatRooms");
        client.writeMessage(message);
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        return (ArrayList<SupporterChatRoom>) answer.getObjects().get(0);
    }



    public void prepareChatRoomForNewClient(String chatRoomId) throws Exception{
        Message message = new Message("prepareChatRoomForNewClient");
        message.addToObjects(chatRoomId);
        client.writeMessage(message);
        isRequestSent = true;
        Message answer = client.readMessage();
        isRequestSent = false;
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }



    public ArrayList<SupporterChatRoom> getSupporterChatRooms() {
        return supporterChatRooms;
    }

    public void setSupporterChatRooms(ArrayList<SupporterChatRoom> supporterChatRooms) {
        this.supporterChatRooms = supporterChatRooms;
    }
}
