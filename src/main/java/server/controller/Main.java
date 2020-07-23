package server.controller;

import client.model.account.*;
import client.model.bank.BankReceipt;
import client.network.Client;
import server.HasFirstManager;
import server.controller.account.LoginController;
import server.controller.account.user.BuyerController;
import server.controller.account.user.ManagerController;
import server.controller.account.user.SellerController;
import server.controller.account.user.SupporterController;
import server.controller.bank.BankServer;
import client.model.product.*;
import client.model.product.category.SubCategory;
import client.model.product.category.CategorySet;
import client.model.receipt.BuyerReceipt;
import client.model.receipt.SellerReceipt;
import client.network.AuthToken;
import server.controller.chat.ChatController;
import server.controller.product.AdvertisementController;
import server.controller.product.ProductController;
import server.controller.product.ReceiptController;
import server.controller.product.filter.AllProductsController;
import server.controller.product.filter.SaleController;
import server.network.server.DNS;
import server.network.server.Server;


import java.util.HashMap;

public class Main {
    private static PreProcess preProcess = new PreProcess();
    private static HashMap<String, GeneralAccount> authTokenAccountHashMap = new HashMap<>();
    private static boolean hasFirstManager = false;

    public static void main(String[] args) {
        loadData();
        if (PreProcess.getPeriod() == 10)
            preProcess.processOnlyOneTime();
        PreProcess.AddPeriod();
        runServers();
    }


    private static void runServers() {
        new BankServer(); // 9000
        BuyerController.getInstance(); // 6000
        LoginController.getInstance(); // 1100
        ManagerController.getInstance(); // 7000
        SellerController.getInstance(); // 4000
        SupporterController.getInstance(); // 3000
        AllProductsController.getInstance(); // 5000
        new SaleController(); // 2000
        new ProductController(); // 1000
        new HasFirstManager(); // 777
        new AdvertisementController(); // 696
        new ReceiptController(); // 500
        ChatController.getInstance(); //572
        DNS.getInstance(); // 1673
        //TODO run all requierd servers
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
        Supporter.load();
        BankServer.load();
        BankReceipt.load();
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
        Supporter.store();
        BankServer.store();
        BankReceipt.store();
    }

    public static HashMap<String, GeneralAccount> getAuthTokenAccountHashMap() {
        return authTokenAccountHashMap;
    }

    public static void addToTokenHashMap(AuthToken token, GeneralAccount account) {
        try {
            ((Account) account).setNetworkState(NetworkState.ONLINE);
            authTokenAccountHashMap.put(String.valueOf(token.getKey()), account);
        } catch (Exception e) {
            authTokenAccountHashMap.put(String.valueOf(token.getKey()), account);
        }

    }

    public static void removeFromTokenHashMap(AuthToken token, GeneralAccount account) {
        ((Account) account).setNetworkState(NetworkState.OFFLINE);
        authTokenAccountHashMap.remove(String.valueOf(token.getKey()), account);
    }

    public static GeneralAccount getAccountWithToken(AuthToken token) {
        return authTokenAccountHashMap.get(String.valueOf(token.getKey()));
    }
}

