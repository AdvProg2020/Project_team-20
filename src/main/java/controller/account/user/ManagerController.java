package controller.account.user;

import controller.MainController;
import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.Category;
import model.product.Discount;
import model.product.Field.Field;
import model.product.Product;
import model.product.RequestableState;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static model.account.Manager.*;
import static model.product.Category.*;
import static model.product.Discount.removeDiscountCode;
import static model.product.Product.getAllProducts;
import static model.product.Product.removeProduct;

public class ManagerController implements controller.account.user.AccountController {

    private MainController mainController;
    private Manager currentManager;

    private static ManagerController managerController = null;

    private ManagerController() {
        this.mainController = MainController.getInstance();
        currentManager = (Manager) mainController.getAccount();
    }

    public static ManagerController getInstance()
    {
        if (managerController == null)
            managerController = new ManagerController();

        return managerController;
    }


    //manage users
    public ArrayList<Account> manageUsers(){
      return getAllAccounts();
    }

    public Account viewUser(String userName) throws Exception{
        return Account.getAccountWithUsername(userName);
    }

    public void deleteUser(String userName) throws Exception{
            Account account = Account.getAccountWithUsername(userName);
            Account.deleteAccount(account);
    }

    public void createManagerProfile(String name,String lastName,String email,String phoneNumber,String userName,String password, double credit ){
       Manager manager = new Manager(name,lastName,email,phoneNumber , userName , password ,credit , false);
    }

    //products
    public ArrayList<Product> manageAllProducts(){
        return getAllProducts();
    }

    public void mangerRemoveProduct(String productId) throws Exception {
        removeProduct(productId);
    }

    //discount codes
    public void createDiscountCode(LocalDateTime startDate , LocalDateTime endDate ,  double discountPercentage,
                                   int maxNumberOfUsage , ArrayList<Buyer> buyersWithDiscount){
        new Discount(startDate,endDate,discountPercentage,maxNumberOfUsage,buyersWithDiscount);
    }

    public ArrayList<Discount> viewDiscountCodes() {
        return Discount.getAllDiscounts();
    }

    public void addNewBuyerToBuyersWithDiscount(int discountId) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(discountId);

    }

    public Discount viewDiscountCode(int discountCode) throws Exception{
        return Discount.getDiscountByDiscountCode(discountCode);
    }

    public void editDiscountPercentage(int discountCode , Double newOne) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.setDiscountPercentage(newOne);
    }

    public void editMaxDiscountUsage(int discountCode , int newOne) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.setMaxNumberOfUsage(newOne);
    }

    public void editStartDateOfDiscountCode(int id , LocalDateTime startDate) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(id);
        discount.setStartDate(startDate);
    }

    public void editEndDateOfDiscount(int id , LocalDateTime endDate) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(id);
        discount.setEndDate(endDate);
    }

    public void removeDiscountCodes(int discountCode) throws Exception{
        removeDiscountCode(discountCode);
    }

    public Buyer checkUsername(String userName) throws Exception{
        return getBuyerWithUsername(userName);
    }


    public void addBuyerToBuyersWithDiscount(int discountId , String username) throws Exception{
        Buyer buyer = getBuyerWithUsername(username);
        Discount discount = Discount.getDiscountByDiscountCode(discountId);
        discount.addBuyerToBuyersList(buyer);
    }

    public void removeBuyerFromBuyersWithDiscount(int discountId , String username) throws Exception{
        Buyer buyer = getBuyerWithUsername(username);
        Discount discount = Discount.getDiscountByDiscountCode(discountId);
        discount.removeBuyerFromBuyersList(buyer);
    }

    //requests
    public ArrayList<Requestable> manageRequests(){
        return getRequests();
    }

    public String requestDetails(int requestId) throws Exception{
       Requestable request  = findRequestWithId(requestId);
       return request.toString();
    }

    public void acceptRequest(int requestId) throws Exception{
        Requestable request  = findRequestWithId(requestId);
        RequestableState state = request.getState();
        switch (state){
            case EDITED:
                request.edit();
                break;
            case CREATED:
                request.changeStateAccepted();
                break;
        }
        deleteRequest(request, requestId);
    }

    public void declineRequest(int requestId) throws Exception{
        Requestable request  = findRequestWithId(requestId);
        request.changeStateRejected();
        deleteRequest(request, requestId);
    }

    //category
    public ArrayList<Category> manageCategories(){
        return getAllCategories();
    }

    public void editCategory(String categoryName, String newName,Field field ) throws Exception{
      Category category = getCategoryByName(categoryName);
      category.setName(newName);
      category.addField(field);
    }

    public void editCategory(String categoryName,Field field ) throws Exception{
        Category category = getCategoryByName(categoryName);
        category.addField(field);
    }

    public void addCategory(String categoryName , Category parent){
        Category category = new Category(categoryName,parent);
        AddToCategories(category);
    }

    public void addCategory(String categoryName){
        Category category = new Category(categoryName);
        AddToCategories(category);
    }

    public void managerRemoveCategory(String categoryName) throws Exception{
       removeCategory(categoryName);
    }


    @Override
    public Account getAccountInfo() {
        return currentManager;
    }

    @Override
    public void editField(String field, String context) {
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
        }
    }

    @Override
    public void logout() {
        mainController.logout();
    }
}
