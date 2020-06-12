package model.product.comment;

import model.account.Account;
import model.account.Buyer;
import model.product.Product;

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
