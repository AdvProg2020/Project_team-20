package controller.account.user;

import controller.MainController;
import model.Requestable;
import model.account.Manager;
import model.account.Account;
import model.product.Category;
import  model.product.Discount;
import  model.account.Buyer;
import model.product.Field.Field;
import model.product.Product;
import model.product.RequestableState;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static controller.MainController.mainController;
import static model.account.Manager.*;
import static model.product.Category.*;
import static model.product.Discount.removeDiscountCode;
import static model.product.Product.getAllProducts;
import static model.product.Product.removeProduct;

public class ManagerController implements controller.account.user.AccountController {

    MainController mainController;
    Manager currentManager;

    private static ManagerController managerController = null;

    private ManagerController() {
        this.mainController = MainController.getInstance();
        currentManager = (Manager)mainController.getAccount();
    }


    //manage users
    public ArrayList<Account> manageUsers(){
      return getAllAccounts();
    }

    public Account viewUser(String userName) throws Exception{
        Account account1 = Account.getAccountWithUsername(userName);
        return account1;
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
    public void createDiscountCode(LocalDateTime startDate , LocalDateTime endDate , String discountCode , double discountPercentage,
                                   int maxNumberOfUsage , ArrayList<Buyer> buyersWithDiscount){
        new Discount(startDate,endDate,discountCode,discountPercentage,maxNumberOfUsage,buyersWithDiscount);
    }

    public void viewDiscountCodes(){
        ArrayList<Discount> allDiscounts = Discount.getAllDiscounts();
    }

    public Discount viewDiscountCode(String discountCode) throws Exception{
        Discount discount1 = Discount.getDiscountByDiscountCode(discountCode);
        return discount1;
    }

    public void editDiscountCodes(LocalDateTime startDate,LocalDateTime endDate,String discountCode, double discountPercentage, int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.editDiscount(startDate,endDate,discountPercentage,maxNumberOfUsage,buyersWithDiscount);
    }

    public void removeDiscountCodes(String discountCode) throws Exception{
        removeDiscountCode(discountCode);
    }

    //requests
    public ArrayList<Requestable> manageRequests(){
        return getRequests();
    }

    public String requestDetails(String requestId){
       Requestable request  = findRequestWithId(requestId);
       return request.toString();
    }

    public void acceptRequest(String requestId){
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
    }

    public void declineRequest(String requestId){
        Requestable request  = findRequestWithId(requestId);
        request.changeStateRejected();
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
