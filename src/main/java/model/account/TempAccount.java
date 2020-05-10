package model.account;

import model.product.Cart;
import model.product.Product;

public class TempAccount extends GeneralAccount {
    private Cart cart;

    public TempAccount(AccountType accountType) {
        super(accountType);
        cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void addProductToCart(Product product, Buyer buyer) {
        cart.addProduct(product, buyer);
    }
}
