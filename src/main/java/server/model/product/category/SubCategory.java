package server.model.product.category;


import com.gilecode.yagson.YaGson;
import server.controller.product.filter.Filterable;
import server.model.filter.Filter;
import server.model.filter.OptionalFilter;
import server.model.product.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SubCategory extends Category {
    private static ArrayList<SubCategory> allSubCategories = new ArrayList<>();
    private ArrayList<Filter> filters = new ArrayList<>();
    private ArrayList<String> fieldNames;
    private ArrayList<String> productIDs;
    private CategorySet parent;

    public SubCategory(String name, CategorySet parent) throws Exception {
        super(name);
        this.parent = parent;
        this.fieldNames = new ArrayList<>();
        this.productIDs = new ArrayList<>();
        allSubCategories.add(this);
        Category.addToAllCategories(this);
    }

    public SubCategory(String name) throws Exception {
        super(name);
        this.parent = null;
        this.fieldNames = new ArrayList<>();
        this.productIDs = new ArrayList<>();
        allSubCategories.add(this);
        Category.addToAllCategories(this);
    }

    public static ArrayList<SubCategory> getAllSubCategories() {
        return allSubCategories;
    }

    public static void removeCategory(String categoryName) throws Exception {
        for (SubCategory subCategory : allSubCategories) {
            if (subCategory.name.equals(categoryName)) {
                allSubCategories.remove(subCategory);
                Category.removeFromAllCategories(subCategory);
                return;
            }
        }
        throw new CategoryDoesNotFoundException();
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
        filters.remove(filter);
        fieldNames.remove(field);
        fieldNames.remove(field);
        setProductIDs();
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

    private void setProductIDs() {
        productIDs = new ArrayList<>();
        for (Product product : getProducts()) {
            productIDs.add(product.getId());
        }
    }

    public void addField(Product product) {
        this.productIDs.add(product.getId());
        setProductIDs();
    }

    public CategorySet getParent() {
        return parent;
    }

    public void setParent(CategorySet parent) {
        this.parent = parent;
    }

    public static SubCategory getCategoryByName(String categoryName) throws Exception {
        for (SubCategory subCategory : SubCategory.allSubCategories) {
            if (subCategory.getName().equals(categoryName))
                return subCategory;
        }
        throw new CategoryDoesNotFoundException();
    }


    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutProduct/Category.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (SubCategory subCategory : allSubCategories) {
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
                allSubCategories.add(subCategory);
                Category.addToAllCategories(subCategory);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString() {
        StringBuilder fieldNamesString = new StringBuilder();
        StringBuilder productIDsString = new StringBuilder();
        int i = 1, k = 1;
        for (String fieldName : fieldNames) {
            fieldNamesString.append(i++).append(": ").append(fieldName).append("\n");
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
                "product names: \n " + productIDsString;
    }
}
