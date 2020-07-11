package server.controller;

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
    private static HashMap<AuthToken, Account> authTokenAccountHashMap = new HashMap<>();

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
        //TODO run all required servers
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

    public static HashMap<AuthToken, Account> getAuthTokenAccountHashMap() {
        return authTokenAccountHashMap;
    }

    public static void addToTokenHashMap(AuthToken token, Account account) {
        authTokenAccountHashMap.put(token, account);
    }

    public static void removeFromTokenHashMap(AuthToken token, Account account) {
        authTokenAccountHashMap.remove(token, account);
    }

    public static void removeFromTokenHashMap(AuthToken token) {
        authTokenAccountHashMap.remove(token);
    }

    public static Account getAccountWithToken(AuthToken token) {
        return authTokenAccountHashMap.get(token);
    }
}

