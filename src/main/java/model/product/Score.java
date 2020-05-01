package model.product;

import model.account.Buyer;

public class Score {
    private Buyer buyer;
    private double score;
    Product product;

    public Score(Buyer buyer, double score, Product product) {
        this.buyer = buyer;
        this.score = score;
        this.product = product;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public double getScore() {
        return score;
    }

    public Product getProduct() {
        return product;
    }
}
