package client.controller.account.user;

import client.controller.MainController;
import javafx.scene.image.Image;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Supporter;

import java.util.ArrayList;

public class SupporterController implements AccountController {

    private static SupporterController supporterController = null;
    private static Supporter currentSupporter;
    private MainController mainController;

    private ArrayList<Buyer> buyers;

    private SupporterController() {
        this.mainController = MainController.getInstance();
        this.buyers = new ArrayList<>();
    }

    public SupporterController getInstance() {
        if (supporterController == null)
            supporterController = new SupporterController();
        currentSupporter = (Supporter) MainController.getInstance().getAccount();
        return supporterController;
    }

    @Override
    public void editField(String field, String context) throws Exception {
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
                throw new ManagerController.fieldIsInvalidException();
        }
    }


    @Override
    public Account getAccountInfo() {
        return currentSupporter;
    }

    public void setProfileImage(String path) {
        currentSupporter.setImagePath(path);
    }

    @Override
    public void changeMainImage(Image image) {
        currentSupporter.getGraphicPackage().setMainImage(image);
    }

    @Override
    public void logout() {
        mainController.logout();
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
