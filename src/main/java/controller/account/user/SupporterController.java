package controller.account.user;

import controller.MainController;
import javafx.scene.image.Image;
import model.account.Account;
import model.account.Manager;
import model.account.Supporter;

public class SupporterController implements AccountController {

    private static SupporterController supporterController = null;
    private static Supporter currentSupporter;
    private MainController mainController;

    private SupporterController() {
        this.mainController = MainController.getInstance();
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
}
