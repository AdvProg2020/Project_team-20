package view.account;

import controller.account.LoginController;
import model.account.Seller;
import view.Menu;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RegisterAndLogin extends Menu {
    public RegisterAndLogin(String name, Menu parent) {
        super(name);
        this.regex.add(0,"create account [manager|buyer|seller] (\\w+)");
        this.regex.add(1,"login (\\w+)");
        this.methods.add(0,"loginUser");
        this.methods.add(1,"registerUser");
        this.parent = parent;
    }

    public void loginUser(String username){
        System.out.println("please enter your password:");
        String password = Menu.scanner.nextLine();
        try{
            LoginController loginController = LoginController.getInstance();
            loginController.login(username,password);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            this.back();
        }
    }

    public void registerUser(String type , String username){
        System.out.println("please enter your password:");
        String password = Menu.scanner.nextLine();
        System.out.println("please enter your name:");
        String name = Menu.scanner.nextLine();
        System.out.println("please enter your lastName:");
        String lastName = Menu.scanner.nextLine();
        System.out.println("please enter your email:");
        String email = Menu.scanner.nextLine();
        System.out.println("please enter your phone number:");
        String phoneNumber = Menu.scanner.nextLine();
        System.out.println("Thank you:) " + "Please wait for your information to be verified");
        ArrayList<String> details = addDetails(name,lastName,email,phoneNumber,password);
        try{
            LoginController loginController = LoginController.getInstance();
            loginController.createAccount(username,type,details);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            this.back();
        }
    }

    public ArrayList<String> addDetails(String name,String lastName,String email,String phoneNumber,String password){
        ArrayList<String> detail = new ArrayList<>();
        detail.add(0,name);
        detail.add(1,lastName);
        detail.add(2,email);
        detail.add(3,phoneNumber);
        detail.add(4,password);
        return detail;
    }

    @Override
    public void show() {

    }
}
