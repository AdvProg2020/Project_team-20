package model.product;


import java.util.ArrayList;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<String> fieldNames;
    private ArrayList<String> subCategoriesName;
    private ArrayList<String> productIDs;
    private Category parent;

    public Category(String name, Category parent) throws Exception {
        if(checkName(name)) {
            this.name = name;
            this.parent = parent;
            this.fieldNames = new ArrayList<>();
            this.subCategoriesName = new ArrayList<>();
            this.productIDs = new ArrayList<>();
            allCategories.add(this);
        }
        else {
            throw new CategoryNameException();
        }
    }

    public Category(String name) throws Exception{
        if(checkName(name)) {
            this.name = name;
            this.parent = null;
            this.fieldNames = new ArrayList<>();
            this.subCategoriesName = new ArrayList<>();
            this.productIDs = new ArrayList<>();
            allCategories.add(this);
        }
        else{
            throw new CategoryNameException();
        }
    }

    public Boolean checkName(String name){
        for(Category category : allCategories){
            if(category.getName().equals(name))
                return false;
        }
        return true;
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
        this.productIDs.remove(product.getId());
    }

    public void removeSubCategory(Category subCategory){
        this.subCategoriesName.remove(subCategory.getName());
    }

    public ArrayList<Category> getSubCategories() {
        ArrayList<Category> subCategories = new ArrayList<>();
        for(String categoryName : subCategoriesName){
            try {
                Category category = getCategoryByName(categoryName);
                subCategories.add(category);
            }
            catch (Exception e){
            }
        }
        return subCategories;
    }

    public void addSubCategory(Category subCategory) {
        this.subCategoriesName.add(subCategory.getName());
        for(Product product:subCategory.getProducts())
            this.productIDs.add(product.getId());
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> allProducts = new ArrayList<>();
        for(String productID : productIDs){
            try {
                Product product = Product.getProductById(productID);
                allProducts.add(product);
            }
            catch (Exception e){
            }
        }
        return allProducts;
    }

    public void addProduct(Product product) {
        this.productIDs.add(product.getId());
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

    public class CategoryNameException extends Exception {
        public CategoryNameException() {
            super("we have another category with this name!");
        }
    }




}
