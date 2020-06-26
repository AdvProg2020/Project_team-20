package model.product.category;

import com.gilecode.yagson.YaGson;
import model.product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CategorySet extends Category {
    private static ArrayList<CategorySet> allCategorySets = new ArrayList<>();
    private ArrayList<CategorySet> categorySets;
    private ArrayList<SubCategory> categories;
    private String name;

    public CategorySet(String name) throws CategoryNameException {
        super(name);
        this.name = name;
        this.categories = new ArrayList<>();
        this.categorySets = new ArrayList<>();
        allCategorySets.add(this);
        Category.addToAllCategories(this);
    }

    public static CategorySet getCategorySetByName(String categorySetName) throws CategoryDoesNotFoundException {
        for (CategorySet categorySet : allCategorySets) {
            if (categorySet.getName().equals(categorySetName))
                return categorySet;
        }
        throw new CategoryDoesNotFoundException();
    }

    public static void removeCategorySet(String categorySetName) throws CategoryDoesNotFoundException {
        for (CategorySet categorySet : allCategorySets) {
            if (categorySet.name.equals(categorySetName)) {
                allCategorySets.remove(categorySet);
                Category.removeFromAllCategories(categorySet);
                return;
            }
        }
        throw new CategoryDoesNotFoundException();
    }


    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (CategorySet categorySet : categorySets) {
            products.addAll(categorySet.getProducts());
        }
        for (SubCategory subCategory : categories) {
            products.addAll(subCategory.getProducts());
        }
        return products;
    }

    public ArrayList<CategorySet> getCategorySets() {
        return categorySets;
    }

    public void addToCategorySets(CategorySet categorySet) {
        this.categorySets.add(categorySet);
    }

    public ArrayList<SubCategory> getCategories() {
        return categories;
    }

    public void addToCategories(SubCategory subCategory) {
        this.categories.add(subCategory);
    }

    public static ArrayList<CategorySet> getAllCategorySets() {
        return allCategorySets;
    }

    public void removeSubCategory(SubCategory subCategory) {
        categories.remove(subCategory);
    }

    public void removeSubCategorySet(CategorySet categorySet) {
        categorySets.remove(categorySet);
    }

    public static class CategoryDoesNotFoundException extends Exception {
        public CategoryDoesNotFoundException() {
            super("category doesn't exist");
        }
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
                Category.addToAllCategories(categorySet);
            }
        } catch (Exception ignored) {
        }
    }
}
