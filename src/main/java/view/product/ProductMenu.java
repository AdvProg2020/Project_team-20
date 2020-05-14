package view.product;

import controller.product.ProductController;
import model.account.Buyer;
import model.account.Seller;
import model.product.*;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import view.Menu;

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
        this.regex.add("digest");
        this.regex.add("add to cart");
        this.regex.add("select seller (\\S+)");
        this.regex.add("attributes");
        this.regex.add("compare (\\S+)");
        this.regex.add("comments");
        this.regex.add("Add comment");
    }

    public void digest() {
        System.out.println("name : " + product.getName());
        System.out.println("description : " + product.getDescription());
        for (Seller seller : product.getSellers()) {
            Sale sale = seller.getSaleWithProduct(product);
            System.out.print("seller : " + seller.getName() + " seller username : " + seller.getUsername()
                    + " price : " + product.getPrice(seller) + " count : " + product.getCount(seller));
            if (sale != null)
                System.out.println("sale start date : " + sale.getStartDate() + " sale end date"
                        + sale.getEndDate() + " sale percentage " + sale.getSalePercentage());
            else System.out.println();
        }
        System.out.println("average score : " + product.getAverage());
    }

    public void addProductToCart() {
        try {
            productController.addProductToCart(getSellerId());
            System.out.println("addition was successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void attributes() {
        for (Category category : product.getCategories()) {
            System.out.println("category name : " + category.getName());
            for (Field field : category.getFields()) {
                System.out.print("field name : " + field.getName());
                if (field.getType().equals(FieldType.NUMERICAL))
                    System.out.println("numerical field : " + ((NumericalField) field).getNumericalField());
                else System.out.println("optional field : " + ((OptionalField) field).getOptionalFiled());
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
    }

    public void compare(String anotherProductId) {
        try {
            Product anotherProduct = productController.getAnotherProduct(anotherProductId);
            System.out.println("first product");
            attributes();
            System.out.println("second product");
            (new ProductMenu(anotherProduct)).attributes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void comments() {
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
}
