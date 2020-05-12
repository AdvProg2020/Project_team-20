package model.product;

import model.Requestable;
import model.account.Buyer;
import model.account.Seller;
import model.product.Field.Field;

import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Requestable {
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private String id;
    private String name;
    private RequestableState state;
    private ArrayList<Field> generalFields;
    private ArrayList<Seller> sellers;
    private ArrayList<Buyer> buyers;
    private ArrayList<Category> categories;
    private String description;
    private HashMap<Seller, Double> price;
    private HashMap<Seller, Integer> count;
    private ArrayList<Score> scores;
    private ArrayList<Comment> comments;
    private double numberVisited;
    private Product editedProduct;

    public Product(ArrayList<Field> generalFields, Seller seller, String name, String description, int count,
                   double price) {
        this.name = name;
        id = Integer.toString(allProducts.size() + 1);
        state = RequestableState.CREATED;
        this.generalFields = generalFields;
        sellers = new ArrayList<>();
        sellers.add(seller);
        buyers = new ArrayList<>();
        categories = new ArrayList<>();
        this.description = description;
        scores = new ArrayList<>();
        comments = new ArrayList<>();
        numberVisited = 0;
        this.count = new HashMap<>();
        this.price = new HashMap<>();
        this.count.put(seller, count);
        this.price.put(seller, price);
    }

    public Product(ArrayList<Field> generalFields, String description, int count, double price, Seller seller) throws SellerUnavailableException {
        this.generalFields = generalFields;
        this.description = description;
        this.count = new HashMap<>();
        this.price = new HashMap<>();
        this.count.put(seller, count);
        this.price.put(seller, price);
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        allProducts.add(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Field> generalFields, String description, int count, double price, Seller seller) throws Exception {
        editedProduct = new Product(generalFields, description, count, price, seller);
        state = RequestableState.EDITED;
    }

    public void edit(Seller seller) {
        generalFields = editedProduct.getGeneralFields();
        description = editedProduct.getDescription();
        price.replace(seller, editedProduct.getPrice(seller));
        count.replace(seller, editedProduct.getCount(seller));
        editedProduct = null;
        state = RequestableState.ACCEPTED;
    }

    public Buyer getBuyerByUsername(String username) {
        for (Buyer buyer : buyers) {
            if (buyer.getUsername().equals(username))
                return buyer;
        }
        return null;
    }

    public Seller getSellerByUsername(String username) {
        for (Seller seller : sellers) {
            if (seller.getUsername().equals(username))
                return seller;
        }
        return null;
    }

    public void removeSeller(String username) {
        Seller seller = getSellerByUsername(username);
        sellers.remove(seller);
        price.remove(seller);
        count.remove(seller);
    }

    public void addSeller(Seller seller) {
        sellers.add(seller);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
    }

    public static void removeProduct(String productId) throws Exception {
        Product productToDelete = getProductById(productId);
        allProducts.remove(productToDelete);
    }

    public static Product getProductById(String id) throws Exception {
        for (Product product : allProducts) {
            if (product.getId().equals(id))
                return product;
        }
        throw new ProductUnavailableException();
    }

    public static ArrayList<Product> getProductsWithIds(ArrayList<String> productIds) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        Product tempProduct;
        for (String productId : productIds) {
            tempProduct = Product.getProductById(productId);
            products.add(tempProduct);
        }
        return products;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Field> getGeneralFields() {
        return generalFields;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public ArrayList<Buyer> getBuyers() {
        return buyers;
    }

    public String getDescription() {
        return description;
    }

    private int getNumberFields() {
        return generalFields.size();
    }

    public static class ProductUnavailableException extends Exception {
        public ProductUnavailableException() {
            super("product unavailable");
        }
    }

    public static class SellerUnavailableException extends Exception {
        public SellerUnavailableException() {
            super("seller unavailable");
        }
    }

    public int getCount(Seller seller) {
        return count.get(seller);
    }

    public double getPrice(Seller seller) {
        return price.get(seller);
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public String getName() {
        return name;
    }

    public RequestableState getState() {
        return state;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public double getNumberVisited() {
        return numberVisited;
    }

    public Product getEditedProduct() {
        return editedProduct;
    }


    public void addScore(Score scores) {
        this.scores.add(scores);
    }

    public void addComment(Comment comments) {
        this.comments.add(comments);
    }

    public void addNumberVisited() {
        this.numberVisited += 1;
    }

}
