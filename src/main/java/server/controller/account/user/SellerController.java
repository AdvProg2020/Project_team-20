package server.controller.account.user;

import server.controller.MainController;
import javafx.scene.image.Image;
import server.model.account.Account;
import server.model.account.Buyer;
import server.model.account.Manager;
import server.model.account.Seller;
import server.model.product.*;
import server.model.product.Field.Field;
import server.model.product.Field.FieldType;
import server.model.product.Field.NumericalField;
import server.model.product.Field.OptionalField;
import server.model.product.category.SubCategory;
import server.model.receipt.SellerReceipt;
import server.network.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SellerController implements AccountController {
    private static SellerController sellerController = null;
    private MainController mainController = MainController.getInstance();
    private static Seller seller;

    private SellerController() {
    }

    public static SellerController getInstance() {
        if (sellerController == null)
            sellerController = new SellerController();
        seller = (Seller) MainController.getInstance().getAccount();
        return sellerController;
    }

    public void addAdvertisement(Product product, Seller seller, String text) throws Exception {
        if (seller.getCredit() < 500000)
            throw new Account.notEnoughMoneyException();
        Advertisement adv = new Advertisement(seller, product, LocalDateTime.now().plusDays(5), text);
        Manager.addRequest(adv);
    }

    public String viewCompanyInformation() {
        return seller.getDetails();
    }

    public ArrayList<SellerReceipt> viewSalesHistory() {
        return seller.getSaleHistory();
    }

    public ArrayList<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public ArrayList<Product> getSellerProducts() {
        return seller.getProducts();
    }

    public Product viewProduct(String productId) throws Exception {
        return Product.getProductById(productId);
    }

    public ArrayList<Buyer> viewBuyers(String productId) throws Exception {
        Product product = Product.getProductById(productId);
        return product.getBuyers();
    }

    public void editProduct(String productId, ArrayList<String> details, ArrayList<String> numericalFieldsToRemove,
                            HashMap<String, Double> numericalFieldsToAdd,
                            ArrayList<String> optionalFieldsTORemove,
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

        if (!details.get(1).isEmpty()) {
            count = Integer.parseInt(details.get(1));
        }
        if (!details.get(2).isEmpty()) {
            price = Double.parseDouble(details.get(2));
        }
        product.changeStateEdited(fields, description, count, price, seller);
        Manager.addRequest(product);
    }

    private void editFields(ArrayList<Field> fields, ArrayList<String> numericalFieldsToRemove,
                            HashMap<String, Double> numericalFieldsToAdd,
                            ArrayList<String> optionalFieldsTORemove,
                            HashMap<String, ArrayList<String>> optionalFieldsToAdd) throws Exception {
        fields.removeAll(createFieldsToRemove(numericalFieldsToRemove, fields));
        fields.removeAll(createFieldsToRemove(optionalFieldsTORemove, fields));
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
                              HashMap<String, ArrayList<String>> optionalFields, String path) {
        String name = details.get(0), description = details.get(1);
        int count = Integer.parseInt(details.get(2));
        double price = Double.parseDouble(details.get(3));
        ArrayList<Field> fields = new ArrayList<>();
        fields.addAll(createNumericalFields(numericalFields));
        fields.addAll(createOptionalFields(optionalFields));
        Product product = new Product(fields, seller, name, description, count, price);
        product.setImagePath(path);
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

    private ArrayList<Field> createFieldsToRemove(ArrayList<String> fieldsToRemove, ArrayList<Field> fields)
            throws Exception {
        ArrayList<Field> newFields = new ArrayList<>();
        for (String field : fieldsToRemove) {
            for (Field newField : fields) {
                if (newField.getName().equals(field))
                    newFields.add(newField);
                else if (!containsField(fields, field)) {
                    throw new hasNotThisFiledException(newField);
                }
            }
        }
        return newFields;
    }

    private boolean containsField(ArrayList<Field> fields, String name) {
        for (Field field : fields) {
            if (field.getName().equals(name))
                return true;
        }
        return false;
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
        Product.removeProduct(productId);
    }

    public ArrayList<SubCategory> showCategories() {
        return SubCategory.getAllSubCategories();
    }

    public ArrayList<Sale> viewOffs() {
        return seller.getSales();
    }

    public Sale viewOff(String offId) throws SaleUnavailableException {
        return getSaleWithId(offId);
    }

    public void editOff(String offId, LocalDateTime startDate, LocalDateTime endDate, String salePercentageStr, ArrayList<String> productIdsToRemove,
                        ArrayList<String> productIdsToAdd) throws Exception {
        Sale sale = getSaleWithId(offId);
        ArrayList<Product> productsToRemove = Product.getProductsWithIds(productIdsToRemove),
                productsToAdd = Product.getProductsWithIds(productIdsToAdd), newProducts = getSaleProducts(offId);
        double salePercentage;
        if (startDate == null) {
            startDate = sale.getStartDate();
        }
        if (endDate == null) {
            endDate = sale.getEndDate();
        }
        if (salePercentageStr != null) {
            salePercentage = Double.parseDouble(salePercentageStr) / 100;
        } else salePercentage = sale.getSalePercentage();

        for (Product product : productsToRemove) {
            if (seller.hasProduct(product.getId()))
                newProducts.remove(product);
            else
                throw new HaveNotThisProductException(product);
        }
        newProducts.addAll(productsToAdd);
        sale.changeStateEdited(newProducts, startDate, endDate, salePercentage);
        Manager.addRequest(sale);
    }

    private ArrayList<Product> getSaleProducts(String offId) throws Exception {
        Sale sale = getSaleWithId(offId);
        return new ArrayList<>(sale.getProducts());
    }

    public void addOff(LocalDateTime startDate, LocalDateTime endDate, double percentage, ArrayList<String> productIds) throws Exception {
        String id = Integer.toString(Sale.allSalesCount);
        try {
            double salePercentage = percentage / 100;
            if (salePercentage > 1)
                throw new discountPercentageNotValidException();
            ArrayList<Product> products = Product.getProductsWithIds(productIds);
            Sale sale = new Sale(id, products, startDate, endDate, salePercentage, seller);
            Manager.addRequest(sale);
        } catch (Exception e) {
            if (e instanceof Product.productNotFoundException)
                throw e;
            else
                throw new FormatInvalidException();
        }
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

    public static class hasNotThisFiledException extends Exception {
        public hasNotThisFiledException(Field field) {
            super(field.getName() + " is not in this product");
        }
    }

    public static class HaveNotThisProductException extends Exception {
        public HaveNotThisProductException(Product product) {
            super("seller have not this product with this id: " + product.getId());
        }

        private String generateString(Product product) {
            return "";
        }
    }

    public static class FormatInvalidException extends Exception {
        public FormatInvalidException() {
            super("Your input format is invalid!");
        }

        private String generateString(Product product) {
            return "";
        }
    }

    public HashMap<Product, Integer> getProductsToSell() {
        return seller.getProductsToSell();
    }

    public int getProductCount(Product product) {
        return seller.getProductCount(product);
    }

    @Override
    public Message getAccountInfo(String username) {
        Message message = new Message("account info");
        try {
            message.addToObjects(Account.getAccountWithUsername(username));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    public Message editField(String field, String context, Account account) throws Exception {
        Seller seller = (Seller) account;
        switch (field) {
            case "name":
                seller.changeStateEdited(context, seller.getLastName(), seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit(), seller.getDetails());
                break;
            case "lastName":
                seller.changeStateEdited(seller.getName(), context, seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit(), seller.getDetails());
                break;
            case "email":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), context, seller.getPhoneNumber(),
                        seller.getPassword(), seller.getCredit(), seller.getDetails());
                break;
            case "phoneNumber":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(), context,
                        seller.getPassword(), seller.getCredit(), seller.getDetails());
                break;
            case "password":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), context, seller.getCredit(), seller.getDetails());
                break;
            case "credit":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), seller.getPassword(), Double.parseDouble(context), seller.getDetails());
                break;
            case "companyInfo":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), seller.getPassword(), seller.getCredit(), context);
                break;
            default:
                Message message = new Message("edit request sent");
                message.addToObjects(new ManagerController.fieldIsInvalidException());
                return message;
        }
        Manager.addRequest(seller);
        return new Message("edit request sent");
    }

    public static class discountPercentageNotValidException extends Exception {
        public discountPercentageNotValidException() {
            super("Discount percentage must be lower than 100!");
        }
    }

    public Message setProfileImage(String path, Account currentSeller) {
        currentSeller.setImagePath(path);
        return new Message("set profile image was successful");
    }

    @Override
    public Message changeMainImage(Image image, Account currentSeller) {
        currentSeller.getGraphicPackage().setMainImage(image);
        return new Message("change main image");
    }

    @Override
    public Message logout(Account currentSeller) {
        //TODO
        return new Message("logout was successful");
    }
}
