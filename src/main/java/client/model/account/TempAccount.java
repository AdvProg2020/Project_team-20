package client.model.account;

import client.model.product.Cart;
import client.model.product.Product;

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
