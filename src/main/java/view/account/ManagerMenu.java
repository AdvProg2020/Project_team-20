package view.account;

import controller.account.user.ManagerController;
import jdk.vm.ci.meta.Local;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.Discount;
import model.product.Product;
import view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManagerMenu extends Menu {

    private static ManagerMenu single_instance = null;
    private ManagerController managerController = ManagerController.getInstance();

    private ManagerMenu() {
        super("ManagerMenu");
        setRegex();
        setMethods();
    }

    public static ManagerMenu getInstance() {
        if (single_instance == null)
            single_instance = new ManagerMenu();

        return single_instance;
    }

    private void setRegex(){
        this.regex.add(0,"view personal info");
        this.regex.add(1,"edit (\\w+)");
        this.regex.add(2,"manage users");
        this.regex.add(3,"view (\\w+)");
        this.regex.add(4,"delete user (\\w+)");
        this.regex.add(5,"create manager profile");
        this.regex.add(6,"manage all products");
        this.regex.add(7,"remove (\\w+)");
        this.regex.add(8,"create discount code");
        this.regex.add(9,"view discount codes");
    }

    private void setMethods(){
        this.methods.add(0,"viewPersonalInfo");
        this.methods.add(1,"editPersonalInfo");
        this.methods.add(2,"manageUsers");
        this.methods.add(3,"viewUserWithUserName");
        this.methods.add(4,"deleteUserWithUserName");
        this.methods.add(5,"createManagerProfile");
        this.methods.add(6,"manageAllProducts");
        this.methods.add(7,"removeProductWithID");
        this.methods.add(8,"createDiscountCode");
        this.methods.add(9,"viewDiscountCodes");
    }

    public void viewPersonalInfo() {
        Account account = managerController.getAccountInfo();
        System.out.println("UserName : " + account.getUsername() + "\n" + "Password : " + account.getPassword() + "\n" + "Name : " + account.getName() + "\n" + "LastName : " + account.getLastName() + "\n" + "Email : " + account.getEmail()
                + "\n" + "PhoneNumber : " + account.getPhoneNumber());
    }

    public void editPersonalInfo(String field){
        System.out.println("please write new field : ");
        String context = Menu.scanner.nextLine();
        managerController.editField(field , context);
    }

    public void manageUsers(){
        ArrayList<Account> accounts = managerController.manageUsers();
        for(Account account : accounts){
            System.out.println("UserName : "+account.getUsername());
        }
    }

    public void viewUserWithUserName(String username){
        try {
            Account account = managerController.viewUser(username);
            System.out.println("UserName : " + account.getUsername() + "\n" + "Password : " + account.getPassword() + "\n" + "Name : " + account.getName() + "\n" + "LastName : " + account.getLastName() + "\n" + "Email : " + account.getEmail()
                    + "\n" + "PhoneNumber : " + account.getPhoneNumber());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteUserWithUserName(String username){
        try{
            managerController.deleteUser(username);
            System.out.println("It was successfully removed");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void createManagerProfile(){
        System.out.println("please enter new manager's name:");
        String name = Menu.scanner.nextLine();
        System.out.println("please enter new manager's lastName:");
        String lastName = Menu.scanner.nextLine();
        System.out.println("please enter new manager's email:");
        String email = Menu.scanner.nextLine();
        System.out.println("please enter new manager's phoneNumber:");
        String phoneNumber = Menu.scanner.nextLine();
        System.out.println("please enter new manager's userName:");
        String userName = Menu.scanner.nextLine();
        System.out.println("please enter new manager's password:");
        String password = Menu.scanner.nextLine();
        managerController.createManagerProfile(name , lastName ,email , phoneNumber ,userName ,password , 0.0);
        System.out.println("The new manager was successfully added.");
    }

    public void manageAllProducts(){
        ArrayList<Product> products = managerController.manageAllProducts();
        for(Product product : products){
            System.out.println("Product Id: "+product.getId()+"\n"+"Product Name: "+product.getName());
        }
    }

    public void removeProductWithID(String productId){
        try {
            managerController.mangerRemoveProduct(productId);
            System.out.println("product was successfully removed.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void createDiscountCode(){
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();
        double percentage;
        do {
            System.out.println("please write discount percentage:");
            percentage = Menu.scanner.nextInt();
        }
        while(percentage > 100 || percentage < 0);
        System.out.println("please write maximum number of usage this discount:");
        int maxNumberOfUsage = Menu.scanner.nextInt();
        System.out.println("please write number of users who can use this discount:");
        int n = Menu.scanner.nextInt();
        int i=0;
        ArrayList<Buyer> buyersWithDiscount = new ArrayList<>();
        System.out.println("please write the username of those who can use this discount:");
        while(i<n){
            String userName = Menu.scanner.nextLine();
            try {
               Buyer buyer = managerController.checkUsername(userName);
               buyersWithDiscount.add(buyer);
               i++;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        managerController.createDiscountCode(startDate , endDate ,percentage,maxNumberOfUsage,buyersWithDiscount);
        System.out.println("discountCode was successfully created.");
    }

    private LocalDateTime getStartDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
        System.out.println("please write discount start date and time (your format should be like this --> MM/dd/yyyy 'at' hh:mm PM/AM):");
        String dateTime = Menu.scanner.nextLine();
        LocalDateTime startDate;
        try {
            startDate = LocalDateTime.parse(dateTime, formatter);
            return startDate;
        }
        catch (Exception e){
            System.out.println("Input is invalid");
            startDate = getStartDate();
        }
        return startDate;
    }

    private LocalDateTime getEndDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
        System.out.println("please write discount start date and time (your format should be like this --> MM/dd/yyyy 'at' hh:mm PM/AM):");
        String endDateTime = Menu.scanner.nextLine();
        LocalDateTime endDate;
        try {
            endDate = LocalDateTime.parse(endDateTime, formatter);
            return endDate;
        }
        catch (Exception e) {
            System.out.println("Input is invalid");
            endDate = getEndDate();
        }
        return endDate;
    }

    public void viewDiscountCodes(){
        ArrayList<Discount> discounts = managerController.viewDiscountCodes();
        int i = 1;
        for(Discount discount:discounts){
            System.out.println(i+")");
            System.out.println("discountCode: "+discount.getDiscountCode());
            System.out.println("discountPercentage: "+discount.getDiscountPercentage());
            i++;
        }
    }
    

    @Override
    public void show() {

    }
}
