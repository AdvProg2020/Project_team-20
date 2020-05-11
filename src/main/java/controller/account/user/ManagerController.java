package controller.account.user;


import model.account.Manager;
import model.account.Account;
import  model.product.Discount;
import  model.product.Discount.buyerWithoutDiscount;
import  model.product.Discount.discountUnavailableException;
import  model.account.Buyer;
import  model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;



public class ManagerController implements controller.account.user.AccountController {

    public ArrayList<Account> manageUsers(){
        return Account.getAllAccounts();
    }

    // we should discuss
    public Account viewUser(String userName) throws Exception{
        Account account = Account.getAccountWithUsername(userName);
        return account;
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

    public ArrayList<Product> manageAllProducts(){
        return Product.getAllProducts();
    }

    public void removeProduct(String productId){
        removeProduct(productId);
    }

    public void createDiscountCode(LocalDateTime startDate , LocalDateTime endDate , String discountCode , double discountPercentage,
                                   int maxNumberOfUsage , ArrayList<Buyer> buyersWithDiscount){
        new Discount(startDate,endDate,discountCode,discountPercentage,maxNumberOfUsage,buyersWithDiscount);
    }


    public  ArrayList<Discount> viewDiscountCodes(){
        ArrayList<Discount> allDiscounts = Discount.getAllDiscounts();
        return allDiscounts;
    }

    public Discount viewDiscountCode(String discountCode) throws Exception {
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        return discount;
    }

    public void editDiscountCodes(String discountCode){
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
    }

    public void removeDiscountCodes(String discountCode) throws Exception{
        Discount discount = Discount.getDiscountByDiscountCode(discountCode);
        discount.deleteDiscount(discount);
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
