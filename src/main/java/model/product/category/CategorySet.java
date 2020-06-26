package model.product.category;

import com.gilecode.yagson.YaGson;
import model.product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class CategorySet extends Category {
    private static ArrayList<CategorySet> allCategorySets = new ArrayList<>();
    private ArrayList<String> categorySets;
    private ArrayList<String> subCategories;

    public CategorySet(String name) throws CategoryNameException {
        super(name);
        this.name = name;
        this.subCategories = new ArrayList<>();
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
        for (CategorySet categorySet : getCategorySets()) {
            products.addAll(categorySet.getProducts());
        }
        for (SubCategory subCategory : getSubCategories()) {
            products.addAll(subCategory.getProducts());
        }
        return products;
    }

    public ArrayList<CategorySet> getCategorySets() {
        ArrayList<CategorySet> categorySetArrayList = new ArrayList<>();
        for (String categorySet : categorySets) {
            try {
                categorySetArrayList.add(CategorySet.getCategorySetByName(categorySet));
            } catch (CategoryDoesNotFoundException ignored) {
            }
        }
        return categorySetArrayList;
    }

    public void addToCategorySets(CategorySet categorySet) {
        this.categorySets.add(categorySet.getName());
    }

    public ArrayList<SubCategory> getSubCategories() {
        ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
        for (String subCategory : subCategories) {
            try {
                subCategoryArrayList.add(SubCategory.getCategoryByName(subCategory));
            } catch (Exception ignored) {
            }
        }
        return subCategoryArrayList;
    }

    public void addToCategories(SubCategory subCategory) {
        this.subCategories.add(subCategory.getName());
    }

    public static ArrayList<CategorySet> getAllCategorySets() {
        return allCategorySets;
    }

    public void removeSubCategory(SubCategory subCategory) {
        subCategories.remove(subCategory.getName());
    }

    public void removeSubCategorySet(CategorySet categorySet) {
        categorySets.remove(categorySet.getName());
    }

    public static class CategoryDoesNotFoundException extends Exception {
        public CategoryDoesNotFoundException() {
            super("category doesn't exist");
        }
    }

    @Override
    public String toString() {
        String string = "Name: " + name + "\n" + "Category sets name: " + "\n";
        int i = 1, j = 1;
        for (CategorySet categorySet : getCategorySets()) {
            string += (i++) + ": " + categorySet.getName() + "\n";
        }
        string = string + "Categories name: " + "\n";
        for (SubCategory subCategory : getSubCategories()) {
            string += (j++) + ": " + subCategory.getName() + "\n";
        }
        return string;
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
