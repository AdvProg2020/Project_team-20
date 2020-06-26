package controller;

import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.*;
import model.product.category.SubCategory;
import model.product.category.CategorySet;
import model.receipt.BuyerReceipt;
import model.receipt.SellerReceipt;
import view.console.MainMenu;
import view.console.Menu;
import view.console.account.RegisterAndLoginMenu;
import view.console.product.AllProductsMenu;
import view.console.product.SaleMenu;
import view.graphic.ProgramApplication;

public class Main {
    private static Menu currentMenu;
    private static PreProcess preProcess = new PreProcess();

    public static void main(String[] args) {
        loadData();
        if (PreProcess.getPeriod() == 10)
            preProcess.processOnlyOneTime();
        PreProcess.AddPeriod();

        // for graphic view
        ProgramApplication.setFirstManager(Manager.isHasFirstManger());
        ProgramApplication.startApp(args);
    }


    // for console view
/*
       initialMenus();
       while (true) {
            if (PreProcess.getPeriod() >= 3)
                try {
                    if (BuyerController.getInstance().getCurrentBuyer() != null)
                        preProcess.purchaseGift();
                } catch (Exception ignored) {
                }
            currentMenu.getCommand();
        }

    }

 */

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
        SubCategory.load();
        CategorySet.load();
        Discount.load();
        Product.load();
        Sale.load();
        SellerReceipt.load();
        BuyerReceipt.load();
        Advertisement.load();
    }

    public static void storeData() {
        PreProcess.store();
        Manager.store();
        Buyer.store();
        Seller.store();
        SubCategory.store();
        CategorySet.store();
        Discount.store();
        Product.store();
        Sale.store();
        SellerReceipt.store();
        BuyerReceipt.store();
        Advertisement.store();
    }
}

