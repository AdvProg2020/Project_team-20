package client.controller;

import client.model.account.GeneralAccount;
import client.model.account.TempAccount;
import client.model.product.Cart;
import client.view.console.MainMenu;

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
        Main.setCurrentMenu(new MainMenu());
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
