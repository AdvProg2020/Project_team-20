package view.account;

import view.Menu;

public class ManagerMenu extends Menu {

    public ManagerMenu(String name) {
        super(name);
        this.regex.add(0,"view personal info");
        this.methods.add(0,"viewPersonalInfo");

    }

    public void viewPersonalInfo(){

    }


    @Override
    public void show() {

    }
}
