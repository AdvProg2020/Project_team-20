package client.view.console.product;

import client.controller.product.ProductController;
import client.model.product.category.SubCategory;
import client.model.product.Field.Field;
import client.model.product.Field.FieldType;
import client.model.product.Field.NumericalField;
import client.model.product.Field.OptionalField;
import client.model.product.Product;
import client.view.console.Menu;

import java.util.ArrayList;

public class CompareMenu extends Menu {
    private Product firstProduct;
    private ArrayList<Product> otherProducts;
    private ArrayList<String> otherProductsId;
    private ProductController productController;

    public CompareMenu(Menu parent, Product firstProduct, String anotherProductId) throws Exception {
        super("CompareMenu");
        this.parent = parent;
        this.firstProduct = firstProduct;
        this.otherProducts = new ArrayList<>();
        this.otherProductsId = new ArrayList<>();
        this.productController = ((ProductMenu) parent).getProductController();
        otherProducts.add(productController.getAnotherProduct(anotherProductId, otherProductsId));
        otherProductsId.add(anotherProductId);
        this.methods.add("showCompares");
        this.methods.add("addProductToCompare");
        this.methods.add("removeProduct");
        this.regex.add("showCompares");
        this.regex.add("add product to compare (\\S+)");
        this.regex.add("remove product (\\S+)");
        preProcess();
    }


    public void showCompares() {
        System.out.println("first product: " + firstProduct.getName());
        categoryDetails(firstProduct);
        for (Product otherProduct : otherProducts) {
            System.out.println("product name: " + otherProduct.getName());
            categoryDetails(otherProduct);
        }
        System.out.println();
        System.out.println("general fields");
        System.out.println("first product: " + firstProduct.getName());
        fieldDetails(firstProduct);
        for (Product otherProduct : otherProducts) {
            System.out.println("product name: " + otherProduct.getName());
            fieldDetails(otherProduct);
        }
    }

    private void categoryDetails(Product otherProduct) {
        for (SubCategory subCategory : otherProduct.getCategories()) {
            System.out.println("category name : " + subCategory.getName());
            for (String field : subCategory.getFields()) {
                System.out.print("field name : " + field);
            }
        }
    }

    private void fieldDetails(Product product) {
        for (Field generalField : product.getGeneralFields()) {
            System.out.print("field name : " + generalField.getName());
            if (generalField.getType().equals(FieldType.NUMERICAL))
                System.out.println("numerical field : " + ((NumericalField) generalField).getNumericalField());
            else System.out.println("optional field : " + ((OptionalField) generalField).getOptionalFiled());
        }
    }

    public void addProductToCompare(String productId) throws Exception {
        otherProducts.add(productController.getAnotherProduct(productId, otherProductsId));
        otherProductsId.add(productId);
    }

    public void removeProduct(String productId) throws Exception {
        otherProducts.remove(productController.getAnotherProduct(productId, otherProductsId));
        otherProductsId.remove(productId);
    }

    @Override
    public void show() {
        super.show();
        showCompares();
    }

    @Override
    public void help() {
        super.help();
    }

    @Override
    public void enterWithName(String subMenuName) {
        super.enterWithName(subMenuName);
    }

    @Override
    public void back() {
        super.back();
    }

}
