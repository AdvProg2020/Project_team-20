package view.account;

import controller.account.LoginController;
import model.account.AccountType;
import view.Menu;

import java.util.ArrayList;

public class RegisterAndLoginMenu extends Menu {

    private static RegisterAndLoginMenu single_instance = null;

    private RegisterAndLoginMenu() {
        super("RegisterAndLoginMenu");
        this.regex.add("create account (manager|buyer|seller) (\\w+)");
        this.regex.add("login (\\w+)");
        this.methods.add("registerUser");
        this.methods.add("loginUser");
        preProcess();
    }

    public static RegisterAndLoginMenu getInstance() {
        if (single_instance == null)
            single_instance = new RegisterAndLoginMenu();
        return single_instance;
    }

    public void loginUser(String username){
        System.out.println("please enter your password:");
        String password = Menu.scanner.nextLine();
        try{
            LoginController loginController = LoginController.getInstance();
            AccountType type = loginController.login(username,password);
            if (type.equals(AccountType.MANAGER)) {
                enter(ManagerMenu.getInstance());
            } else if (type.equals(AccountType.BUYER)) {
                enter(BuyerMenu.getInstance());
            } else {
                enter(SellerMenu.getInstance());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            this.back();
        }
    }

    public void registerUser(String type , String username){
        System.out.println("please enter your password:");
        String password = scanner.nextLine();
        System.out.println("please enter your name:");
        String name = scanner.nextLine();
        System.out.println("please enter your lastName:");
        String lastName = scanner.nextLine();
        System.out.println("please enter your email:");
        String email = scanner.nextLine();
        System.out.println("please enter your phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("please enter your credit");
        String credit = scanner.nextLine();
        System.out.println("Thank you:) " + "Please wait for your information to be verified");
        ArrayList<String> details = addDetails(name,lastName,email,phoneNumber,password,credit);
        try{
            LoginController loginController = LoginController.getInstance();
            loginController.createAccount(username,type,details);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            this.back();
        }
    }

    private ArrayList<String> addDetails(String name,String lastName,String email,String phoneNumber,String password,
                                         String credit){
        ArrayList<String> detail = new ArrayList<>();
        detail.add(name);
        detail.add(lastName);
        detail.add(email);
        detail.add(phoneNumber);
        detail.add(password);
        detail.add(credit);
        return detail;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void help() {
        super.help();
    }

    @Override
    public void enterWithName(String subMenuName) {
        super.enterWithName(subMenuName);
    }

    @Override
    public void back() {
        super.back();
    }

}
