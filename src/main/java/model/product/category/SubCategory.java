package model.product.category;


import com.gilecode.yagson.YaGson;
import controller.product.filter.Filterable;
import model.filter.Filter;
import model.filter.OptionalFilter;
import model.product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SubCategory extends Category{
    private static ArrayList<SubCategory> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<Filter> filters = new ArrayList<>();
    private ArrayList<String> fieldNames;
    private ArrayList<String> subCategoriesName;
    private ArrayList<String> productIDs;
    private CategorySet parent;

    public SubCategory(String name, CategorySet parent) throws Exception {
        if (checkName(name)) {
            this.name = name;
            this.parent = parent;
            this.fieldNames = new ArrayList<>();
            this.subCategoriesName = new ArrayList<>();
            this.productIDs = new ArrayList<>();
            allCategories.add(this);
        } else {
            throw new CategoryNameException();
        }
    }

    public SubCategory(String name) throws Exception {
        if (checkName(name)) {
            this.name = name;
            this.parent = null;
            this.fieldNames = new ArrayList<>();
            this.subCategoriesName = new ArrayList<>();
            this.productIDs = new ArrayList<>();
            allCategories.add(this);
        } else {
            throw new CategoryNameException();
        }
    }

    public Boolean checkName(String name) {
        for (SubCategory subCategory : allCategories) {
            if (subCategory.getName().equals(name))
                return false;
        }
        for (CategorySet categorySet : CategorySet.getAllCategorySets()) {
            if (categorySet.getName().equals(name))
                return false;
        }
        return true;
    }

    public static ArrayList<SubCategory> getAllCategories() {
        return allCategories;
    }

    public static void AddToCategories(SubCategory subCategory) {
        allCategories.add(subCategory);
    }

    public static void removeCategory(String categoryName) throws Exception {
        for (SubCategory subCategory : allCategories) {
            if (subCategory.name.equals(categoryName)) {
                allCategories.remove(subCategory);
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
        if (!fieldNames.contains(field)) {
            this.fieldNames.add(field);
            filters.add(new OptionalFilter(field));
        }
    }

    public void removeField(String field) throws Filterable.FilterNotFoundException {
        Filter filter = getFilterByName(field);
        if (filter == null)
            throw new Filterable.FilterNotFoundException();
        filters.remove(filter);fieldNames.remove(field);
        fieldNames.remove(field);
    }

    private Filter getFilterByName(String name) {
        for (Filter filter : filters) {
            if (filter.getName().equals(name))
                return filter;
        }
        return null;
    }

    public void removeProduct(Product product) {
        this.productIDs.remove(product.getId());
    }

    public void removeSubCategory(SubCategory subCategory) {
        this.subCategoriesName.remove(subCategory.getName());
    }

    public ArrayList<SubCategory> getSubCategories() {
        ArrayList<SubCategory> subCategories = new ArrayList<>();
        for (String categoryName : subCategoriesName) {
            try {
                SubCategory subCategory = getCategoryByName(categoryName);
                subCategories.add(subCategory);
            } catch (Exception e) {
            }
        }
        return subCategories;
    }

    public void addSubCategory(SubCategory subCategory) {
        this.subCategoriesName.add(subCategory.getName());
        for (Product product : subCategory.getProducts())
            this.productIDs.add(product.getId());
    }

    public ArrayList<Product> getProducts() {
        return Product.getAllProducts().stream()
                .filter(product -> {
                    for (Filter filter : filters)
                        if (!filter.validFilter(product))
                            return false;
                    return true;
                })
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }

    public void addField(Product product) {
        this.productIDs.add(product.getId());
    }

    public CategorySet getParent() {
        return parent;
    }

    public void setParent(CategorySet parent) {
        this.parent = parent;
    }

    public static SubCategory getCategoryByName(String categoryName) throws Exception {
        for (SubCategory subCategory : SubCategory.allCategories) {
            if (subCategory.getName().equals(categoryName))
                return subCategory;
        }
        throw new CategoryDoesNotFoundException();
    }

    public static class CategoryDoesNotFoundException extends Exception {
        public CategoryDoesNotFoundException() {
            super("category doesn't exist");
        }
    }

    public static class CategoryNameException extends Exception {
        public CategoryNameException() {
            super("we have another category with this name!");
        }
    }

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutProduct/Category.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (SubCategory subCategory : allCategories) {
                fileWriter.write(yaGson.toJson(subCategory) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutProduct/Category.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                SubCategory subCategory = yaGson.fromJson(fileScanner.nextLine(), SubCategory.class);
                allCategories.add(subCategory);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString() {
        StringBuilder fieldNamesString = new StringBuilder();
        StringBuilder subCategoriesString = new StringBuilder();
        StringBuilder productIDsString = new StringBuilder();
        int i = 1, j = 1, k = 1;
        for (String fieldName : fieldNames) {
            fieldNamesString.append(i++).append(": ").append(fieldName).append("\n");
        }
        for (String sub : subCategoriesName) {
            try {
                subCategoriesString.append(j++).append(": ").append(getCategoryByName(sub).toString()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String productID : productIDs) {
            try {
                productIDsString.append(k++).append(": ").append(Product.getProductById(productID).getName()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "name: " + name + '\n' +
                "fieldNames: \n" + fieldNamesString +
                "subCategories: \n" + subCategoriesString +
                "product names: \n " + productIDsString;
    }
}
