package model.product.category;

import com.gilecode.yagson.YaGson;
import model.product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutProduct/CategorySet.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (CategorySet categorySet : allCategorySets) {
                fileWriter.write(yaGson.toJson(categorySet) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutProduct/CategorySet.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                CategorySet categorySet = yaGson.fromJson(fileScanner.nextLine(), CategorySet.class);
                allCategorySets.add(categorySet);
            }
        } catch (Exception ignored) {
        }
    }
}
