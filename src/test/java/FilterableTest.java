import controller.product.filter.AllProductsController;
import mockit.Tested;
import model.account.Buyer;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import model.product.Score;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FilterableTest {


    @Tested
    AllProductsController allProductsController = AllProductsController.getInstance();

    LocalDateTime start = LocalDateTime.now(), end = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    ArrayList<Field> fields = new ArrayList<>(), fields1 = new ArrayList<>(), fields2 = new ArrayList<>();
    Seller seller = new Seller("ehsan", "ehsan", "ehsan@", "4444"
            , "aaa", "ehsan", 5000, "aaa");
    Buyer buyer = new Buyer("sadegh", "amoo", "amoo@", "1"
            , "biJohar", "sadegh", 500000);

    @Before
    public void preProcess() {
        strings.add("this is option");
        strings.add("second");
        strings.add("third");
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("test");
        Field numericalField = new NumericalField("numeric", FieldType.NUMERICAL, 0);
        Field numericalField1 = new NumericalField("numeric1", FieldType.NUMERICAL, 10);
        Field numericalField2 = new NumericalField("numeric1", FieldType.NUMERICAL, 15);
        Field optionalFiled = new OptionalField("optional", FieldType.OPTIONAL, strings);
        Field optionalFiled1 = new OptionalField("optional1", FieldType.OPTIONAL, strings1);
        Field optionalFiled2 = new OptionalField("optional1", FieldType.OPTIONAL, strings1);
        fields.add(numericalField);
        fields1.add(numericalField1);
        fields2.add(numericalField2);
        fields.add(optionalFiled);
        fields1.add(optionalFiled1);
        fields2.add(optionalFiled2);
        Product product = new Product(fields, seller, "milk", "description", 10, 500);
        product.changeStateAccepted();
        Product product1 = new Product(fields1, seller, "coco", "description1", 5, 1000);
        product1.changeStateAccepted();
        Product product2 = new Product(fields2, seller, "choc", "description", 22, 600);
        product2.changeStateAccepted();
        Product product5 = new Product(fields, seller, "milk", "description5", 10, 500);
        //product5.changeStateAccepted();
        Score score = new Score(buyer, 3, product);
        Score score1 = new Score(buyer, 5, product1);
        Score score2 = new Score(buyer, 1, product2);
        product.addScore(score);
        product1.addScore(score1);
        product2.addScore(score2);
        products.add(product);
        products.add(product1);
        products.add(product2);
        products.add(product5);
    }


    @Test
    public void showProductsByScoresInAllProductsController() {
        allProductsController.getProducts().clear();
        ArrayList<Product> productArrayList = new ArrayList<>();
        productArrayList.add(products.get(1));
        productArrayList.add(products.get(0));
        productArrayList.add(products.get(2));
        try {
            allProductsController.changeSort("ByScores");
            ArrayList<Product> result = allProductsController.showProducts();
            Assert.assertEquals(productArrayList, result);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showProductsByDatesInAllProductsController() {
        allProductsController.getProducts().clear();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Product product4 = new Product(fields2, seller, "chocoooo", "description2a", 22, 600);
        product4.changeStateAccepted();
        productArrayList.add(product4);
        productArrayList.add(products.get(2));
        productArrayList.add(products.get(1));
        productArrayList.add(products.get(0));
        try {
            allProductsController.changeSort("ByDates");
            ArrayList<Product> result = allProductsController.showProducts();
            Assert.assertEquals(productArrayList, result);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showProductsByNumberOfViewsInAllProductsController() {
        allProductsController.getProducts().clear();
        ArrayList<Product> productArrayList = new ArrayList<>();
        Product product4 = new Product(fields2, seller, "chocoooo", "description2a", 22, 600);
        product4.addToNumberOfViews();
        product4.addToNumberOfViews();
        product4.addToNumberOfViews();
        products.get(0).addToNumberOfViews();
        products.get(0).addToNumberOfViews();
        products.get(1).addToNumberOfViews();
        products.get(1).addToNumberOfViews();
        products.get(1).addToNumberOfViews();
        products.get(1).addToNumberOfViews();
        products.get(2).addToNumberOfViews();
        product4.changeStateAccepted();
        productArrayList.add(products.get(1));
        productArrayList.add(product4);
        productArrayList.add(products.get(0));
        productArrayList.add(product4);
        productArrayList.add(products.get(2));
        try {
            allProductsController.changeSort("ByNumberOfViews");
            ArrayList<Product> result = allProductsController.showProducts();
            Assert.assertEquals(productArrayList, result);
        } catch (Exception e) {
            Assert.fail();
        }
    }


    @Test
    public void filterByName() {
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add("milk");
        try {
            allProductsController.filter("product name", arrayList);
            ArrayList<Product> arrayList1 = new ArrayList<>();
            arrayList1.add(products.get(0));
            arrayList1.add(products.get(3));
            //products1.add(products.get(1));
            //products1.add(products1.get(2));
            ArrayList<Product> result = allProductsController.getProducts();
            Assert.assertEquals(arrayList1, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void filterByNumerical() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("numeric1");
        arrayList.add("5");
        arrayList.add("20");

        try {
            allProductsController.filter("numerical field", arrayList);
            ArrayList<Product> arrayList1 = new ArrayList<>();
            arrayList1.add(products.get(1));
            arrayList1.add(products.get(2));
            ArrayList<Product> result = allProductsController.getProducts();
            Assert.assertEquals(arrayList1, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void filterByOptional() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("optional1");
        strings.add("test");
        /*arrayList.add("optional");
        arrayList.add("this is option");
        arrayList.add("second");
        arrayList.add("third");*/
        try {
            allProductsController.filter("optional field", arrayList);
            ArrayList<Product> arrayList1 = new ArrayList<>();
            arrayList1.add(products.get(1));
            arrayList1.add(products.get(2));
            //arrayList1.add(products.get(0));
            ArrayList<Product> result = allProductsController.getProducts();
            Assert.assertEquals(arrayList1, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
