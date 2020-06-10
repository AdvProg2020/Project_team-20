package model.product;

import model.Requestable;
import model.account.Account;
import model.account.Buyer;

public class Score implements Requestable{
    private String buyerUsername;
    private double score;
    private String productID;

    public Score(Buyer buyer, double score, Product product) {
        this.buyerUsername = buyer.getUsername();
        this.score = score;
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

    }

    @Override
    public void changeStateRejected() {

    }

    @Override
    public void edit() {

    }

    @Override
    public RequestableState getState() {
        return null;
    }

    @Override
    public RequestType getRequestType() {
        return null;
    }
}
