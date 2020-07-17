package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.Requestable;
import client.model.account.Account;
import client.model.account.AccountType;
import client.model.account.Buyer;
import client.model.account.Manager;
import client.model.product.Discount;
import client.model.product.Product;
import client.model.product.category.Category;
import client.model.product.category.CategorySet;
import client.model.product.category.SubCategory;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerController implements client.controller.account.user.AccountController {

    private MainController mainController;
    private static Manager currentManager;
    private static Client client;

    private static ManagerController managerController = null;

    private ManagerController() {
        this.mainController = MainController.getInstance();
    }

    public static ManagerController getInstance() {
        if (managerController == null)
            managerController = new ManagerController();
        currentManager = (Manager) MainController.getInstance().getAccount();
        client = new Client(7000);
        client.setAuthToken(LoginController.getClient().getAuthToken());
        client.readMessage();
        System.out.println(client.getAuthToken());
        return managerController;
    }


    public ArrayList<Account> manageUsers() {
        client.writeMessage(new Message("manageUsers"));
        return (ArrayList<Account>) client.readMessage().getObjects().get(0);
    }

    public Account viewUser(String userName) throws Exception {
        Message message = new Message("viewUser");
        message.addToObjects(userName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Account) answer.getObjects().get(0);
    }

    public void deleteUser(String userName) throws Exception {
        Message message = new Message("deleteUser");
        message.addToObjects(userName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void createManagerProfile(String name, String lastName, String email, String phoneNumber,
                                     String userName, String password, String creditString) throws Exception {
        try {
            Double.parseDouble(creditString);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
        Message message = new Message("createManagerProfile");
        message.addToObjects(name);
        message.addToObjects(lastName);
        message.addToObjects(email);
        message.addToObjects(phoneNumber);
        message.addToObjects(userName);
        message.addToObjects(password);
        message.addToObjects(creditString);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<Product> manageAllProducts() {
        client.writeMessage(new Message("manageAllProducts"));
        // todo check for arraylist
        return (ArrayList<Product>) client.readMessage().getObjects().get(0);
    }

    public void mangerRemoveProduct(String productId) throws Exception {
        Message message = new Message("mangerRemoveProduct");
        message.addToObjects(productId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void createDiscountCode(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage,
                                   int maxNumberOfUsage, ArrayList<String> buyersWithDiscount) throws Exception {
        Message message = new Message("createDiscountCode");
        message.addToObjects(startDate);
        message.addToObjects(endDate);
        message.addToObjects(discountPercentage);
        message.addToObjects(maxNumberOfUsage);
        message.addToObjects(buyersWithDiscount);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<Discount> viewDiscountCodes() {
        client.writeMessage(new Message("viewDiscountCodes"));
        return (ArrayList<Discount>) client.readMessage().getObjects().get(0);
    }

    public Discount viewDiscountCode(int discountCode) throws Exception {
        Message message = new Message("viewDiscountCode");
        message.addToObjects(discountCode);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Discount) answer.getObjects().get(0);
    }

    public void editDiscountPercentage(int discountCode, Double newOne) throws Exception {
        Message message = new Message("editDiscountPercentage");
        message.addToObjects(discountCode);
        message.addToObjects(newOne);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void editDiscountPercentage(int discountCode, String newOneString) throws Exception {
        double newOne;
        try {
            newOne = Double.parseDouble(newOneString);
            editDiscountPercentage(discountCode, newOne);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
    }

    public void editMaxDiscountUsage(int discountCode, int newOne) throws Exception {
        Message message = new Message("editMaxDiscountUsage");
        message.addToObjects(discountCode);
        message.addToObjects(newOne);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void editMaxDiscountUsage(int discountCode, String newOneString) throws Exception {
        int newOne;
        try {
            newOne = Integer.parseInt(newOneString);
            editMaxDiscountUsage(discountCode, newOne);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
    }

    public void editStartDateOfDiscountCode(int id, LocalDateTime startDate) throws Exception {
        Message message = new Message("editStartDateOfDiscountCode");
        message.addToObjects(id);
        message.addToObjects(startDate);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void editEndDateOfDiscount(int id, LocalDateTime endDate) throws Exception {
        Message message = new Message("editEndDateOfDiscount");
        message.addToObjects(id);
        message.addToObjects(endDate);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeDiscountCodes(int discountCode) throws Exception {
        Message message = new Message("removeDiscountCodes");
        message.addToObjects(discountCode);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public Buyer checkUsername(String userName) throws Exception {
        Message message = new Message("checkUsername");
        message.addToObjects(userName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Buyer) answer.getObjects().get(0);
    }


    public void addBuyerToBuyersWithDiscount(int discountId, String username) throws Exception {
        Message message = new Message("addBuyerToBuyersWithDiscount");
        message.addToObjects(discountId);
        message.addToObjects(username);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeBuyerFromBuyersWithDiscount(int discountId, String username) throws Exception {
        Message message = new Message("removeBuyerFromBuyersWithDiscount");
        message.addToObjects(discountId);
        message.addToObjects(username);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public HashMap<Integer, Requestable> manageRequests() {
        client.writeMessage(new Message("manageRequests"));
        return (HashMap<Integer, Requestable>) client.readMessage().getObjects().get(0);
    }

    public String requestDetails(int requestId) throws Exception {
        Message message = new Message("requestDetails");
        message.addToObjects(requestId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (String) answer.getObjects().get(0);
    }

    public void acceptRequest(int requestId) throws Exception {
        Message message = new Message("acceptRequest");
        message.addToObjects(requestId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void declineRequest(int requestId) throws Exception {
        Message message = new Message("declineRequest");
        message.addToObjects(requestId);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<SubCategory> manageSubCategories() {
        client.writeMessage(new Message("manageSubCategories"));
        return (ArrayList<SubCategory>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<CategorySet> manageCategorySets() {
        client.writeMessage(new Message("manageCategorySets"));
        return (ArrayList<CategorySet>) client.readMessage().getObjects().get(0);
    }

    public ArrayList<Category> manageAllCategories() {
        client.writeMessage(new Message("manageAllCategories"));
        return (ArrayList<Category>) client.readMessage().getObjects().get(0);
    }

    public void editCategoryName(String categoryName, String newName) throws Exception {
        Message message = new Message("editCategoryName");
        message.addToObjects(categoryName);
        message.addToObjects(newName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeFieldFromCategory(String name, String fieldName) throws Exception {
        Message message = new Message("removeFieldFromCategory");
        message.addToObjects(name);
        message.addToObjects(fieldName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeSubCategoryFromAllSubCategories(String categoryName, String subCategoryName) throws Exception {
        Message message = new Message("removeSubCategoryFromAllSubCategories");
        message.addToObjects(categoryName);
        message.addToObjects(subCategoryName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeCategorySetFromCategorySet(String parentName, String subName) throws Exception {
        Message message = new Message("removeCategorySetFromCategorySet");
        message.addToObjects(parentName);
        message.addToObjects(subName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void removeProductFromCategory(String categoryName, String productName) throws Exception {
        Message message = new Message("removeProductFromCategory");
        message.addToObjects(categoryName);
        message.addToObjects(productName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addCategory(String categoryName) throws Exception {
        Message message = new Message("addCategory");
        message.addToObjects(categoryName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addCategorySet(String categorySetName) throws Exception {
        Message message = new Message("addCategorySet");
        message.addToObjects(categorySetName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void managerRemoveCategory(String categoryName) throws Exception {
        Message message = new Message("managerRemoveCategory");
        message.addToObjects(categoryName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void managerRemoveCategorySet(String categorySetName) throws Exception {
        Message message = new Message("managerRemoveCategorySet");
        message.addToObjects(categorySetName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addFieldToCategory(String categoryName, String fieldName) throws Exception {
        Message message = new Message("addFieldToCategory");
        message.addToObjects(categoryName);
        message.addToObjects(fieldName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addSubCategoryToCategorySet(String categoryName, String subCategoryName) throws Exception {
        Message message = new Message("addSubCategoryToCategorySet");
        message.addToObjects(categoryName);
        message.addToObjects(subCategoryName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addCategorySetToCategorySet(String parentName, String subName) throws Exception {
        Message message = new Message("addCategorySetToCategorySet");
        message.addToObjects(parentName);
        message.addToObjects(subName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addNewFieldToCategory(String categoryName, String name) throws Exception {
        Message message = new Message("addNewFieldToCategory");
        message.addToObjects(categoryName);
        message.addToObjects(name);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public void addParentToCategory(String categoryName, String parentName) throws Exception {
        Message message = new Message("addParentToCategory");
        message.addToObjects(categoryName);
        message.addToObjects(parentName);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<Buyer> getAllBuyers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (Account account : manageUsers()) {
            if (account.getAccountType().equals(AccountType.BUYER))
                buyers.add((Buyer) account);
        }
        return buyers;
    }

    public static class fieldIsInvalidException extends Exception {
        public fieldIsInvalidException() {
            super("field is invalid!");
        }
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
        currentManager.setImagePath(path);
        client.readMessage();
    }

    @Override
    public void changeMainImage(Image image) {
        Message message = new Message("changeMainImage");
        message.addToObjects(image);
        client.writeMessage(message);
        currentManager.getGraphicPackage().setMainImage(image);
        client.readMessage();
    }

    @Override
    public void logout() {
        client.writeMessage(new Message("logout"));
        mainController.logout();
        client.readMessage();
        client.disconnect();
    }

    public static Manager getCurrentManager() {
        return currentManager;
    }
}
