package controller;

import view.MainMenu;
import view.Menu;

public class Main {
    private static Menu currentMenu;

    public static void main(String[] args) {
        initialMenus();

        while (true)
            currentMenu.  getCommand();
    }

    public static void setCurrentMenu(Menu currentMenu) {
        Main.currentMenu = currentMenu;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    private static void initialMenus() {
        currentMenu = new MainMenu();
    }
}
