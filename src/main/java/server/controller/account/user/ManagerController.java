package server.controller.account.user;

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
import javafx.scene.image.Image;
import server.controller.Main;
import server.controller.account.LoginController;
import server.network.server.Server;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static client.model.account.Manager.*;
import static client.model.product.Discount.removeDiscountCode;
import static client.model.product.Product.getProductWithItsName;
import static client.model.product.Product.removeProduct;
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

    public Message manageRequests(AuthToken authToken) {
        Message message = new Message("requests");
        message.addToObjects(getRequestWithIds());
        return message;
    }

    public Message requestDetails(int requestId, AuthToken authToken) {
        Message message = new Message("details of request");
        try {
            message.addToObjects(findRequestWithId(requestId).toString());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message acceptRequest(int requestId, AuthToken authToken) {
        Message message = new Message("request accepted");
        try {
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
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message declineRequest(int requestId, AuthToken authToken) {
        Message message = new Message("request declined");
        try {
            Requestable request = findRequestWithId(requestId);
            request.changeStateRejected();
            deleteRequest(request, requestId);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message manageSubCategories(AuthToken authToken) {
        Message message = new Message("sub categories");
        message.addToObjects(getAllSubCategories());
        return message;
    }

    public Message manageCategorySets(AuthToken authToken) {
        Message message = new Message("category sets");
        message.addToObjects(getAllCategorySets());
        return message;
    }

    public Message manageAllCategories(AuthToken authToken) {
        Message message = new Message("all categories");
        message.addToObjects(Category.getAllCategories());
        return message;
    }

    public Message editCategoryName(String categoryName, String newName, AuthToken authToken) {
        Message message = new Message("category name edited");
        try {
            Category category = Category.getCategoryByName(categoryName);
            category.setName(newName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeFieldFromCategory(String name, String fieldName, AuthToken authToken) {
        Message message = new Message("field removed");
        try {
            SubCategory subCategory = getCategoryByName(name);
            subCategory.removeField(fieldName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeSubCategoryFromAllSubCategories(String categoryName, String subCategoryName, AuthToken authToken) {
        Message message = new Message("sub category removed");
        try {
            CategorySet categorySet = getCategorySetByName(categoryName);
            SubCategory subCategory = getCategoryByName(subCategoryName);
            subCategory.setParent(null);
            categorySet.removeSubCategory(subCategory);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeCategorySetFromCategorySet(String parentName, String subName, AuthToken authToken) {
        Message message = new Message("category set removed");
        try {
            CategorySet parentCategorySet = getCategorySetByName(parentName);
            CategorySet categorySet = getCategorySetByName(subName);
            parentCategorySet.removeSubCategorySet(categorySet);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message removeProductFromCategory(String categoryName, String productName, AuthToken authToken) {
        Message message = new Message("product removed from category");
        try {
            SubCategory subCategory = getCategoryByName(categoryName);
            Product product = getProductWithItsName(productName);
            subCategory.removeProduct(product);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addCategory(String categoryName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            new SubCategory(categoryName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addCategorySet(String categorySetName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            new CategorySet(categorySetName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message managerRemoveCategory(String categoryName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            removeCategory(categoryName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message managerRemoveCategorySet(String categorySetName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            CategorySet.removeCategorySet(categorySetName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addFieldToCategory(String categoryName, String fieldName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            SubCategory subCategory = getCategoryByName(categoryName);
            subCategory.addField(fieldName);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addSubCategoryToCategorySet(String categoryName, String subCategoryName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            CategorySet categorySet = getCategorySetByName(categoryName);
            SubCategory subCategory = getCategoryByName(subCategoryName);
            subCategory.setParent(categorySet);
            categorySet.addToCategories(subCategory);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addCategorySetToCategorySet(String parentName, String subName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            CategorySet parentCategorySet = getCategorySetByName(parentName);
            CategorySet categorySet = getCategorySetByName(subName);
            parentCategorySet.addToCategorySets(categorySet);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addNewFieldToCategory(String categoryName, String name, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            SubCategory subCategory = SubCategory.getCategoryByName(categoryName);
            subCategory.addField(name);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addParentToCategory(String categoryName, String parentName, AuthToken authToken) {
        Message message = new Message("category added");
        try {
            SubCategory subCategory = getCategoryByName(categoryName);
            CategorySet parentCategory = getCategorySetByName(parentName);
            subCategory.setParent(parentCategory);
            parentCategory.addToCategories(subCategory);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    protected void setMethods() {
        methods.add("manageUsers");
        methods.add("viewUser");
        methods.add("deleteUser");
        methods.add("createManagerProfile");
        methods.add("manageAllProducts");
        methods.add("mangerRemoveProduct");
        methods.add("createDiscountCode");
        methods.add("viewDiscountCodes");
        methods.add("viewDiscountCode");
        methods.add("editDiscountPercentage");
        methods.add("editMaxDiscountUsage");
        methods.add("editStartDateOfDiscountCode");
        methods.add("editEndDateOfDiscount");
        methods.add("removeDiscountCodes");
        methods.add("checkUsername");
        methods.add("addBuyerToBuyersWithDiscount");
        methods.add("removeBuyerFromBuyersWithDiscount");
        methods.add("manageRequests");
        methods.add("requestDetails");
        methods.add("acceptRequest");
        methods.add("declineRequest");
        methods.add("manageSubCategories");
        methods.add("manageCategorySets");
        methods.add("manageAllCategories");
        methods.add("editCategoryName");
        methods.add("removeFieldFromCategory");
        methods.add("removeSubCategoryFromAllSubCategories");
        methods.add("removeCategorySetFromCategorySet");
        methods.add("removeProductFromCategory");
        methods.add("addCategory");
        methods.add("addCategorySet");
        methods.add("managerRemoveCategory");
        methods.add("managerRemoveCategorySet");
        methods.add("addFieldToCategory");
        methods.add("addSubCategoryToCategorySet");
        methods.add("addCategorySetToCategorySet");
        methods.add("addNewFieldToCategory");
        methods.add("addParentToCategory");
        methods.add("getAccountInfo");
        methods.add("editField");
        methods.add("setProfileImage");
        methods.add("changeMainImage");
        methods.add("logout");
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

    @Override
    public Message callCommand(String command, Message message) throws InvalidCommand {
        return super.callCommand(command, message);
    }
}
