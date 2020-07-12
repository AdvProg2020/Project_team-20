package client.controller.product.filter;

import client.model.product.Advertisement;
import client.model.product.Product;
import client.model.product.category.Category;
import client.model.product.category.CategorySet;
import client.model.product.category.SubCategory;
import client.network.Client;
import client.network.Message;

import java.util.ArrayList;

public class AllProductsController extends Filterable {

    public static AllProductsController allProductsController = null;

    private AllProductsController() {
    }

    public static AllProductsController getInstance() {
        if (allProductsController == null)
            allProductsController = new AllProductsController();
        client = new Client(5000);
        Message message = client.readMessage();
        return allProductsController;
    }

    public void purchase() throws Exception {
        Message message = new Message("purchase");
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
    }

    public ArrayList<SubCategory> getAllSubCategories() {
        Message message = new Message("getAllSubCategories");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<SubCategory>) answer.getObjects().get(0);
    }

    public ArrayList<Category> getAllCategories() {
        Message message = new Message("getAllCategories");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Category>) answer.getObjects().get(0);
    }

    public ArrayList<CategorySet> getAllCategorySets() {
        Message message = new Message("getAllCategorySets");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<CategorySet>) answer.getObjects().get(0);
    }

    public ArrayList<Product> getProductsToShow() {
        Message message = new Message("getProductsToShow");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Product>) answer.getObjects().get(0);
    }


    public Product getProduct(String id) throws Exception {
        Message message = new Message("purchase");
        message.addToObjects(id);
        client.writeMessage(message);
        Message answer = client.readMessage();
        if (answer.getText().equals("Error")) {
            throw (Exception) answer.getObjects().get(0);
        }
        return (Product) answer.getObjects().get(0);
    }

    public ArrayList<Advertisement> getAdvertisement() {
        Message message = new Message("getAdvertisement");
        client.writeMessage(message);
        Message answer = client.readMessage();
        return (ArrayList<Advertisement>) answer.getObjects().get(0);
    }

    public void disableCategoryFields() {
        Message message = new Message("disableCategoryFields");
        client.writeMessage(message);
        client.readMessage();
    }
}
