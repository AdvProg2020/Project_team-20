package controller;

import model.account.Account;
import model.product.Cart;

public class MainController {
    private static MainController mainController = null;
    private Account account;
    private Cart cart;

    private MainController() {
        this.cart = new Cart();
    }

    public static MainController getInstance() {
        if (mainController == null)
            mainController = new MainController();
        return mainController;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
