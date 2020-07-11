package server.controller.account.user;

import javafx.scene.image.Image;
import server.controller.Main;
import client.model.account.Account;
import client.model.account.Supporter;
import client.network.AuthToken;
import client.network.Message;

public class SupporterController implements AccountController {

    private static SupporterController supporterController = null;

    private SupporterController() {
    }

    public SupporterController getInstance() {
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
                Message message = new Message("edit request sent");
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
        return new Message("logout was successful");
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
