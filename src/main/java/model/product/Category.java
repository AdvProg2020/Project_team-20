package model.product;

import model.product.Field.Field;

import java.util.ArrayList;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<String> fieldNames;
    private ArrayList<Category> subCategories;
    private ArrayList<Product> products;
    private Category parent;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        this.fieldNames = new ArrayList<>();
        this.subCategories = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public Category(String name){
        this.name = name;
        this.parent = null;
        this.fieldNames = new ArrayList<>();
        this.subCategories = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static void AddToCategories(Category category) {
        allCategories.add(category);
    }

    public static void removeCategory(String categoryName) throws Exception{
        for(Category category : allCategories){
            if(category.name.equals(categoryName)){
                allCategories.remove(category);
                return;
            }
        }
        throw new CategoryDoesNotFoundException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFields() {
        return fieldNames;
    }

    public void addField(String field) {
        this.fieldNames.add(field);
    }

    public void removeField(String field){
        this.fieldNames.remove(field);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }

    public void removeSubCategory(Category subCategory){
        this.subCategories.remove(subCategory);
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

    public static Category getCategoryByName(String categoryName) throws Exception{
     for(Category category:allCategories){
         if(category.name.equals(categoryName))
             return category;
     }
     throw new CategoryDoesNotFoundException();
    }

    public static class CategoryDoesNotFoundException extends Exception {
        public CategoryDoesNotFoundException() {
            super("category doesn't exist");
        }
    }

}
