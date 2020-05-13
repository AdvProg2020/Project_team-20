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

    private void addProductToCart(String sellerId) throws Exception {
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

    public static ProductController getInstance(Product product) {
        if (productController == null)
            productController = new ProductController(product);
        return productController;
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
