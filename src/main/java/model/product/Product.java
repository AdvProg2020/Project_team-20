package model.product;

import model.Requestable;
import model.account.Buyer;
import model.account.Seller;
import model.product.Field.Field;

import java.util.ArrayList;

public class Product implements Requestable {
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private String id;
    private RequestableState state;
    private ArrayList<Field> generalFields;
    private ArrayList<Seller> sellers;
    private ArrayList<Buyer> buyers;
    private ArrayList<Category> categories;
    private String description;
    private double price;
    private int count;
    private ArrayList<Score> scores;
    private ArrayList<Comment> comments;
    private double numberVisited;
    private Product editedProduct;
    private Seller sellerToAdd;

    public Product(ArrayList<Field> generalFields, Seller seller, String description, int count, double price) {
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
        this.count = count;
        this.price = price;
    }

    public Product(ArrayList<Field> generalFields, String description, int count, double price,Seller seller) {
        this.generalFields = generalFields;
        this.description = description;
        this.count = count;
        this.price = price;
        sellerToAdd = seller;
    }

    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        allProducts.add(this);
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Field> generalFields, String description, int count, double price,Seller seller) {
        editedProduct = new Product(generalFields, description, count, price, seller);
        state = RequestableState.EDITED;
    }

    public void edit() {
        generalFields = editedProduct.getGeneralFields();
        description = editedProduct.getDescription();
        count = editedProduct.getCount();
        sellers.add(editedProduct.getSellerToAdd());
        price = editedProduct.getPrice();
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
        throw new productUnavailableException();
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

    public Seller getSellerToAdd() {
        return sellerToAdd;
    }

    public static class productUnavailableException extends Exception {
        public productUnavailableException() {
            super("product unavailable");
        }
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }
}
