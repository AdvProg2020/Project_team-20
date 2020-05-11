package controller.account.user;


import model.account.Manager;
import model.account.Account;
import  model.product.Discount;
import  model.account.Buyer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;


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

    public void removeProduct(String productId){
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
