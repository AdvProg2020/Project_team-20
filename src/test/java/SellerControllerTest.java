import client.controller.MainController;
import client.controller.account.user.seller.SellerController;
import mockit.Tested;
import client.model.account.Buyer;
import client.model.account.Seller;
import client.model.product.Field.Field;
import client.model.product.Field.FieldType;
import client.model.product.Field.NumericalField;
import client.model.product.Field.OptionalField;
import client.model.product.Product;
import client.model.product.Sale;
import client.model.receipt.SellerReceipt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;


public class SellerControllerTest {

    @Tested
    SellerController sellerController;
    MainController mainController;


    Sale sale;
    LocalDateTime start = LocalDateTime.now(), end = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    HashMap<Product, Integer> productIntegerHashMap = new HashMap<>();
    Seller seller = new Seller("ehsan", "ehsan", "ehsan@", "4444"
            , "aaa", "ehsan", 5000, "aaa");
    Buyer buyer = new Buyer("sadegh", "amoo", "amoo@", "1"
            , "biJohar", "sadegh", 500000);
    ArrayList<Field> fields = new ArrayList<>();
    Product product;
    SellerReceipt sellerReceipt;

    @Before
    public void preProcess() {
        strings.add("this is option");
        Field numericalField = new NumericalField("numeric", FieldType.NUMERICAL, 55555);
        Field optionalFiled = new OptionalField("optional", FieldType.OPTIONAL, strings);
        product = new Product(fields, seller, "milk", "description", 5, 5);
        fields.add(numericalField);
        fields.add(optionalFiled);
        products.add(product);
        sale = new Sale("id", products, start, end, 5, seller);
        sellerReceipt = new SellerReceipt("5", 5, productIntegerHashMap, true,
                5, buyer, 5);
        mainController = MainController.getInstance();
        mainController.setAccount(seller);
        sellerController = SellerController.getInstance();
        seller.addToSaleHistory(sellerReceipt);
        product.changeStateAccepted();
    }


    @Test
    public void testViewCompanyInformation() {
        String result = sellerController.viewCompanyInformation();
        Assert.assertEquals("aaa", result);
    }

    @Test
    public void testViewSalesHistory() {
        ArrayList<SellerReceipt> result = sellerController.viewSalesHistory();
        ArrayList<SellerReceipt> sellerReceipts = new ArrayList<>();
        sellerReceipts.add(sellerReceipt);
        Assert.assertEquals(sellerReceipts, result);
        sellerReceipts.clear();
    }

    @Test
    public void viewProductWithIdTest() {
        try {
            Product result = sellerController.viewProduct("1");
            Assert.assertEquals(product, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void viewProductWithIdHaveException() {
        try {
            Product result = sellerController.viewProduct("2");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getAllProductsTest() {
        ArrayList<Product> result = sellerController.getAllProducts();
        Assert.assertEquals(products, result);
    }



}
