package server.controller.account.user;

import client.model.account.Account;
import client.model.account.Manager;
import client.model.account.Supporter;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;
import client.network.chat.SupporterChatRoom;
import javafx.scene.image.Image;
import server.controller.Main;
import server.network.server.Server;

import java.util.ArrayList;

public class SupporterController extends Server implements AccountController {

    private static SupporterController supporterController = null;

    private SupporterController() {
        super(3000);
        setMethods();
        System.out.println("supporter controller run");
    }

    public static SupporterController getInstance() {
        if (supporterController == null)
            supporterController = new SupporterController();
        return supporterController;
    }

    @Override
    public Message editField(String field, String context, AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        switch (field) {
            case "name":
                currentSupporter.setName(context);
                break;
            case "lastName":
                currentSupporter.setLastName(context);
                break;
            case "email":
                currentSupporter.setEmail(context);
                break;
            case "phoneNumber":
                currentSupporter.setPhoneNumber(context);
                break;
            case "password":
                currentSupporter.setPassword(context);
                break;
            default:
                Message message = new Message("Error");
                message.addToObjects(new ManagerController.fieldIsInvalidException());
                return message;
        }
        return new Message("edit request sent");
    }


    @Override
    public Message getAccountInfo(AuthToken authToken) {
        Message message = new Message("account info");
        try {
            message.addToObjects(Account.getAccountWithUsername(authToken.getUsername()));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message setProfileImage(String path, AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        currentSupporter.setImagePath(path);
        return new Message("set profile image was successful");
    }

    @Override
    public Message changeMainImage(Image image, AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        currentSupporter.getGraphicPackage().setMainImage(image);
        return new Message("change main image");
    }

    @Override
    public Message logout(AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        Main.removeFromTokenHashMap(authToken, currentSupporter);
        for (SupporterChatRoom chatRoom : currentSupporter.getChatRooms()) {
            removeChatRoom(chatRoom.getId(), authToken);
        }
        return new Message("logout was successful");
    }

    public synchronized Message createChatRoom(String username, AuthToken authToken) {
        for (Client client : clients) {
            System.out.println(client);
        }
        Message message = new Message("createChatRoom server side");
        try {
            Supporter supporter = Account.getSupporterWithUsername(username);
            SupporterChatRoom supporterChatRoom = new SupporterChatRoom(supporter);
            new Thread(() -> updateChatRooms(supporterChatRoom.getId())).start();
            //updateChatRooms(supporterChatRoom.getId());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeChatRoom(String chatRoomId, AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        Message message = new Message("removeChatRoom");
        try {
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            currentSupporter.removeFromChatRooms(supporterChatRoom);
            SupporterChatRoom.removeChatRoom(supporterChatRoom);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllChatRooms(AuthToken authToken) {
        Supporter currentSupporter = (Supporter) Main.getAccountWithToken(authToken);
        Message message = new Message("getAllChatRooms");
        message.addToObjects(currentSupporter.getChatRooms());
        for (SupporterChatRoom chatRoom : currentSupporter.getChatRooms()) {
            System.out.println(chatRoom);
        }
        return message;
    }

    public void updateChatRooms(String chatRoomId) {
        try {
            Message message = new Message("chatRooms updated");
            System.out.println("update chatroom in server");
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            Supporter supporter = supporterChatRoom.getSupporter();
            message.addToObjects(supporter.getChatRooms());
            /*for (Client client : clients) {
                client.writeMessage(message);
                System.out.println(client);
            }

             */
            for (SupporterChatRoom chatRoom : supporter.getChatRooms()) {
                System.out.println(chatRoom);
                System.out.println(chatRoom.getBuyer());
            }


            Client client = Main.getClientWithAccount(supporter);
            client.writeMessage(message);
            System.out.println(client.getAccount());
        } catch (Exception invalidToken) {
            invalidToken.printStackTrace();
        }
    }

    public Message prepareChatRoomForNewClient(String chatRoomId, AuthToken authToken) {
        Message message = new Message("prepareChatRoomForNewClient");
        try {
            SupporterChatRoom supporterChatRoom = SupporterChatRoom.getChatRoom(chatRoomId);
            supporterChatRoom.prepareToAcceptNewBuyer();
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    protected void setMethods() {
        methods.add("getAccountInfo");
        methods.add("editField");
        methods.add("setProfileImage");
        methods.add("changeMainImage");
        methods.add("logout");
        methods.add("prepareChatRoomForNewClient");
        methods.add("removeChatRoom");
        methods.add("getAllChatRooms");
        methods.add("createChatRoom");
    }
}
