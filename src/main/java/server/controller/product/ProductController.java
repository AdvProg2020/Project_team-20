package server.controller.product;

import client.model.account.*;
import client.model.product.Product;
import client.model.product.comment.Comment;
import client.model.product.comment.Reply;
import client.network.AuthToken;
import client.network.Message;
import server.controller.Main;
import server.controller.account.user.BuyerController;
import server.network.server.Server;

import java.util.ArrayList;

public class ProductController extends Server {

    public ProductController() {
        super(1000);
        setMethods();
        System.out.println("product controller run");
    }

    public Message addProductToCart(String sellerId, String productId, AuthToken authToken) {
        Message message = new Message("addProductToCart");
        try {
            Product currentProduct = Product.getProductById(productId);
            GeneralAccount currentAccount = Main.getAccountWithToken(authToken);
            Seller seller = currentProduct.getSellerByUsername(sellerId);
            if (seller == null)
                throw new SellerNotFound();
            if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                    !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)))
                throw new AccountNotBuyerException();
            else if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                    (((Account) currentAccount).getAccountType().equals(AccountType.BUYER))) {
                ((Buyer) currentAccount).addProductToCart(currentProduct, seller);
            } else if (currentAccount instanceof TempAccount) {
                ((TempAccount) currentAccount).addProductToCart(currentProduct, seller);
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addComment(String productId, String title, String content, AuthToken authToken) {
        Message message = new Message("addComment");
        try {
            GeneralAccount currentAccount = Main.getAccountWithToken(authToken);
            if ((currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                    !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER))) ||
                    currentAccount.getGeneralAccountType().equals(GeneralAccountType.TEMP_ACCOUNT))
                throw new AccountNotBuyerException();
            Product product = Product.getProductById(productId);
            product.addComment(new Comment((Buyer) currentAccount, product, title, content));

        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addScore(double score, String productId, AuthToken authToken) throws Exception {
        Message message = new Message("addScore");
        try {
            GeneralAccount currentAccount = Main.getAccountWithToken(authToken);
            if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                    !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)) ||
                    currentAccount.getGeneralAccountType().equals(GeneralAccountType.TEMP_ACCOUNT))
                throw new AccountNotBuyerException();
            BuyerController.getInstance().rate(productId, score, authToken);
        } catch (
                Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message addReplyToComment(Comment comment, String title, String content, AuthToken authToken) throws Exception {
        Message message = new Message("addReplyToComment");
        try {
            GeneralAccount currentAccount = Main.getAccountWithToken(authToken);
            if (currentAccount.getGeneralAccountType().equals(GeneralAccountType.ACCOUNT) &&
                    !(((Account) currentAccount).getAccountType().equals(AccountType.BUYER)))
                throw new AccountNotBuyerException();
            comment.addToReplies(new Reply((Buyer) currentAccount, title, content));
        } catch (
                Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }


    public Message getAnotherProduct(String productId, String id, ArrayList<String> othersIds, AuthToken authToken) {
        Message message = new Message("getAnotherProduct");
        try {
            if (hasExistInOthers(id, othersIds))
                throw new ProductIsInCompare();
            else if (productId.equals(id))
                throw new ThisProductIsFirstProduct();
            message.addToObjects(Product.getProductById(id));
        } catch (
                Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private boolean hasExistInOthers(String id, ArrayList<String> othersIds) {
        for (String otherId : othersIds) {
            if (id.equals(otherId))
                return true;
        }
        return false;
    }

    public Message connectWithTempAccount() {
        System.out.println("in token generator");
        AuthToken authToken = AuthToken.generateAuth();
        Main.addToTokenHashMap(authToken, new TempAccount());
        Message answer = new Message("token for tempAccount");
        answer.setAuth(authToken);
        return answer;
    }

    @Override
    protected void setMethods() {
        methods.add("connectWithTempAccount");
        methods.add("addProductToCart");
        methods.add("addComment");
        methods.add("addScore");
        methods.add("addReplyToComment");
        methods.add("getAnotherProduct");
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
