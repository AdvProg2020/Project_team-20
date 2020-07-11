package client.controller.account.user;

import client.controller.MainController;
import client.controller.account.LoginController;
import client.model.Requestable;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Manager;
import client.model.product.Discount;
import client.model.product.Product;
import client.model.product.RequestableState;
import client.model.product.category.Category;
import client.model.product.category.CategorySet;
import client.model.product.category.SubCategory;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static client.model.account.Manager.*;
import static client.model.product.Product.getProductWithItsName;
import static client.model.product.category.CategorySet.getAllCategorySets;
import static client.model.product.category.CategorySet.getCategorySetByName;
import static client.model.product.category.SubCategory.*;

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
        return getRequestWithIds();
    }

    public String requestDetails(int requestId) throws Exception {
        Requestable request = findRequestWithId(requestId);
        return request.toString();
    }

    public void acceptRequest(int requestId) throws Exception {
        Requestable request = findRequestWithId(requestId);
        RequestableState state = request.getState();
        switch (state) {
            case EDITED:
                request.edit();
                break;
            case CREATED:
                request.changeStateAccepted();
                break;
        }
        deleteRequest(request, requestId);
    }

    public void declineRequest(int requestId) throws Exception {
        Requestable request = findRequestWithId(requestId);
        request.changeStateRejected();
        deleteRequest(request, requestId);
    }

    public ArrayList<SubCategory> manageSubCategories() {
        return getAllSubCategories();
    }

    public ArrayList<CategorySet> manageCategorySets() {
        return getAllCategorySets();
    }

    public ArrayList<Category> manageAllCategories() {
        return Category.getAllCategories();
    }

    public void editCategoryName(String categoryName, String newName) throws Exception {
        Category category = Category.getCategoryByName(categoryName);
        category.setName(newName);
    }

    public void removeFieldFromCategory(String name, String fieldName) throws Exception {
        SubCategory subCategory = getCategoryByName(name);
        subCategory.removeField(fieldName);
    }

    public void removeSubCategoryFromAllSubCategories(String categoryName, String subCategoryName) throws Exception {
        CategorySet categorySet = getCategorySetByName(categoryName);
        SubCategory subCategory = getCategoryByName(subCategoryName);
        subCategory.setParent(null);
        categorySet.removeSubCategory(subCategory);
    }

    public void removeCategorySetFromCategorySet(String parentName, String subName) throws CategorySet.CategoryDoesNotFoundException {
        CategorySet parentCategorySet = getCategorySetByName(parentName);
        CategorySet categorySet = getCategorySetByName(subName);
        parentCategorySet.removeSubCategorySet(categorySet);
    }

    public void removeProductFromCategory(String categoryName, String productName) throws Exception {
        SubCategory subCategory = getCategoryByName(categoryName);
        Product product = getProductWithItsName(productName);
        subCategory.removeProduct(product);
    }

    public void addCategory(String categoryName) throws Exception {
        SubCategory subCategory = new SubCategory(categoryName);
    }

    public void addCategorySet(String categorySetName) throws CategorySet.CategoryNameException {
        CategorySet categorySet = new CategorySet(categorySetName);
    }

    public void managerRemoveCategory(String categoryName) throws Exception {
        removeCategory(categoryName);
    }

    public void managerRemoveCategorySet(String categorySetName) throws CategorySet.CategoryDoesNotFoundException {
        CategorySet.removeCategorySet(categorySetName);
    }

    public void addFieldToCategory(String categoryName, String fieldName) throws Exception {
        SubCategory subCategory = getCategoryByName(categoryName);
        subCategory.addField(fieldName);
    }

    public void addSubCategoryToCategorySet(String categoryName, String subCategoryName) throws Exception {
        CategorySet categorySet = getCategorySetByName(categoryName);
        SubCategory subCategory = getCategoryByName(subCategoryName);
        subCategory.setParent(categorySet);
        categorySet.addToCategories(subCategory);
    }

    public void addCategorySetToCategorySet(String parentName, String subName) throws CategorySet.CategoryDoesNotFoundException {
        CategorySet parentCategorySet = getCategorySetByName(parentName);
        CategorySet categorySet = getCategorySetByName(subName);
        parentCategorySet.addToCategorySets(categorySet);
    }

    public void addNewFieldToCategory(String categoryName, String name) throws Exception {
        SubCategory subCategory = SubCategory.getCategoryByName(categoryName);
        subCategory.addField(name);
    }

    public void addParentToCategory(String categoryName, String parentName) throws Exception {
        SubCategory subCategory = getCategoryByName(categoryName);
        CategorySet parentCategory = getCategorySetByName(parentName);
        subCategory.setParent(parentCategory);
        parentCategory.addToCategories(subCategory);
    }

    public static class fieldIsInvalidException extends Exception {
        public fieldIsInvalidException() {
            super("field is invalid!");
        }
    }

    @Override
    public Account getAccountInfo() {
        return currentManager;
    }

    @Override
    public void editField(String field, String context) throws Exception {
        switch (field) {
            case "name":
                currentManager.setName(context);
                break;
            case "lastName":
                currentManager.setLastName(context);
                break;
            case "email":
                currentManager.setEmail(context);
                break;
            case "phoneNumber":
                currentManager.setPhoneNumber(context);
                break;
            case "password":
                currentManager.setPassword(context);
                break;
            case "credit":
                currentManager.setCredit(Double.parseDouble(context));
                break;
            default:
                throw new fieldIsInvalidException();
        }
    }

    public void setProfileImage(String path) {
        currentManager.setImagePath(path);
    }

    public static Manager getCurrentManager() {
        return currentManager;
    }

    @Override
    public void changeMainImage(Image image) {
        currentManager.getGraphicPackage().setMainImage(image);
    }

    @Override
    public void logout() {
        mainController.logout();
    }
}
