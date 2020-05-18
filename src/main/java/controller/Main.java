package controller;

import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.Category;
import model.product.Discount;
import model.product.Product;
import model.product.Sale;
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
        loadData();
        if (PreProcess.getPeriod() == 10)
            preProcess.processOnlyOneTime();
        PreProcess.AddPeriod();
        while (true) {
            if (PreProcess.getPeriod() == 3)
                preProcess.purchaseGift();
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

    private static void loadData() {
        PreProcess.load();
        Manager.load();
        Buyer.load();
        Seller.load();
        Category.load();
        Discount.load();
        Product.load();
        Sale.load();
    }

    public static void storeData() {
        PreProcess.store();
        Manager.store();
        Buyer.store();
        Seller.store();
        Category.store();
        Discount.store();
        Product.store();
        Sale.store();
    }
}
