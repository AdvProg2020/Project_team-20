package controller.account.user;

import controller.MainController;
import controller.account.LoginController;
import javafx.scene.image.Image;
import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.Category;
import model.product.Discount;
import model.product.Product;
import model.product.RequestableState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static model.account.Manager.*;
import static model.product.Category.*;
import static model.product.Discount.removeDiscountCode;
import static model.product.Product.*;

public class ManagerController implements controller.account.user.AccountController {

    private MainController mainController;
    private static Manager currentManager;

    private static ManagerController managerController = null;

    private ManagerController() {
        this.mainController = MainController.getInstance();
    }

    public static ManagerController getInstance() {
        if (managerController == null)
            managerController = new ManagerController();
        currentManager = (Manager) MainController.getInstance().getAccount();
        return managerController;
    }


    //manage users
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
        double credit;
        try {
            credit = Double.parseDouble(creditString);
        } catch (Exception e) {
            throw new LoginController.CreditIsNotNumber();
        }
        Account.addAccount(new Manager(name, lastName, email, phoneNumber, userName, password, credit,
                false));
    }

    //products
    public ArrayList<Product> manageAllProducts() {
        return getAllProducts();
    }

    public void mangerRemoveProduct(String productId) throws Exception {
        removeProduct(productId);
    }

    //discount codes
   /* public void createDiscountCode(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage,
                                   int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) {
        new Discount(startDate, endDate, discountPercentage, maxNumberOfUsage, buyersWithDiscount);
    }*/

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

    //requests
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

    //category
    public ArrayList<Category> manageCategories() {
        return getAllCategories();
    }

    public void editCategoryName(String categoryName, String newName) throws Exception {
        Category category = getCategoryByName(categoryName);
        category.setName(newName);
    }

    public void removeFieldFromCategory(String name, String fieldName) throws Exception {
        Category category = getCategoryByName(name);
        category.removeField(fieldName);
    }

    public void removeSubCategoryFromAllSubCategories(String categoryName, String subCategoryName) throws Exception {
        Category category = getCategoryByName(categoryName);
        Category subCategory = getCategoryByName(subCategoryName);
        subCategory.setParent(null);
        category.removeSubCategory(subCategory);
    }

    public void removeProductFromCategory(String categoryName, String productName) throws Exception {
        Category category = getCategoryByName(categoryName);
        Product product = getProductWithItsName(productName);
        category.removeProduct(product);
    }

    public void addCategory(String categoryName, Category parent) throws Exception {
        Category category = new Category(categoryName, parent);
    }

    public void addCategory(String categoryName) throws Exception {
        Category category = new Category(categoryName);
    }

    public void managerRemoveCategory(String categoryName) throws Exception {
        removeCategory(categoryName);
    }

    public void addProductToCategory(String categoryName, String productName) throws Exception {
        Product product = Product.getProductWithItsName(productName);
        Category category = getCategoryByName(categoryName);
        category.addProduct(product);
    }

    public void addSubCategoryToCategory(String categoryName, String subCategoryName) throws Exception {
        Category category = getCategoryByName(categoryName);
        Category subCategory = getCategoryByName(subCategoryName);
        subCategory.setParent(category);
        category.addSubCategory(subCategory);
    }

    public void addNewFieldToCategory(String categoryName, String name) throws Exception {
        Category category = Category.getCategoryByName(categoryName);
        category.addField(name);
    }

    public void addParentToCategory(String categoryName, String parentName) throws Exception {
        Category category = getCategoryByName(categoryName);
        Category parentCategory = getCategoryByName(parentName);
        category.setParent(parentCategory);
        parentCategory.addSubCategory(category);
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

    @Override
    public void changeMainImage(Image image) {
        currentManager.getGraphicPackage().setMainImage(image);
    }

    @Override
    public void logout() {
        mainController.logout();
    }
}
