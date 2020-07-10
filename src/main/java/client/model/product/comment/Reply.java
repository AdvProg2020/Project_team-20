package client.model.product.comment;

import client.model.account.Buyer;

public class Reply {
    private String buyerUsername;
    private String title;
    private String content;

    public Reply(Buyer buyer, String title, String content) {
        this.buyerUsername = buyer.getUsername();
        this.title = title;
        this.content = content;
    }

    public Buyer getBuyer() throws Exception {
        return Buyer.getBuyerWithUsername(buyerUsername);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
