package server.controller.account.user;

import javafx.scene.image.Image;
import server.controller.Main;
import server.controller.account.LoginController;
import server.model.Requestable;
import server.model.account.Account;
import server.model.account.Buyer;
import server.model.account.Manager;
import server.model.product.Discount;
import server.model.product.Product;
import server.model.product.RequestableState;
import server.model.product.category.Category;
import server.model.product.category.CategorySet;
import server.model.product.category.SubCategory;
import server.network.AuthToken;
import server.network.Message;
import server.network.server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static server.model.account.Manager.*;
import static server.model.product.Discount.removeDiscountCode;
import static server.model.product.Product.*;
import static server.model.product.category.CategorySet.getAllCategorySets;
import static server.model.product.category.CategorySet.getCategorySetByName;
import static server.model.product.category.SubCategory.*;

public class ManagerController extends Server implements AccountController {

    private static ManagerController managerController = null;

    private ManagerController() {
        super(7000);
    }

    public static ManagerController getInstance() {
        if (managerController == null)
            managerController = new ManagerController();
        return managerController;
    }


    public ArrayList<Account> manageUsers() {
        return getAllAccounts();
    }

    public Account viewUser(String userName) throws Exception {
        return Account.getAccountWithUsername(userName);
    }

    public void deleteUser(String userName) throws Exception {
        Account account = Account.getAccountWithUsername(userName);
        Account.deleteAccount(account);
    }

    public void createManagerProfile(String name, String lastName, String email, String phoneNumber,
                                     String userName, String password, String creditString) throws Exception {
        if (Account.hasThisAccount(userName))
            throw new LoginController.AccountIsAvailableException();
        double credit;
        try {
            credit = Double.parseDouble(creditString);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
        Account.addAccount(new Manager(name, lastName, email, phoneNumber, userName, password, credit,
                false));
    }

    public ArrayList<Product> manageAllProducts() {
        return getAllProducts();
    }

    public void mangerRemoveProduct(String productId) throws Exception {
        removeProduct(productId);
    }

    public void createDiscountCode(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage,
                                   int maxNumberOfUsage, ArrayList<String> buyersWithDiscount) throws Exception {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (String username : buyersWithDiscount) {
            checkUsername(username);
            buyers.add(Buyer.getBuyerWithUsername(username));
        }
        new Discount(startDate, endDate, discountPercentage, maxNumberOfUsage, buyers);
    }

    public ArrayList<Discount> viewDiscountCodes() {
        return Discount.getAllDiscounts();
    }

    public void addNewBuyerToBuyersWithDiscount(int discountId) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountId);

    }

    public Discount viewDiscountCode(int discountCode) throws Exception {
        return Discount.getDiscountByDiscountCode(discountCode);
    }

    public void editDiscountPercentage(int discountCode, Double newOne) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.setDiscountPercentage(newOne);
    }

    public void editDiscountPercentage(int discountCode, String newOneString) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        double newOne;
        try {
            newOne = Double.parseDouble(newOneString);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
        discount.setDiscountPercentage(newOne);
    }

    public void editMaxDiscountUsage(int discountCode, int newOne) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.setMaxNumberOfUsage(newOne);
    }

    public void editMaxDiscountUsage(int discountCode, String newOneString) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        int newOne;
        try {
            newOne = Integer.parseInt(newOneString);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
        discount.setMaxNumberOfUsage(newOne);
    }

    public void editStartDateOfDiscountCode(int id, LocalDateTime startDate) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(id);
        discount.setStartDate(startDate);
    }

    public void editEndDateOfDiscount(int id, LocalDateTime endDate) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(id);
        discount.setEndDate(endDate);
    }

    public void removeDiscountCodes(int discountCode) throws Exception {
        removeDiscountCode(discountCode);
    }

    public Buyer checkUsername(String userName) throws Exception {
        return getBuyerWithUsername(userName);
    }


    public void addBuyerToBuyersWithDiscount(int discountId, String username) throws Exception {
        Buyer buyer = getBuyerWithUsername(username);
        Discount discount = Discount.getDiscountByDiscountCode(discountId);
        discount.addBuyerToBuyersList(buyer);
    }

    public void removeBuyerFromBuyersWithDiscount(int discountId, String username) throws Exception {
        Buyer buyer = getBuyerWithUsername(username);
        Discount discount = Discount.getDiscountByDiscountCode(discountId);
        discount.removeBuyerFromBuyersList(buyer);
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
                Message message = new Message("edit request sent");
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
