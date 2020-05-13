package controller.account.user;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.AddSellerRequest;
import model.product.Category;
import model.product.Field.Field;
import model.product.Field.FieldType;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import model.product.Sale;
import model.receipt.SellerReceipt;

import java.time.LocalDateTime;
import java.util.*;

public class SellerController implements AccountController {
    private static SellerController sellerController = null;
    private MainController mainController = MainController.getInstance();
    private Seller seller;

    private SellerController() {
        this.seller = (Seller) mainController.getAccount();
    }

    public static SellerController getInstance() {
        if (sellerController == null)
            return new SellerController();
        return sellerController;
    }

    public String viewCompanyInformation() {
        HashMap<String, String> details = seller.getDetails();
        return details.get("CompanyInformation");
    }

    public ArrayList<SellerReceipt> viewSalesHistory() {
        return seller.getSaleHistory();
    }

    public Product viewProduct(String productId) throws Exception {
        return Product.getProductById(productId);
    }

    public ArrayList<Buyer> viewBuyers(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        return product.getBuyers();
    }

    //TODO nemidonam che konam kase che konam gereftam dastam
    public void editProduct(String productId, ArrayList<String> details, HashMap<String, Double> numericalFieldsToRemove,
                            HashMap<String, Double> numericalFieldsToAdd,
                            HashMap<String, ArrayList<String>> optionalFieldsTORemove,
                            HashMap<String, ArrayList<String>> optionalFieldsToAdd) throws Exception {
        Product product = Product.getProductById(productId);
        ArrayList<Field> fields = new ArrayList<>(product.getGeneralFields());
        editFields(fields, numericalFieldsToRemove, numericalFieldsToAdd, optionalFieldsTORemove,
                optionalFieldsToAdd);
        String description = product.getDescription();
        int count = product.getCount(seller);
        double price = product.getPrice(seller);
        if (!details.get(0).isEmpty()) {
            description = details.get(0);
        }
        //increase or decrease product (manfi bashe ya mosbat(addad vorodi))
        if (!details.get(1).isEmpty()) {
            count += Integer.parseInt(details.get(1));
        }
        if (!details.get(2).isEmpty()) {
            price += Double.parseDouble(details.get(2));
        }
        product.changeStateEdited(fields, description, count, price, seller);
        Manager.addRequest(product);
    }

    private void editFields(ArrayList<Field> fields, HashMap<String, Double> numericalFieldsToRemove,
                            HashMap<String, Double> numericalFieldsToAdd,
                            HashMap<String, ArrayList<String>> optionalFieldsTORemove,
                            HashMap<String, ArrayList<String>> optionalFieldsToAdd) {
        fields.removeAll(createNumericalFields(numericalFieldsToRemove));
        fields.removeAll(createOptionalFields(optionalFieldsTORemove));
        fields.addAll(createOptionalFields(optionalFieldsToAdd));
        fields.addAll(createNumericalFields(numericalFieldsToAdd));
    }


    public void addToProduct(String id, int count, double price) throws Exception {
        if (seller.hasProduct(id)) {
            throw new AlreadyHaveThisProductException();
        } else {
            Product product = Product.getProductById(id);
            AddSellerRequest request = new AddSellerRequest(product, seller, count, price);
            Manager.addRequest(request);
        }
    }


    public void createProduct(ArrayList<String> details, HashMap<String, Double> numericalFields,
                              HashMap<String, ArrayList<String>> optionalFields) {
        String name = details.get(0), description = details.get(1);
        int count = Integer.parseInt(details.get(2));
        double price = Double.parseDouble(details.get(3));
        ArrayList<Field> fields = new ArrayList<>();
        fields.addAll(createNumericalFields(numericalFields));
        fields.addAll(createOptionalFields(optionalFields));
        Product product = new Product(fields, seller, name, description, count, price);
        Manager.addRequest(product);
    }

    private ArrayList<Field> createOptionalFields(HashMap<String, ArrayList<String>> optionalFields) {
        ArrayList<Field> fields = new ArrayList<>();
        String name;
        ArrayList<String> optionalFiled;
        Set<Map.Entry<String, ArrayList<String>>> optionalSet = optionalFields.entrySet();
        for (Map.Entry<String, ArrayList<String>> mentry : optionalSet) {
            name = mentry.getKey();
            optionalFiled = mentry.getValue();
            fields.add(new OptionalField(name, FieldType.OPTIONAL, optionalFiled));
        }
        return fields;
    }

