package server.controller;

import server.controller.account.LoginController;
import server.controller.account.user.BuyerController;
import server.controller.bank.BankServer;
import server.model.account.Account;
import server.model.account.Buyer;
import server.model.account.Manager;
import server.model.account.Seller;
import server.model.product.*;
import server.model.product.category.SubCategory;
import server.model.product.category.CategorySet;
import server.model.receipt.BuyerReceipt;
import server.model.receipt.SellerReceipt;
import server.network.AuthToken;

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
        new BankServer();
        BuyerController.getInstance();
        LoginController.getInstance();
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

