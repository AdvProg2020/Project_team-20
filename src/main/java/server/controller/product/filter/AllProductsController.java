package server.controller.product.filter;

import client.model.product.Advertisement;
import client.model.product.Product;
import client.model.product.category.Category;
import client.model.product.category.CategorySet;
import client.model.product.category.SubCategory;
import client.network.Message;

public class AllProductsController extends Filterable {

    public static AllProductsController allProductsController = null;

    private AllProductsController() {
        super(5000);
        productsToShow = Product.getAllProducts();
    }

    public static AllProductsController getInstance() {
        if (allProductsController == null)
            allProductsController = new AllProductsController();
        return allProductsController;
    }

    public Message purchase() {
        Message message;
        try {
            throw new InvalidCommandException();
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllSubCategories() {
        Message message = new Message("getAllSubCategories");
        try {
            message.addToObjects(SubCategory.getAllSubCategories());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllCategories() {
        Message message = new Message("getAllCategories");
        try {
            message.addToObjects(Category.getAllCategories());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllCategorySets() {
        Message message = new Message("getAllCategorySets");
        try {
            message.addToObjects(CategorySet.getAllCategorySets());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getProductsToShow() {
        Message message = new Message("getProductsToShow");
        try {
            message.addToObjects(productsToShow);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public static class NotLoggedInException extends Exception {
        public NotLoggedInException() {
            super("You have not logged in. Please go to LoginMenu and log in!:)");
        }
    }

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("invalid command");
        }
    }

    public Message getProduct(String id){
        Message message = new Message("getProduct");
        try {
            Product product = Product.getProductById(id);
            product.addToNumberOfViews();
            message.addToObjects(product);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAdvertisement() {
        Message message = new Message("getAdvertisement");
        try {
            message.addToObjects(Advertisement.getAdds());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message disableCategoryFields()  {
        return disableFilterByCategory();
    }

    @Override
    protected void setMethods() {
        super.setMethods();
        methods.add("purchase");
        methods.add("getAllSubCategories");
        methods.add("getAllCategories");
        methods.add("getAllCategorySets");
        methods.add("getProductsToShow");
        methods.add("getProduct");
        methods.add("getAdvertisement");
        methods.add("disableCategoryFields");
    }
}
