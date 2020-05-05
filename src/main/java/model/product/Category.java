package model.product;

import java.util.ArrayList;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<Field> fields;
    private ArrayList<Category> subCategories;
    private ArrayList<Product> products;
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        this. fields = new ArrayList<>();
        this. subCategories = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void AddToCategories(Category category) {
        allCategories.add(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
