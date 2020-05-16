import controller.product.filter.Filterable;
import mockit.Tested;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import org.junit.Before;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FilterableTest {


    @Tested
    Filterable filterablel;


    LocalDateTime start = LocalDateTime.now(), end = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    ArrayList<Field> fields = new ArrayList<>(), fields1 = new ArrayList<>(), fields2 = new ArrayList<>();
    Seller seller = new Seller("ehsan", "ehsan", "ehsan@", "4444"
            , "aaa", "ehsan", 5000, "aaa");

    @Before
    public void preProcess() {
        strings.add("this is option");
        strings.add("second");
        strings.add("third");
        Field numericalField = new NumericalField("numeric", FieldType.NUMERICAL, 0);
        Field numericalField1 = new NumericalField("numeric1", FieldType.NUMERICAL, 10);
        Field numericalField2 = new NumericalField("numeric2", FieldType.NUMERICAL, 15);
        Field optionalFiled = new OptionalField("optional", FieldType.OPTIONAL, strings);
        Field optionalFiled1 = new OptionalField("optional1", FieldType.OPTIONAL, strings);
        Field optionalFiled2 = new OptionalField("optional2", FieldType.OPTIONAL, strings);
        fields.add(numericalField);
        fields1.add(numericalField1);
        fields2.add(numericalField2);
        fields.add(optionalFiled);
        fields1.add(optionalFiled1);
        fields2.add(optionalFiled2);
        Product product = new Product(fields, seller, "milk", "description", 10, 500);
        Product product1 = new Product(fields1, seller, "coco", "description1", 5, 1000);
        Product product2 = new Product(fields2, seller, "choc", "description2", 22, 600);
        products.add(product);
        products.add(product1);
        products.add(product2);
    }


}
