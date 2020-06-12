package view.console.product;

import controller.product.ProductController;
import model.account.Buyer;
import model.account.Seller;
import model.product.*;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.comment.Comment;
import model.product.comment.Reply;
import model.product.comment.Score;
import view.console.Menu;

import java.util.ArrayList;

public class ProductMenu extends Menu {
    ProductController productController;
    Product product;
    String sellerId;

    public ProductMenu(Product product) {
        super("ProductMenu");
        productController = new ProductController(product);
        this.methods.add("digest");
        this.methods.add("addProductToCart");
        this.methods.add("selectSeller");
        this.methods.add("attributes");
        this.methods.add("compare");
        this.methods.add("comments");
        this.methods.add("addComment");
        this.methods.add("addScore");
        this.methods.add("addReply");
        this.methods.add("replies");
        this.regex.add("digest");
        this.regex.add("add to cart");
        this.regex.add("select seller (\\S+)");
        this.regex.add("attributes");
        this.regex.add("compare (\\S+)");
        this.regex.add("comments");
        this.regex.add("Add comment");
        this.regex.add("addScore");
        this.regex.add("addReply");
        this.regex.add("replies");
        this.product = product;
        preProcess();
    }

    public void digest() {
        System.out.println("name                : " + product.getName());
        System.out.println("description         : " + product.getDescription());
        for (Seller seller : product.getSellers()) {
            Sale sale = seller.getSaleWithProduct(product);
            System.out.println("seller              : " + seller.getName() + "\n" + "seller username     : " +
                    seller.getUsername() + "\n" + "price               : " + product.getPrice(seller) + "\n" +
                    "count               : " + product.getCount(seller));
            if (sale != null)
                System.out.println("sale start date     : " + "\n" + sale.getStartDate() +
                        "\n" + "sale end date       : " + "\n" + sale.getEndDate() + "\n" +
                        "sale percentage     : " + "\n" + sale.getSalePercentage() * 100);
            else System.out.println();
        }
        System.out.println("average score       : " + product.getAverage());
    }

    public void addProductToCart() {
        try {
            productController.addProductToCart(getSellerId());
            System.out.println("addition was successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void attributes() {
        for (Category category : product.getCategories()) {
            System.out.println("category name : " + category.getName());
            for (String field : category.getFields()) {
                System.out.print("field name : " + field);
            }
        }
        System.out.println();
        System.out.println("general fields");
        for (Field generalField : product.getGeneralFields()) {
            System.out.print("field name : " + generalField.getName());
            if (generalField.getType().equals(FieldType.NUMERICAL))
                System.out.println("numerical field : " + ((NumericalField) generalField).getNumericalField());
            else System.out.println("optional field : " + ((OptionalField) generalField).getOptionalFiled());
        }
    }

    public void compare(String anotherProductId) {
        CompareMenu compareMenu = null;
        try {
            compareMenu = new CompareMenu(this, product, anotherProductId);
            enter(compareMenu);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void comments() throws Exception {
        ArrayList<Score> scores = product.getScores();
        ArrayList<Comment> comments = product.getComments();
        Buyer buyer;
        for (Score score : scores) {
            buyer = score.getBuyer();
            System.out.println("buyer : " + buyer.getUsername() + " score from this buyer : " + score.getScore());
        }
        for (Comment comment : comments) {
            buyer = comment.getBuyer();
            System.out.println("buyer: " + buyer.getUsername() + " comment from this buyer :\n" +
                    "title : " + comment.getTitle() + "content : " + comment.getContent());
        }
    }

    public void addComment() {
        String title, content;
        System.out.println("please enter your title:");
        title = scanner.nextLine();
        System.out.println("please enter your content");
        content = scanner.nextLine();
        try {
            productController.addComment(product, title, content);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addScore() {
        double score;
        System.out.println("please enter your Score");
        score = scanner.nextDouble();
        try {
            productController.addScore(score, product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addReply() {
        System.out.println("please enter comment id");
        String id = scanner.nextLine();
        System.out.println("please enter title of reply");
        String title = scanner.nextLine();
        System.out.println("please enter your content");
        String content = scanner.nextLine();
        try {
            Comment comment = product.getCommentWithId(id);
            productController.addReplyToComment(comment, title, content);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void replies() {
        System.out.println("please enter comment id");
        String id = scanner.nextLine();
        try {
            Comment comment = product.getCommentWithId(id);
            for (Reply reply : comment.getReplies()) {
                System.out.println("buyer: " + reply.getBuyer().getUsername());
                System.out.println("title: " + reply.getTitle());
                System.out.println("content: " + reply.getContent());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void selectSeller(String sellerId) {
        setSellerId(sellerId);
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public ProductController getProductController() {
        return productController;
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void help() {
        super.help();
    }

    @Override
    public void enterWithName(String subMenuName) {
        super.enterWithName(subMenuName);
    }

    @Override
    public void back() {
        super.back();
    }
}
