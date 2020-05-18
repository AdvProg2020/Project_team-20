package controller;

import model.account.Account;
import view.MainMenu;
import view.Menu;
import view.account.RegisterAndLoginMenu;
import view.product.AllProductsMenu;
import view.product.SaleMenu;

public class Main {
    private static Menu currentMenu;
    private static PreProcess preProcess = new PreProcess();

    public static void main(String[] args) {
        initialMenus();
        //data base rp zadi inja ye boolean bezar ke hey nasaze
        preProcess.processOnlyOneTime();
        Account.store();
        while (true) {
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
        Menu.addToAllMenus(SaleMenu.getInstance());
    }
}
