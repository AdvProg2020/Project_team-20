package model.product;

import model.Requestable;
import model.account.Buyer;
import model.account.Seller;

import java.util.ArrayList;

public class Product implements Requestable {
    static private ArrayList<Product> allProducts = new ArrayList<>();
    private String id;
    private RequestableState state;
    private ArrayList<Field> generalFields;
    private Seller seller;
    private Buyer buyer;
    private ArrayList<Category> categories;
    private String description;
    private double count;
    private ArrayList<Score> scores;
    private ArrayList<Comment> comments;
    private double numberVisited;
    private Product editedProduct;

    public Product(ArrayList<Field> generalFields, Seller seller, Buyer buyer, String description, int count) {
        id = Integer.toString(allProducts.size()+1);
        state =  RequestableState.CREATED;
        this.generalFields = generalFields;
        this.seller = seller;
        this.buyer = buyer;
        categories = new ArrayList<>();
        this.description = description;
        scores = new ArrayList<>();
        comments = new ArrayList<>();
        numberVisited = 0;
        this.count = count;
    }

    public Product(ArrayList<Field> generalFields, String description, int count) {
        this.generalFields = generalFields;
        this.description = description;
        this.count = count;
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        allProducts.add(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Field> generalFields, String description, int count) {
        editedProduct = new Product(generalFields , description, count);
        state = RequestableState.EDITED;
    }

    public void edit() {
        generalFields = editedProduct.getGeneralFields();
        description = editedProduct.getDescription();
        count = editedProduct.getCount();
        editedProduct = null;
        state = RequestableState.ACCEPTED;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    static void removeProduct (String productId) throws Exception {
        Product productToDelete = getProductById(productId);
        allProducts.remove(productToDelete);
    }

    static Product getProductById(String id) throws Exception{
        for (Product product:allProducts) {
            if (product.getId().equals(id))
                return product;
        }
        throw new productUnavailableException();
    }

    public String getId() {
        return id;
    }

    public ArrayList<Field> getGeneralFields() {
        return generalFields;
    }

    public Seller getSeller() {
        return seller;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public String getDescription() {
        return description;
    }

    private int getNumberFields() {
        return generalFields.size();
    }

    public static class productUnavailableException extends Exception {
        public productUnavailableException() { super("product unavailable"); }
    }

    public double getCount() {
        return count;
    }
}
