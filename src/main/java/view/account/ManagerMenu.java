package view.account;

import model.account.Account;
import model.account.Manager;
import view.Menu;

public class ManagerMenu extends Menu {

    private static ManagerMenu single_instance = null;

    private ManagerMenu() {
        super("ManagerMenu");
        this.regex.add(0,"view personal info");
        this.methods.add(0,"viewPersonalInfo");

    }
    public static ManagerMenu getInstance()
    {
        if (single_instance == null)
            single_instance = new ManagerMenu();

        return single_instance;
    }

    public void viewPersonalInfo(){

        Account account =
    }


    @Override
    public void show() {

    }
}
