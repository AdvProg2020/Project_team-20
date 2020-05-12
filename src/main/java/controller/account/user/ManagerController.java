package controller.account.user;


import model.Requestable;
import model.account.Manager;
import model.account.Account;
import model.product.Category;
import  model.product.Discount;
import  model.account.Buyer;
import model.product.Field.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static model.account.Manager.FindRequestWithId;
import static model.account.Manager.getRequests;
import static model.product.Category.*;
import static model.product.Product.removeProduct;

public class ManagerController implements controller.account.user.AccountController {

    public void manageUsers(){

    }
    // we should discuss
    public void viewUser(String userName){

    }

    public void deleteUser(String userName){
        try {
            Account account = Account.getAccountWithUsername(userName);
            Account.deleteAccount(account);
        }
        catch(Exception AccountUnavailableException){
           // throw new AccountUnavailableException();
        }
    }

    public void createManagerProfile(String[] details , double credit ){
        String name = details[0];
        String lastName = details[1];
        String email = details[2];
        String phoneNumber = details[3];
        String userName = details[4];
        String password = details[5];
       Manager manager = new Manager(name,lastName,email,phoneNumber , userName , password ,credit , false);
    }

    public void manageAllProducts(){

    }

    public void mangerRemoveProduct(String productId) throws Exception {
        removeProduct(productId);
    }

    public void createDiscountCode(LocalDateTime startDate , LocalDateTime endDate , String discountCode , double discountPercentage,
                                   int maxNumberOfUsage , ArrayList<Buyer> buyersWithDiscount){
        new Discount(startDate,endDate,discountCode,discountPercentage,maxNumberOfUsage,buyersWithDiscount);
    }

    // we should discuss
    public void viewDiscountCodes(){
        ArrayList<Discount> allDiscounts = Discount.getAllDiscounts();
    }
    // we should discuss
    public void viewDiscountCode(String discountCode){
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
    }

    public void editDiscountCodes(String discountCode){
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
    }

    //requests
    public ArrayList<Requestable> manageRequests(){
        return getRequests();
    }

    public String requestDetails(String requestId){
       Requestable request  = FindRequestWithId(requestId);
       return request.toString();
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
        return null;
    }

    @Override
    public void editField(String field, String context) {

    }

    @Override
    public void logout() {

    }
}
