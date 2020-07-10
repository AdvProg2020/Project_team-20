package server.controller;

import server.model.account.Account;
import server.model.account.GeneralAccount;
import server.model.account.TempAccount;
import server.model.product.Cart;

public class MainController {
    private static MainController mainController = null;
    private GeneralAccount account;
    private Cart cart;

    private MainController() {
        this.cart = new Cart();
        this.account = new TempAccount();
    }

    public static MainController getInstance() {
        if (mainController == null)
            mainController = new MainController();
        return mainController;
    }

    public void logout() {
        this.account = new TempAccount();
    }

    public GeneralAccount getAccount() {
        return account;
    }

    public void setAccount(GeneralAccount account) {
        this.account = account;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}