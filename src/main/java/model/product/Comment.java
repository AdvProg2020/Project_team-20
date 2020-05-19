package model.product;

import model.Requestable;
import model.account.Buyer;

public class Comment {
    private Buyer buyer;
    private Product product;
    private String title;
    private String content;

    public Comment(Buyer buyer, Product product, String title, String content) {
        this.buyer = buyer;
        this.product = product;
        this.title = title;
        this.content = content;
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

    public RequestType getRequestType() {
        return RequestType.Comment;
    }
}
