package server.controller.account.user;

import javafx.scene.image.Image;
import server.controller.Main;
import server.controller.account.LoginController;
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
import client.network.AuthToken;
import client.network.Message;
import server.network.server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static client.model.account.Manager.*;
import static client.model.product.Discount.removeDiscountCode;
import static client.model.product.Product.*;
import static client.model.product.category.CategorySet.getAllCategorySets;
import static client.model.product.category.CategorySet.getCategorySetByName;
import static client.model.product.category.SubCategory.*;

public class ManagerController extends Server implements AccountController {

    private static ManagerController managerController = null;

    private ManagerController() {
        super(7000);
        setMethods();
        System.out.println("manager controller run");
    }

    public static ManagerController getInstance() {
        if (managerController == null)
            managerController = new ManagerController();
        return managerController;
    }


    public Message manageUsers(AuthToken authToken) {
        Message message = new Message("all users");
        message.addToObjects(getAllAccounts());
        return message;
    }

    public Message viewUser(String userName, AuthToken authToken) {
        Message message = new Message("view user");
        try {
            message.addToObjects(Account.getAccountWithUsername(userName));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message deleteUser(String userName, AuthToken authToken) {
        Message message = new Message("delete was successful");
        try {
            Account account = Account.getAccountWithUsername(userName);
            Account.deleteAccount(account);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message createManagerProfile(String name, String lastName, String email, String phoneNumber,
                                        String userName, String password, String creditString, AuthToken authToken) {
        Message message = new Message("manager created");
        if (Account.hasThisAccount(userName)) {
            message = new Message("Error");
            message.addToObjects(new LoginController.AccountIsAvailableException());
            return message;
        }
        double credit;
        try {
            credit = Double.parseDouble(creditString);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(new LoginController.CreditIsNotNumber());
            return message;
        }
        Account.addAccount(new Manager(name, lastName, email, phoneNumber, userName, password, credit,
                false));
        return message;
    }

    public Message manageAllProducts(AuthToken authToken) {
        Message message = new Message("all products");
        message.addToObjects(Product.getAllProducts());
        return message;
    }

    public Message mangerRemoveProduct(String productId, AuthToken authToken) {
        Message message = new Message("delete was successful");
        try {
            removeProduct(productId);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message createDiscountCode(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage,
                                      int maxNumberOfUsage, ArrayList<String> buyersWithDiscount, AuthToken authToken) {
        Message message = new Message("discount created");
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (String username : buyersWithDiscount) {
            try {
                buyers.add(Buyer.getBuyerWithUsername(username));
                new Discount(startDate, endDate, discountPercentage, maxNumberOfUsage, buyers);
            } catch (Exception e) {
                message = new Message("Error");
                message.addToObjects(e);
            }
        }
        return message;
    }

    public Message viewDiscountCodes(AuthToken authToken) {
        Message message = new Message("all discounts");
        message.addToObjects(Discount.getAllDiscounts());
        return message;
    }

    public Message viewDiscountCode(int discountCode, AuthToken authToken) {
        Message message = new Message("view discount");
        try {
            message.addToObjects(Discount.getDiscountByDiscountCode(discountCode));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message editDiscountPercentage(int discountCode, Double newOne, AuthToken authToken) {
        Message message = new Message("discount percentage edited");
        try {
            Discount discount = Discount.getDiscountByDiscountCode(discountCode);
            discount.setDiscountPercentage(newOne);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message editMaxDiscountUsage(int discountCode, int newOne, AuthToken authToken) {
        Message message = new Message("discount usage edited");
        try {
            Discount discount = Discount.getDiscountByDiscountCode(discountCode);
            discount.setMaxNumberOfUsage(newOne);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }


    public Message editStartDateOfDiscountCode(int id, LocalDateTime startDate, AuthToken authToken) {
        Message message = new Message("discount start date edited");
        try {
            Discount discount = Discount.getDiscountByDiscountCode(id);
            discount.setStartDate(startDate);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message editEndDateOfDiscount(int id, LocalDateTime endDate, AuthToken authToken) {
        Message message = new Message("discount end date edited");
        try {
            Discount discount = Discount.getDiscountByDiscountCode(id);
            discount.setStartDate(endDate);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeDiscountCodes(int discountCode, AuthToken authToken) {
        Message message = new Message("remove discount was successful");
        try {
            removeDiscountCode(discountCode);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message checkUsername(String userName, AuthToken authToken) {
        Message message = new Message("check username");
        try {
            message.addToObjects(getBuyerWithUsername(userName));
            return message;
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }


    public Message addBuyerToBuyersWithDiscount(int discountId, String username, AuthToken authToken) {
        Message message = new Message("add was successful");
        try {
            Buyer buyer = getBuyerWithUsername(username);
            Discount discount = Discount.getDiscountByDiscountCode(discountId);
            discount.addBuyerToBuyersList(buyer);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeBuyerFromBuyersWithDiscount(int discountId, String username, AuthToken authToken) {
        Message message = new Message("buyer removed");
        try {
            Buyer buyer = getBuyerWithUsername(username);
            Discount discount = Discount.getDiscountByDiscountCode(discountId);
            discount.removeBuyerFromBuyersList(buyer);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
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

    @Override
    protected void setMethods() {

    }

    public static class fieldIsInvalidException extends Exception {
        public fieldIsInvalidException() {
            super("field is invalid!");
        }
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

    @Override
    public Message editField(String field, String context, AuthToken authToken) {
        Manager currentManager = (Manager) Main.getAccountWithToken(authToken);
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
                Message message = new Message("Error");
                message.addToObjects(new ManagerController.fieldIsInvalidException());
                return message;
        }
        return new Message("edited");
    }

    public Message setProfileImage(String path, AuthToken authToken) {
        Manager currentManager = (Manager) Main.getAccountWithToken(authToken);
        currentManager.setImagePath(path);
        return new Message("set profile image was successful");
    }

    @Override
    public Message changeMainImage(Image image, AuthToken authToken) {
        Manager currentManager = (Manager) Main.getAccountWithToken(authToken);
        currentManager.getGraphicPackage().setMainImage(image);
        return new Message("change main image");
    }

    @Override
    public Message logout(AuthToken authToken) {
        Manager currentManager = (Manager) Main.getAccountWithToken(authToken);
        Main.removeFromTokenHashMap(authToken, currentManager);
        return new Message("logout was successful");
    }
}
