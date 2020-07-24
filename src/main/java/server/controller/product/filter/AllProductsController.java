package server.controller.product.filter;

import client.model.product.Advertisement;
import client.model.product.Product;
import client.model.product.category.Category;
import client.model.product.category.CategorySet;
import client.model.product.category.SubCategory;
import client.network.Client;
import client.network.Message;
import server.controller.Main;

import java.io.File;
import java.util.ArrayList;

public class AllProductsController extends Filterable {

    public static AllProductsController allProductsController = null;

    private AllProductsController() {
        super(5000);
        setMethods();
        productsToShow = Product.getAllProducts();
        System.out.println("all products controller run");
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


    public static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("invalid command");
        }
    }

    public Message getProduct(String id) {
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

    public Message disableCategoryFields() {
        return disableFilterByCategory();
    }

    public Message productsImage() {
        Message message = new Message("many products");
        message.addToObjects(((ArrayList<Product>) showProducts().getObjects().get(0)).size());
        return message;
    }

    public Message sendProductsImage(int i, Client client) {
        String path = "src/main/resources/Images/" + Product.getAllProducts().get(i).getImagePath();
        client.writeMessage(new Message(path));
        if (client.readMessage().getText().equals("has file"))
            return new Message("okay");
        client.writeFile(new File(path));
        return new Message("success");
    }

    @Override
    protected void handleClient(Client client) {
        client.writeMessage(new Message("client accepted"));
        while (true) {
            Main.storeData();
            Message message = client.readMessage();
            System.out.println(message.getText());
            if (message.getText().equals("buy")) {
                clients.remove(client);
                return;
            }
            try {
                if (message.getText().equals("sendProductsImage"))
                    message.addToObjects(client);
                Message answer = callCommand(message.getText(), message);
                client.setAuthToken(answer.getAuthToken());
                client.writeMessage(answer);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        }
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
        methods.add("productsImage");
        methods.add("sendProductsImage");
    }

    @Override
    public Message filter(String filterType, ArrayList<String> details) {
        return super.filter(filterType, details);
    }

    @Override
    public Message disAbleFilter(String filterName) {
        return super.disAbleFilter(filterName);
    }

    @Override
    public Message getProducts() {
        return super.getProducts();
    }

    @Override
    public Message getProductFilterByCategory() {
        return super.getProductFilterByCategory();
    }

    @Override
    public Message showProducts() {
        return super.showProducts();
    }

    @Override
    public Message changeSort(String newSort) {
        return super.changeSort(newSort);
    }

    @Override
    public Message disableSort() {
        return super.disableSort();
    }

    @Override
    public Message disableFilterByCategory() {
        return super.disableFilterByCategory();
    }

    @Override
    public Message filterByCategory(ArrayList<String> details) {
        return super.filterByCategory(details);
    }

    @Override
    public Message filterByProductName(ArrayList<String> details) {
        return super.filterByProductName(details);
    }

    @Override
    public Message filterByOptionalFilter(ArrayList<String> details) {
        return super.filterByOptionalFilter(details);
    }

    @Override
    public Message filterByNumericalFilter(ArrayList<String> details) {
        return super.filterByNumericalFilter(details);
    }

    @Override
    public Message getFilters() {
        return super.getFilters();
    }

    @Override
    public Message getCurrentSort() {
        return super.getCurrentSort();
    }
}
