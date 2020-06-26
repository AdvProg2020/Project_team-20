package model.product.category;

import model.product.Product;

import java.util.ArrayList;

public abstract class Category {
    ArrayList<SubCategory> categories;
    ArrayList<CategorySet> categorySets;

    public abstract ArrayList<Product> getProducts();

    public ArrayList<CategorySet> getCategorySets() {
        return categorySets;
    }

    public ArrayList<SubCategory> getCategories() {
        return categories;
    }
}
