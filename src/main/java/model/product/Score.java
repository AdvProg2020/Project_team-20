package model.product;

import model.account.Account;
import model.account.Buyer;

public class Score {
    private String buyerUsername;
    private double score;
    private String productID;

    public Score(Buyer buyer, double score, Product product) {
        this.buyerUsername = buyer.getUsername();
        this.score = score;
    }

    public Buyer getBuyer() {
        try {
            return (Buyer) Account.getAccountWithUsername(buyerUsername);
        }
        catch (Exception e){
            return null;
        }
    }

    public double getScore() {
        return score;
    }

    public Product getProduct() {
        try {
            return Product.getProductById(productID);
        }
        catch (Exception e){
            return null;
        }
    }
}