    private ArrayList<Field> createNumericalFields(HashMap<String, Double> numericalFields) {
        ArrayList<Field> fields = new ArrayList<>();
        String name;
        double numericalField;
        Set<Map.Entry<String, Double>> numericalSet = numericalFields.entrySet();
        for (Map.Entry<String, Double> mentry : numericalSet) {
            name = mentry.getKey();
            numericalField = mentry.getValue();
            fields.add(new NumericalField(name, FieldType.NUMERICAL, numericalField));
        }
        return fields;
    }

    public void deleteProduct(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        seller.removeFromProductsToSell(product);
        product.removeSeller(seller.getUsername());
    }

    public ArrayList<Category> showCategories() {
        return Category.getAllCategories();
    }

    public ArrayList<Sale> viewOffs() {
        return seller.getSales();
    }

    public Sale viewOff(String offId) throws SaleUnavailableException {
        return getSaleWithId(offId);
    }

    public void editOff(String offId, ArrayList<String> details, ArrayList<String> productIdsToRemove,
                        ArrayList<String> productIdsToAdd) throws Exception {
        Sale sale = getSaleWithId(offId);
        LocalDateTime startDate, endDate;
        double salePercentage;
        ArrayList<Product> productsToRemove = Product.getProductsWithIds(productIdsToRemove),
                productsToAdd = Product.getProductsWithIds(productIdsToAdd), newProducts = getSaleProducts(offId);
        if (!details.get(0).isEmpty()) {
            startDate = LocalDateTime.parse(details.get(0));
        } else startDate = sale.getStartDate();
        if (!details.get(1).isEmpty()) {
            endDate = LocalDateTime.parse(details.get(1));
        } else endDate = sale.getEndDate();
        if (!details.get(2).isEmpty()) {
            salePercentage = Double.parseDouble(details.get(2));
        } else salePercentage = sale.getSalePercentage();
        //momkene hamchin producty tosh nabashe onvaght chi(in ro throw mikone hala baiad check she
        for (Product product : productsToRemove) {
            if (seller.hasProduct(product.getId()))
                newProducts.remove(product);
            else
                throw new HaveNotThisProductException(product);
        }
        newProducts.addAll(productsToAdd);
        sale.changeStateEdited(newProducts, startDate, endDate, salePercentage);
    }

    private ArrayList<Product> getSaleProducts(String offId) throws Exception {
        Sale sale = getSaleWithId(offId);
        return new ArrayList<>(sale.getProducts());
    }

    public void addOff(ArrayList<String> details, ArrayList<String> productIds) throws Exception {
        String id = details.get(0);
        LocalDateTime startDate = LocalDateTime.parse(details.get(1)), endDate = LocalDateTime.parse(details.get(2));
        double salePercentage = Double.parseDouble(details.get(3));
        ArrayList<Product> products = Product.getProductsWithIds(productIds);
        Sale sale = new Sale(id, products, startDate, endDate, salePercentage);
        Manager.addRequest(sale);
    }

    public double viewBalance() {
        return seller.getCredit();
    }

    private Sale getSaleWithId(String offId) throws SaleUnavailableException {
        for (Sale sale : seller.getSales()) {
            if (sale.getId().equals(offId))
                return sale;
        }
        throw new SaleUnavailableException();
    }

    public static class SaleUnavailableException extends Exception {
        public SaleUnavailableException() {
            super("sale(off) unavailable");
        }
    }

    public static class AlreadyHaveThisProductException extends Exception {
        public AlreadyHaveThisProductException() {
            super("seller already have this product");
        }
    }

    public static class HaveNotThisProductException extends Exception {
        private Product product;

        public HaveNotThisProductException(Product product) {
            super("seller have not this product with this id: " + product.getId());
            this.product = product;
        }

        //check shavad che qalati bokonim
        private String generateString(Product product) {
            return "";
        }
    }

    @Override
    public Account getAccountInfo() {
        return seller;
    }

    @Override
    public void editField(String field, String context) {
        switch (field) {
            case "name":
                seller.changeStateEdited(context, seller.getLastName(), seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit());
                break;
            case "lastName":
                seller.changeStateEdited(seller.getName(), context, seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit());
            case "email":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), context, seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit());
                break;
            case "phoneNumber":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(), context,
                        seller.getPassword(), seller.getCredit());
                break;
            case "password":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), context, seller.getCredit());
                break;
            case "credit":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), seller.getPassword(), Integer.parseInt(context));
        }
        Manager.addRequest(seller);
    }

    @Override
    public void logout() {
        mainController.logout();
    }
}
