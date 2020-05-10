package model.account;

import model.product.Cart;
import model.product.Product;

public class TempAccount extends GeneralAccount {
    private Cart cart;

    public TempAccount() {
        super(GeneralAccountType.TEMP_ACCOUNT);
        cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void addProductToCart(Product product, Seller seller) {
        cart.addProduct(product, seller);
    }
}
