package server.controller;

import server.controller.account.LoginController;
import server.controller.account.user.BuyerController;
import server.controller.account.user.ManagerController;
import server.controller.account.user.SellerController;
import server.controller.account.user.SupporterController;
import server.controller.bank.BankServer;
import client.model.account.Account;
import client.model.account.Buyer;
import client.model.account.Manager;
import client.model.account.Seller;
import client.model.product.*;
import client.model.product.category.SubCategory;
import client.model.product.category.CategorySet;
import client.model.receipt.BuyerReceipt;
import client.model.receipt.SellerReceipt;
import client.network.AuthToken;
import server.controller.product.filter.AllProductsController;

import java.util.HashMap;

public class Main {
    private static PreProcess preProcess = new PreProcess();
    private static HashMap<String, Account> authTokenAccountHashMap = new HashMap<>();

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

    public static HashMap<String, Account> getAuthTokenAccountHashMap() {
        return authTokenAccountHashMap;
    }

    public static void addToTokenHashMap(AuthToken token, Account account) {
        authTokenAccountHashMap.put(String.valueOf(token.getKey()), account);
    }

    public static void removeFromTokenHashMap(AuthToken token, Account account) {
        authTokenAccountHashMap.remove(String.valueOf(token.getKey()), account);
    }

    public static void removeFromTokenHashMap(AuthToken token) {
        authTokenAccountHashMap.remove(String.valueOf(token.getKey()));
    }

    public static Account getAccountWithToken(AuthToken token) {
        return authTokenAccountHashMap.get(String.valueOf(token.getKey()));
    }
}

