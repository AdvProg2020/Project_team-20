package model.product.category;

import model.product.Product;

import java.util.ArrayList;

public abstract class Category {
    protected static ArrayList<Category> allCategories;
    protected String name;

    public Category(String name) throws CategoryNameException {
        if (checkName(name))
            this.name = name;
        else throw new CategoryNameException();
    }

    public abstract ArrayList<Product> getProducts();

    public boolean checkName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return false;
        }
        return true;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static void addToAllCategories(Category category) {
        allCategories.add(category);
    }

    public static void removeFromAllCategories(Category category) {
        allCategories.remove(category);
    }


    public static class CategoryNameException extends Exception {
        public CategoryNameException() {
            super("we have another category with this name!");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
