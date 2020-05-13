package controller.product;

import controller.MainController;
import model.account.*;
import model.product.Product;

public class ProductController {
    private Product currentProduct;
    MainController mainController;

    public ProductController(Product product) {
        this.currentProduct = product;
        mainController = MainController.getInstance();
    }

    public void addProductToCart(String sellerId) throws Exception {
        Seller seller = currentProduct.getSellerByUsername(sellerId);
        if (seller == null)
            throw new SellerNotFound();
        GeneralAccount currentAccount = mainController.getAccount();

        if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) && !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)))
            throw new AccountNotBuyerException();
        else if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) && (((Account) currentAccount).getAccountType().equals(AccountType.BUYER))) {
            ((Buyer) currentAccount).addProductToCart(currentProduct, seller);
        } else if (currentAccount instanceof TempAccount) {
            ((TempAccount) currentAccount).addProductToCart(currentProduct, seller);
        }
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public static class AccountNotBuyerException extends Exception {
        public AccountNotBuyerException() {
            super("Account not buyer");
        }
    }

    public static class SellerNotFound extends Exception {
        public SellerNotFound() {
            super("Seller not found");
        }
    }
}
