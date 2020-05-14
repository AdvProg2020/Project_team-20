package view.account;

import controller.account.user.ManagerController;
import jdk.vm.ci.meta.Local;
import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.Category;
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
        this.regex.add(10,"view discount code (\\d+)");
        this.regex.add(11,"edit discount code (\\d+)");
        this.regex.add(12,"remove discount code (\\d+)");
        this.regex.add(13,"manage requests");
        this.regex.add(14,"details (\\d+)");
        this.regex.add(15,"accept (\\d+)");
        this.regex.add(16,"decline (\\d+)");
        this.regex.add(17,"manage categories");
        this.regex.add(18,"edit (\\w+)");
        this.regex.add(19,"add (\\w+)");
        this.regex.add(20,"remove (\\w+)");
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
        this.methods.add(10,"viewDiscountCodeWithId");
        this.methods.add(11,"editDiscountCode");
        this.methods.add(12,"removeDiscountCode");
        this.methods.add(13,"manageRequests");
        this.methods.add(14,"detailsOfRequest");
        this.methods.add(15,"acceptRequest");
        this.methods.add(16,"declineRequest");
        this.methods.add(17,"manageCategory");
        this.methods.add(18,"editCategory");
        this.methods.add(19,"addCategory");
        this.methods.add(20,"removeCategory");
    }

    //personalInfo
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

    //manage users
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

    //create manager profile
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

    //manage products
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

    //manage discounts
    public void createDiscountCode(){
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate;
        do {
            endDate = getEndDate();
        }
        while (endDate.isBefore(LocalDateTime.now()));
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

    public void viewDiscountCodeWithId(int id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
        try {
            Discount discount = managerController.viewDiscountCode(id);
            System.out.println("Discount code                        : "+discount.getDiscountCode());
            System.out.println("Discount percentage                  : "+discount.getDiscountPercentage());
            System.out.println("Maximum number of usage this discount: "+discount.getMaxNumberOfUsage());
            System.out.println("Start Date                           : "+formatter.format(discount.getStartDate()));
            System.out.println("End Date                             : "+formatter.format(discount.getEndDate()));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void editDiscountCode(int id){
        System.out.println("Fields:");
        System.out.println("1) Start Date"+"\n"+"2) End Date"+"\n"+"3) Discount Percentage"+"\n"+"4) maximum Number Of Usage"
        +"\n"+"5)edit buyers with this discount");
        System.out.println("please select field which you want to edit:");
        int n = Menu.scanner.nextInt();
        selectEditedField(n,id);
    }

    private void selectEditedField(int n , int id){
        switch (n){
            case 1:
                LocalDateTime startDate = getStartDate();
                try {
                    managerController.editStartDateOfDiscountCode(id,startDate);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                LocalDateTime endDate = getEndDate();
                try {
                    managerController.editEndDateOfDiscount(id,endDate);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                try {
                    System.out.println("please enter new discount percentage:");
                    Double newOne = Menu.scanner.nextDouble();
                    managerController.editDiscountPercentage(id , newOne);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                try {
                    System.out.println("please enter new maximum Number Of Usage:");
                    int newOne = Menu.scanner.nextInt();
                    managerController.editMaxDiscountUsage(id , newOne);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 5:
                editBuyersWithDiscount(id);
            default:
                System.out.println("invalid input :)");
                editDiscountCode(id);
        }
    }

    private void editBuyersWithDiscount(int discountId){
        System.out.println("please select the desired option:");
        System.out.println("1)remove buyer from list of buyers"+"\n"+"2)add buyer to list of buyers");
        int n = Menu.scanner.nextInt();
        System.out.println("please enter username:");
        String username = Menu.scanner.nextLine();
        switch (n){
            case 1:
                try{
                    managerController.removeBuyerFromBuyersWithDiscount(discountId,username);
                    return;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    editBuyersWithDiscount(discountId);
                }
                break;
            case 2:
                try{
                    managerController.addBuyerToBuyersWithDiscount(discountId,username);
                    return;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    editBuyersWithDiscount(discountId);
                }
                break;
            default:
                System.out.println("invalid input");
                editBuyersWithDiscount(discountId);
        }
    }

    public void removeDiscountCode(int id){
        try {
            managerController.removeDiscountCodes(id);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //manage requests
    public void manageRequests(){
       ArrayList<Requestable> requests = managerController.manageRequests();
       for(Requestable requestable:requests){
           System.out.println(requestable.toString());
       }
    }

    public void detailsOfRequest(int requestId){
        try {
           System.out.println(managerController.requestDetails(requestId));
        }
       catch (Exception e){
            System.out.println(e.getMessage());
       }
    }

    public void acceptRequest(int id){
        try {
            managerController.acceptRequest(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void declineRequest(int id){
        try {
            managerController.declineRequest(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //manage category
    public void manageCategory(){
       ArrayList<Category> categories = managerController.manageCategories();
       for(Category category:categories){
           System.out.println("Name : "+category.getName());
       }
    }

    public void editCategory(String name){
        System.out.println("fields:");
        System.out.println("1)category's name"+"\n"+"2)");
        System.out.println("please select field which you want to edit:");
    }

    public void addCategory(String name){
        addProductPart(name);
        addSubCategoryPart(name);
        addFieldPart(name);
        addParentPart(name);
    }

    private void addProductPart(String name){
        System.out.println("please write products which you want to add to this category:(write \"end\" when you reached end of products)");
        String productName = Menu.scanner.nextLine();
        while (!productName.equals("end")){
            try {
                managerController.addProductToCategory(name,productName);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            productName = Menu.scanner.nextLine();
        }
    }

    private void addSubCategoryPart(String name){
        System.out.println("please write subCategories:(write \"end\" when you reached end of subCategories)");
        String subcategoryName = Menu.scanner.nextLine();
        while (!subcategoryName.equals("end")){
            try {
                managerController.addSubCategoryToCategory(name,subcategoryName);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            subcategoryName = Menu.scanner.nextLine();
        }
    }

    private void addFieldPart(String name){
        System.out.println("Please enter \"end\" when fields which you want add finished");
        String fieldName = Menu.scanner.nextLine();
        while (!fieldName.equals("end")){
            try {
                managerController.addNewFieldToCategory(name,fieldName);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            fieldName = Menu.scanner.nextLine();
        }
    }

    private void addParentPart(String name){
        System.out.println("is it a subCategory of another Category?");
        System.out.println("1)yes"+"\n"+"2)No");
        int n = Menu.scanner.nextInt();
        switch (n){
            case 1:
                try {
                    System.out.println("please write parent category's name:");
                    String parentName = Menu.scanner.nextLine();
                    managerController.addParentToCategory(name , parentName);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                return;
            default:
                System.out.println("invalid input");
                addParentPart(name);
        }
    }

    public void removeCategory(String name){
        try {
            managerController.managerRemoveCategory(name);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {

    }
}
