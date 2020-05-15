package controller;

import model.account.Account;
import model.account.GeneralAccount;
import model.account.TempAccount;
import model.product.Cart;
import view.MainMenu;

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
