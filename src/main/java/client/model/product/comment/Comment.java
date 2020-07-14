package client.model.product.comment;

import client.model.account.Buyer;
import client.model.product.Product;

import java.util.ArrayList;

public class Comment {
    private String commentId;
    private String buyersUsername;
    private String productId;
    private String title;
    private String content;
    private ArrayList<Reply> replies;


    public Comment(Buyer buyer, Product product, String title, String content) {
        this.commentId = String.valueOf(product.getComments().size());
        this.buyersUsername = buyer.getUsername();
        this.productId = product.getId();
        this.title = title;
        this.content = content;
        this.replies = new ArrayList<>();
    }

    public Buyer getBuyer() throws Exception {
        return Buyer.getBuyerWithUsername(buyersUsername);
    }

    public Product getProduct() throws Exception {
        return Product.getProductById(productId);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void addToReplies(Reply reply) {
        replies.add(reply);
    }

    public String getCommentId() {
        return commentId;
    }

    public String getBuyerUserName() {
        return buyersUsername;
    }
}
