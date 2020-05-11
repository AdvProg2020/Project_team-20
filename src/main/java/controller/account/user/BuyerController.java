package controller.account.user;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;

public class BuyerController implements AccountController {
    MainController mainController;
    Buyer currentBuyer;

    public BuyerController() {
        this.mainController = MainController.getInstance();
        currentBuyer = (Buyer)mainController.getAccount();
    }

    @Override
    public Account getAccountInfo() {
        return null;
    }

    @Override
    public void editField(String field, String context) {

    }

    @Override
    public void logout() {

    }

    private Buyer getInfo() {
        return currentBuyer;
    }
}
