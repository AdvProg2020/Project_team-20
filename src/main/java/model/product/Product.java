package model.product;

import com.gilecode.yagson.YaGson;
import controller.product.filter.Filterable;
import model.Requestable;
import model.account.Account;
import model.account.Buyer;
import model.account.Seller;
import model.product.Field.Field;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


public class Product implements Requestable {
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static int productCount = 1;
    private String id;
    private int views;
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
    private LocalDateTime addingDate;

    public Product(ArrayList<Field> generalFields, Seller seller, String name, String description, int count,
                   double price) {
        this.views = 0;
        this.name = name;
        id = Integer.toString(productCount);
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
        this.addingDate = LocalDateTime.now();
        productCount++;
    }

    public Product(ArrayList<Field> generalFields, String description, int count, double price, Seller seller) {
        this.views = 0;
        this.generalFields = generalFields;
        this.description = description;
        this.count = new HashMap<>();
        this.price = new HashMap<>();
        this.count.put(seller, count);
        this.price.put(seller, price);
    }

    public void increaseProductViews(){
        this.views++;
    }

    public int getViews() {
        return views;
    }

    public LocalDateTime getAddingDate(){
        return  addingDate;
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        Seller seller = sellers.get(0);
        int num = count.get(seller);
        seller.addToProductsToSell(this, num);
        allProducts.add(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Field> generalFields, String description, int count, double price, Seller seller) throws Exception {
        if (editedProduct == null) {
            editedProduct = new Product(generalFields, description, count, price, seller);
            state = RequestableState.EDITED;
        } else
            throw new ProductIsUnderEditingException();
    }

    public void edit() {
        Set<Seller> sellerSet = editedProduct.getCount().keySet();
        ArrayList<Seller> sellers = new ArrayList<>(sellerSet);
        Seller seller = sellers.get(0);
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

    public void addSeller(Seller seller, int count, double price) {
        sellers.add(seller);
        this.count.put(seller, count);
        this.price.put(seller, price);
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

    public static class ProductIsUnderEditingException extends Exception {
        public ProductIsUnderEditingException() {
            super("product is under editing");
        }
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    public int getCount(Seller seller) {
        return count.get(seller);
    }

    public double getPrice(Seller seller) {
        return price.get(seller);
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    public HashMap<Seller, Integer> getCount() {
        return count;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public RequestableState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public static Product getProductWithItsName(String name) throws Exception{
        for(Product product:allProducts){
            if(product.getName().equals(name))
                return product;
        }
        throw new productNotFoundException();
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public HashMap<Seller, Double> getPrice() {
        return price;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public double getAverage() {
        double sum = 0;
        for (Score score : getScores()) {
            sum += score.getScore();
        }
        return sum / getScores().size();
    }

    public void decreaseCountSeller(Seller seller, int number) {
        int temp = count.get(seller);
        count.replace(seller, temp-number);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public double getNumberVisited() {
        return numberVisited;
    }

    public void addToNumberOfViews() {
        numberVisited += 1;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public static class productNotFoundException extends Exception {
        public productNotFoundException() {
            super("product doesn't exist");
        }
    }

    @Override
    public String toString() {
        String productString = "Name                : " + name + "\n" +
                             "RequestType         : Product" + "\n" +
                             "Seller              : " + sellers.get(0).getName() + "\n" +
                             "Price               : " + price.get(sellers.get(0)) + "\n" +
                             "Count               : " + count.get(sellers.get(0));
        if (state.equals(RequestableState.EDITED))
            productString = "<Edited>\n" + productString;
        return productString;
    }

    public void setSellers(ArrayList<Seller> sellers) {
        this.sellers = sellers;
    }

    public void setBuyers(ArrayList<Buyer> buyers) {
        this.buyers = buyers;
    }

    public void setPrice(HashMap<Seller, Double> price) {
        this.price = price;
    }

    public void setCount(HashMap<Seller, Integer> count) {
        this.count = count;
    }

    public static void load() {
        YaGson yaGson = new YaGson();
    }

    public RequestType getRequestType() {
        return RequestType.Product;
    }
}
