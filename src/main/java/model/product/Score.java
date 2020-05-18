package model.product;

import model.Requestable;
import model.account.Buyer;

public class Score implements Requestable {
    private Buyer buyer;
    private double score;
    private Product product;
    private RequestableState state;
    private RequestType requestType = RequestType.Score;

    public Score(Buyer buyer, double score, Product product) {
        this.buyer = buyer;
        this.score = score;
        this.product = product;
        this.state = RequestableState.CREATED;
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

    @Override
    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        product.addScore(this);
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
        return requestType;
    }
}
