package server.network.server;

import server.controller.PreProcess;
import server.model.account.Buyer;
import server.model.account.Manager;
import server.model.account.Seller;
import server.model.product.*;
import server.model.product.category.SubCategory;
import server.model.product.category.CategorySet;
import server.model.receipt.BuyerReceipt;
import server.model.receipt.SellerReceipt;

public class MainServer {
    private static PreProcess preProcess = new PreProcess();

    public static void main(String[] args) {
        loadData();
        if (PreProcess.getPeriod() == 10)
            preProcess.processOnlyOneTime();
        PreProcess.AddPeriod();

        // for graphic client.view
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

