package controller.product;

import controller.MainController;
import model.account.*;
import model.product.Product;

public class ProductController {
    private Product currentProduct;
    MainController mainController;

    private static ProductController productController = null;

    private ProductController(Product product) {
        this.currentProduct = product;
        mainController = MainController.getInstance();
    }

    private void addProductToCart (String sellerId) throws Exception{
        Account accountBuyFrom = Account.getAccountWithUsername(sellerId);
        GeneralAccount currentAccount = mainController.getAccount();
        if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) && !(((Account)currentAccount).getAccountType().equals(AccountType.BUYER)))
            throw new AccountNotBuyerException();
        else if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) && (((Account)currentAccount).getAccountType().equals(AccountType.BUYER))) {
            ((Buyer)currentAccount).addProductToCart(currentProduct, (Buyer)accountBuyFrom);
        }
        else {

        }
    }

    public static ProductController getInstance(Product product) {
        if (productController == null)
            productController = new ProductController(product);
        return productController;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public static class AccountNotBuyerException extends Exception {
        public AccountNotBuyerException() { super("Account not buyer"); }
    }
}
