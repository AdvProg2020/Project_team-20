package model.product;

import model.Requestable;
import model.account.Account;
import model.account.Buyer;

public class Score implements Requestable {
    private String buyerUsername;
    private double score;
    private String productID;
    private RequestableState state;

    public Score(Buyer buyer, double score, Product product) {
        this.buyerUsername = buyer.getUsername();
        this.score = score;
        this.productID = product.getId();
        this.state = RequestableState.CREATED;
    }

    public Buyer getBuyer() {
        try {
            Buyer buyer =(Buyer) Account.getAccountWithUsername(buyerUsername);
            return buyer;
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
            Product product = Product.getProductById(productID);
            return product;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void changeStateAccepted() {
        try {
            state = RequestableState.ACCEPTED;
            Product.getProductById(productID).addScore(this);
        }
        catch (Exception e){
        }
    }

    @Override
    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    @Override
    public void edit() {

    }

    @Override
    public RequestableState getState() {
        return null;
    }

    public RequestType getRequestType() {
        return RequestType.Score;
    }

    @Override
    public String toString() {
            String scoreString =
                    "RequestType         : Score" + "\n" +
                    "Product              : " + productID + "\n" +
                    "Buyer               : " + buyerUsername + "\n" +
                    "Score               : " + score;
            if (state.equals(RequestableState.EDITED))
                scoreString = "<Edited>\n" + scoreString;
            return scoreString;
    }
}
