package controller.account.user;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.Cart;
import model.product.Product;

public class BuyerController implements AccountController {
    MainController mainController;
    Buyer currentBuyer;

    public BuyerController() {
        this.mainController = MainController.getInstance();
        currentBuyer = (Buyer)mainController.getAccount();
    }

    public Cart viewCart() {
        return currentBuyer.getCart();
    }

    public Product getProductById(String id) throws Exception {
        return (currentBuyer.getCart()).getProductById(id);
    }

    public void editBuyer(String fieldToChangeName, String editedField) {
        switch (fieldToChangeName) {
            case "name":
                currentBuyer.changeStateEdited(editedField, currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "lastName":
                currentBuyer.changeStateEdited(currentBuyer.getName(), editedField, currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
            case "email":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), editedField, currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "phoneNumber":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), editedField, currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "password":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), editedField, currentBuyer.getCredit());
                break;
            case "credit":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), Integer.parseInt(editedField));
                break;
        }
        Manager.addRequest(currentBuyer);
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
