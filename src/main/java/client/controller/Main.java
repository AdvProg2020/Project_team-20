package client.controller;

import client.model.account.Buyer;
import client.model.account.Manager;
import client.model.account.Seller;
import client.model.product.*;
import client.model.product.category.SubCategory;
import client.model.product.category.CategorySet;
import client.model.receipt.BuyerReceipt;
import client.model.receipt.SellerReceipt;
import client.network.Client;
import client.network.Message;
import client.view.console.MainMenu;
import client.view.console.Menu;
import client.view.console.account.RegisterAndLoginMenu;
import client.view.console.product.AllProductsMenu;
import client.view.console.product.SaleMenu;
import client.view.graphic.ProgramApplication;

public class Main {
    private static Menu currentMenu;
    private static PreProcess preProcess = new PreProcess();

    public static void main(String[] args) {
        //loadData();
        if (PreProcess.getPeriod() == 10)
            preProcess.processOnlyOneTime();
        PreProcess.AddPeriod();

        // for graphic client.view
        ProgramApplication.setFirstManager(receiveHasFirstManager());
        ProgramApplication.startApp(args);
    }


    // for console client.view
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

    private static boolean receiveHasFirstManager() {
        Client client = new Client(777);
        client.readMessage();
        client.writeMessage(new Message("sendHasFirstManager"));
        Message answer = client.readMessage();
        boolean hasFirstManager  = (boolean) answer.getObjects().get(0);
        client.writeMessage(new Message("buy"));
        return hasFirstManager;
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

