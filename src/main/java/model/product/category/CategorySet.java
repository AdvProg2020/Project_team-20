package model.product.category;

import model.product.Product;

import java.util.ArrayList;

public class CategorySet {
    private static ArrayList<CategorySet> allCategorySets = new ArrayList<>();
    private ArrayList<CategorySet> categorySets;
    private ArrayList<Category> categories;
    private String name;

    public CategorySet(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.categorySets = new ArrayList<>();
        allCategorySets.add(this);
    }


    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (CategorySet categorySet : categorySets) {
            products.addAll(categorySet.getProducts());
        }
        for (Category category : categories) {
            products.addAll(category.getProducts());
        }
        return products;
    }

    public ArrayList<CategorySet> getCategorySets() {
        return categorySets;
    }

    public void addToCategorySets(CategorySet categorySet) {
        this.categorySets.add(categorySet);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addToCategories(Category category) {
        this.categories.add(category);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<CategorySet> getAllCategorySets() {
        return allCategorySets;
    }
}
