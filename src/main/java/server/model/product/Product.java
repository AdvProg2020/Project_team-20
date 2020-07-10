package server.model.product;

import com.gilecode.yagson.YaGson;
import server.model.GraphicPackage;
import server.model.Requestable;
import server.model.account.Account;
import server.model.account.Buyer;
import server.model.account.Seller;
import server.model.product.Field.Field;
import server.model.product.category.SubCategory;
import server.model.product.comment.Comment;
import server.model.product.comment.Score;

import java.io.*;
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
    private ArrayList<String> sellersUsername;
    private ArrayList<String> buyersUsername;
    private ArrayList<String> categoriesName;
    private String description;
    private HashMap<String, Double> priceWithName;
    private HashMap<String, Integer> countWithName;
    private ArrayList<Score> scores;
    private ArrayList<Comment> comments;
    private double numberVisited;
    private String imagePath;
    private Product editedProduct;
    private LocalDateTime addingDate;
    private GraphicPackage graphicPackage;

    public Product(ArrayList<Field> generalFields, Seller seller, String name, String description, int count,
                   double price) {
        this.views = 0;
        this.name = name;
        id = Integer.toString(productCount);
        state = RequestableState.CREATED;
        this.generalFields = generalFields;
        sellersUsername = new ArrayList<>();
        sellersUsername.add(seller.getUsername());
        buyersUsername = new ArrayList<>();
        categoriesName = new ArrayList<>();
        this.description = description;
        scores = new ArrayList<>();
        comments = new ArrayList<>();
        numberVisited = 0;
        this.countWithName = new HashMap<>();
        this.priceWithName = new HashMap<>();
        this.countWithName.put(seller.getUsername(), count);
        this.priceWithName.put(seller.getUsername(), price);
        this.addingDate = LocalDateTime.now();
        productCount++;
        this.graphicPackage = new GraphicPackage();
    }

    public Product(ArrayList<Field> generalFields, String description, int count, double price, Seller seller) {
        this.views = 0;
        this.generalFields = generalFields;
        this.description = description;
        this.countWithName = new HashMap<>();
        this.priceWithName = new HashMap<>();
        this.countWithName.put(seller.getUsername(), count);
        this.priceWithName.put(seller.getUsername(), price);
    }

    public boolean isSold() {
        for (String sellerId : countWithName.keySet()) {
            if (countWithName.get(sellerId) != 0)
                return false;
        }
        return true;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void increaseProductViews() {
        this.views++;
    }

    public int getViews() {
        return views;
    }

    public LocalDateTime getAddingDate() {
        return addingDate;
    }

    public void changeStateAccepted() {
        try {
            state = RequestableState.ACCEPTED;
            Seller seller = (Seller) Account.getAccountWithUsername(sellersUsername.get(0));
            int num = countWithName.get(seller.getUsername());
            seller.addToProductsToSell(this, num);
            allProducts.add(this);
        } catch (Exception e) {
        }
    }

    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    public void changeStateEdited(ArrayList<Field> generalFields, String description, int count,
                                  double price, Seller seller) throws Exception {
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
        priceWithName.replace(seller.getUsername(), editedProduct.getPrice(seller));
        countWithName.replace(seller.getUsername(), editedProduct.getCount(seller));
        editedProduct = null;
        state = RequestableState.ACCEPTED;
    }

    public Buyer getBuyerByUsername(String username) {
        for (String buyerUsername : buyersUsername) {
            if (buyerUsername.equals(username)) {
                try {
                    Buyer buyer = (Buyer) Account.getAccountWithUsername(username);
                    return buyer;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public Seller getSellerByUsername(String username) {
        for (String sellerName : sellersUsername) {
            if (sellerName.equals(username)) {
                try {
                    Seller seller = (Seller) Account.getAccountWithUsername(username);
                    return seller;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public void removeSeller(String username) {
        Seller seller = getSellerByUsername(username);
        sellersUsername.remove(seller.getUsername());
        priceWithName.remove(seller.getUsername());
        countWithName.remove(seller.getUsername());
    }

    public void addSeller(Seller seller, int count, double price) {
        sellersUsername.add(seller.getUsername());
        this.countWithName.put(seller.getUsername(), count);
        this.priceWithName.put(seller.getUsername(), price);
    }

    public void addCategory(SubCategory subCategory) {
        categoriesName.add(subCategory.getName());
    }

    public void addBuyer(Buyer buyer) {
        buyersUsername.add(buyer.getUsername());
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
        ArrayList<Seller> sellers = new ArrayList<>();
        for (String sellerName : sellersUsername) {
            try {
                Seller seller = (Seller) Account.getAccountWithUsername(sellerName);
                sellers.add(seller);
            } catch (Exception e) {
            }
        }
        return sellers;
    }

    public ArrayList<Buyer> getBuyers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (String buyerName : buyersUsername) {
            try {
                Buyer buyer = (Buyer) Account.getAccountWithUsername(buyerName);
                buyers.add(buyer);
            } catch (Exception e) {
            }
        }
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

    public void addBuyerToBuyers(Buyer buyer) {
        buyersUsername.add(buyer.getUsername());
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    public int getCount(Seller seller) {
        return countWithName.get(seller.getUsername());
    }

    public double getPrice(Seller seller) {
        return priceWithName.get(seller.getUsername());
    }

    public double getPriceWithOff(Seller seller) {
        Sale sale = Sale.getProductSale(this);
        double price = getPrice(seller);
        if (sale == null)
            return price;
        else {
            if (sale.getSeller().equals(seller)) {
                return price * (1 - sale.getSalePercentage());
            } else
                return price;
        }
    }

    public boolean isInSale() {
        Sale sale = Sale.getProductSale(this);
        return sale != null;
    }

    public boolean isInSale(Seller seller) {
        Sale sale = Sale.getProductSale(this);
        return sale != null && sale.getSeller().equals(seller);
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    public HashMap<Seller, Integer> getCount() {
        HashMap<Seller, Integer> count = new HashMap<>();
        for (String name : countWithName.keySet()) {
            try {
                Seller seller = (Seller) Account.getAccountWithUsername(name);
                count.put(seller, countWithName.get(name));
            } catch (Exception e) {
            }
        }
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

    public static Product getProductWithItsName(String name) throws Exception {
        for (Product product : allProducts) {
            if (product.getName().equals(name))
                return product;
        }
        throw new productNotFoundException();
    }

    public ArrayList<SubCategory> getCategories() {
        ArrayList<SubCategory> categories = new ArrayList<>();
        for (String categoryName : categoriesName) {
            try {
                SubCategory subCategory = SubCategory.getCategoryByName(categoryName);
                categories.add(subCategory);
            } catch (Exception e) {
            }
        }
        return categories;
    }

    public HashMap<Seller, Double> getPrice() {
        HashMap<Seller, Double> price = new HashMap<>();
        for (String name : priceWithName.keySet()) {
            try {
                Seller seller = (Seller) Account.getAccountWithUsername(name);
                price.put(seller, priceWithName.get(name));
            } catch (Exception e) {
            }
        }
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
        if (getScores().size()==0)
            return 0;
        return sum / getScores().size();
    }

    public void decreaseCountSeller(Seller seller, int number) {
        int temp = countWithName.get(seller.getUsername());
        countWithName.replace(seller.getUsername(), temp - number);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Comment getCommentWithId(String id) throws Exception {
        for (Comment comment : comments) {
            if (comment.getCommentId().equals(id))
                return comment;
        }
        throw new CommentDidNotExist();
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

    public static class CommentDidNotExist extends Exception {
        public CommentDidNotExist() {
            super("comment doesn't exist");
        }
    }

    @Override
    public String toString() {
        try {
            Seller seller = (Seller) Account.getAccountWithUsername(sellersUsername.get(0));
            String productString = "Name                : " + name + "\n" + "\n" +
                    "RequestType         : Product" + "\n" + "\n" +
                    "Seller              : " + seller.getName() + "\n" + "\n" +
                    "Price               : " + priceWithName.get(seller.getUsername()) + "\n" + "\n" +
                    "Count               : " + countWithName.get(seller.getUsername());
            if (state.equals(RequestableState.EDITED))
                productString = "<Edited>\n" + productString;
            return productString;
        } catch (Exception e) {
            return null;
        }
    }

    public void setSellers(ArrayList<Seller> sellers) {
        ArrayList<String> names = new ArrayList<>();
        for (Seller seller : sellers) {
            names.add(seller.getUsername());
        }
        this.sellersUsername = names;
    }

    public void setBuyers(ArrayList<Buyer> buyers) {
        ArrayList<String> names = new ArrayList<>();
        for (Buyer buyer : buyers) {
            names.add(buyer.getUsername());
        }
        this.buyersUsername = names;
    }

    public void setPrice(HashMap<Seller, Double> price) {
        HashMap<String, Double> newOne = new HashMap<>();
        for (Seller seller : price.keySet()) {
            newOne.put(seller.getUsername(), price.get(seller));
        }
        this.priceWithName = newOne;
    }

    public void setCount(HashMap<Seller, Integer> count) {
        HashMap<String, Integer> newOne = new HashMap<>();
        for (Seller seller : count.keySet()) {
            newOne.put(seller.getUsername(), count.get(seller));
        }
        this.countWithName = newOne;
    }

    public static void store() {
        storeProducts();
        storeNumberOfProducts();
    }

    public static void load() {
        loadProducts();
        loadNumberOfProducts();
    }

    public static void storeProducts() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutProduct/products.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Product product : allProducts) {
                fileWriter.write(yaGson.toJson(product) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadProducts() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutProduct/products.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Product product = yaGson.fromJson(fileScanner.nextLine(), Product.class);
                allProducts.add(product);
            }
        } catch (Exception ignored) {
        }
    }

    public static void storeNumberOfProducts() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutProduct/numberOfProducts.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(productCount) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadNumberOfProducts() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutProduct/numberOfProducts.txt");
            Scanner fileScanner = new Scanner(inputStream);
            productCount = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }

    public RequestType getRequestType() {
        return RequestType.Product;
    }

    public GraphicPackage getGraphicPackage() {
        return graphicPackage;
    }

    public double getFirstPrice() {
        return getPrice(getSellerByUsername(sellersUsername.get(0)));
    }
}
