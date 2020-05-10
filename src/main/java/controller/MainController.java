package controller;

import model.account.Account;
import model.account.GeneralAccount;
import model.product.Cart;

public class MainController {
    private static MainController mainController = null;
    private GeneralAccount account;
    private Cart cart;

    private MainController() {
        this.cart = new Cart();
    }

    public static MainController getInstance() {
        if (mainController == null)
            mainController = new MainController();
        return mainController;
    }

    public GeneralAccount getAccount() {
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
