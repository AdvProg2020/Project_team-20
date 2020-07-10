package client.model.product.comment;

import client.model.account.Account;
import client.model.account.Buyer;
import client.model.product.Product;

public class Score {
    private String buyerUsername;
    private double score;
    private String productID;

    public Score(Buyer buyer, double score, Product product) {
        this.buyerUsername = buyer.getUsername();
        this.score = score;
        this.productID = product.getId();
    }

    public Buyer getBuyer() {
        try {
            return (Buyer) Account.getAccountWithUsername(buyerUsername);
        } catch (Exception e) {
            return null;
        }
    }

    public double getScore() {
        return score;
    }

    public Product getProduct() {
        try {
            return Product.getProductById(productID);
        } catch (Exception e) {
            return null;
        }
    }
}
