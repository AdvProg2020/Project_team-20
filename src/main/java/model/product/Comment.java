package model.product;

import model.Requestable;
import model.account.Buyer;

public class Comment implements Requestable {
    private Buyer buyer;
    private Product product;
    private String title;
    private String content;
    private RequestableState state;


    public Comment(Buyer buyer, Product product, String title, String content) {
        this.buyer = buyer;
        this.product = product;
        this.title = title;
        this.content = content;
        state = RequestableState.CREATED;
    }

    @Override
    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
    }

    @Override
    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public RequestableState getState() {
        return state;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
