package controller;

import view.MainMenu;
import view.Menu;
import view.account.RegisterAndLoginMenu;
import view.product.AllProductsMenu;

public class Main {
    private static Menu currentMenu;
    private static PreProcess preProcess;

    public static void main(String[] args) {
        initialMenus();
        //preProcess.processOnlyOneTime();
        while (true) {
            //preProcess.doPreProcess();
            currentMenu.getCommand();
        }
    }

    public static void setCurrentMenu(Menu currentMenu) {
        Main.currentMenu = currentMenu;
    }

    private static void initialMenus() {
        currentMenu = new MainMenu();
        Menu.addToAllMenus(RegisterAndLoginMenu.getInstance());
        Menu.addToAllMenus(AllProductsMenu.getInstance());
    }


}
