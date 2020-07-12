package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Supporter;

import java.util.ArrayList;

public class SupporterController implements AccountController {

    private static SupporterController supporterController = null;
    private static Supporter currentSupporter;
    private MainController mainController;
    private static Client client;

    private ArrayList<Buyer> buyers;

    private SupporterController() {
        this.mainController = MainController.getInstance();
        this.buyers = new ArrayList<>();
    }

    public SupporterController getInstance() {
        if (supporterController == null)
            supporterController = new SupporterController();
        currentSupporter = (Supporter) MainController.getInstance().getAccount();
        client = new Client(3000);
        client.setAuthToken(LoginController.getClient().getAuthToken());
        client.readMessage();
        System.out.println(client.getAuthToken());
        return supporterController;
    }

    @Override
    public Account getAccountInfo() {
        client.writeMessage(new Message("getAccountInfo"));
        return (Account) client.readMessage().getObjects().get(0);
    }

    @Override
    public void editField(String field, String context) throws Exception {
        Message message = new Message("editField");
        message.addToObjects(field);
        message.addToObjects(context);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void setProfileImage(String path) {
        Message message = new Message("setProfileImage");
        message.addToObjects(path);
        client.writeMessage(message);
        currentSupporter.setImagePath(path);
        client.readMessage();
    }

    @Override
    public void changeMainImage(Image image) {
        Message message = new Message("changeMainImage");
        message.addToObjects(image);
        client.writeMessage(message);
        currentSupporter.getGraphicPackage().setMainImage(image);
        client.readMessage();
    }

    @Override
    public void logout() {
        client.writeMessage(new Message("logout"));
        mainController.logout();
        client.readMessage();
        client.writeMessage(new Message("buy"));
        client.disconnect();
    }

    //todo complete these
    public void connectToChat() {

    }

    public void answerToBuyer(String buyerUserName) {

    }

    public String showMessagesFromBuyer(String buyerUserName) {

        return "";
    }
}
