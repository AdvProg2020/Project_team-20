package server.controller.product;

import server.controller.Main;
import server.controller.MainController;
import server.controller.account.user.BuyerController;
import client.model.account.*;
import client.model.product.Product;
import client.model.product.comment.Comment;
import client.model.product.comment.Reply;
import client.network.AuthToken;

import java.util.ArrayList;

public class ProductController {
    private Product currentProduct;
    MainController mainController;

    public ProductController(Product product) {
        this.currentProduct = product;
        product.increaseProductViews();
        mainController = MainController.getInstance();
    }

    public void addProductToCart(String sellerId) throws Exception {
        Seller seller = currentProduct.getSellerByUsername(sellerId);
        if (seller == null)
            throw new SellerNotFound();
        GeneralAccount currentAccount = mainController.getAccount();

        if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)))
            throw new AccountNotBuyerException();
        else if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                (((Account) currentAccount).getAccountType().equals(AccountType.BUYER))) {
            ((Buyer) currentAccount).addProductToCart(currentProduct, seller);
        } else if (currentAccount instanceof TempAccount) {
            ((TempAccount) currentAccount).addProductToCart(currentProduct, seller);
        }
    }

    public void addComment(Product product, String title, String content) throws Exception {
        GeneralAccount currentAccount = mainController.getAccount();
        if ((currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER))) ||
                currentAccount.getGeneralAccountType().equals(GeneralAccountType.TEMP_ACCOUNT))
            throw new AccountNotBuyerException();
        product.addComment(new Comment((Buyer) currentAccount, product, title, content));
    }

    public void addScore(double score, Product product, AuthToken authToken) throws Exception {
        GeneralAccount currentAccount = Main.getAccountWithToken(authToken);
        if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)) ||
                currentAccount.getGeneralAccountType().equals(GeneralAccountType.TEMP_ACCOUNT))
            throw new AccountNotBuyerException();
        BuyerController.getInstance().rate(product.getId(), score, authToken);
    }

    public void addReplyToComment(Comment comment, String title, String content) throws Exception {
        GeneralAccount currentAccount = mainController.getAccount();
        if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)))
            throw new AccountNotBuyerException();
        comment.addToReplies(new Reply((Buyer) currentAccount, title, content));
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public Product getAnotherProduct(String id, ArrayList<String> othersIds) throws Exception {
        if (hasExistInOthers(id, othersIds))
            throw new ProductIsInCompare();
        else if (currentProduct.getId().equals(id))
            throw new ThisProductIsFirstProduct();
        return Product.getProductById(id);
    }

    private boolean hasExistInOthers(String id, ArrayList<String> othersIds) {
        for (String otherId : othersIds) {
            if (id.equals(otherId))
                return true;
        }
        return false;
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

    public static class ProductIsInCompare extends Exception {
        public ProductIsInCompare() {
            super("this product is in compare");
        }
    }

    public static class ThisProductIsFirstProduct extends Exception {
        public ThisProductIsFirstProduct() {
            super("this product is first product");
        }
    }
}
