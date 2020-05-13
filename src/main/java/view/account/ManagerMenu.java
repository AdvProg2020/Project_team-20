package view.account;

import controller.account.user.ManagerController;
import model.account.Account;
import model.account.Manager;
import view.Menu;

import java.util.ArrayList;

public class ManagerMenu extends Menu {

    private static ManagerMenu single_instance = null;

    private ManagerMenu() {
        super("ManagerMenu");
        this.regex.add(0,"view personal info");
        this.methods.add(0,"viewPersonalInfo");
        this.regex.add(1,"edit (\\w+)");
        this.methods.add(1,"editPersonalInfo");
        this.regex.add(2,"manage users");
        this.methods.add(2,"manageUsers");
        this.regex.add(3,"view (\\w+)");
        this.methods.add(3,"viewUserWithUserName");
        this.regex.add(4,"delete user (\\w+)");
        this.methods.add(4,"deleteUserWithUserName");
    }

    public static ManagerMenu getInstance() {
        if (single_instance == null)
            single_instance = new ManagerMenu();

        return single_instance;
    }

    public void viewPersonalInfo() {
        ManagerController managerController = ManagerController.getInstance();
        Account account = managerController.getAccountInfo();
        System.out.println("UserName : " + account.getUsername() + "\n" + "Password : " + account.getPassword() + "\n" + "Name : " + account.getName() + "\n" + "LastName : " + account.getLastName() + "\n" + "Email : " + account.getEmail()
                + "\n" + "PhoneNumber : " + account.getPhoneNumber());
    }

    public void editPersonalInfo(String field){
        System.out.println("please write new field : ");
        String context = Menu.scanner.nextLine();
        ManagerController managerController = ManagerController.getInstance();
        managerController.editField(field , context);
    }

    public void manageUsers(){
        ManagerController managerController = ManagerController.getInstance();
        ArrayList<Account> accounts = managerController.manageUsers();
        for(Account account : accounts){
            System.out.println("UserName : "+account.getUsername());
        }
    }

    public void viewUserWithUserName(String username){
        ManagerController managerController = ManagerController.getInstance();
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
        ManagerController managerController = ManagerController.getInstance();
        try{
            managerController.deleteUser(username);
            System.out.println("It was successfully removed");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void show() {

    }
}
