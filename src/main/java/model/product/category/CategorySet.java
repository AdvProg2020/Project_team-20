package model.product.category;

import model.product.Product;

import java.util.ArrayList;

public class CategorySet {
    private ArrayList<CategorySet> categorySets;
    private ArrayList<Category> categories;
    private String name;

    public CategorySet(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.categorySets = new ArrayList<>();
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
}
