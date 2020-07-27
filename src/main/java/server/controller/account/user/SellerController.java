package server.controller.account.user;

import client.model.account.*;
import client.model.auction.Auction;
import client.model.product.*;
import client.model.product.Field.Field;
import client.model.product.Field.FieldType;
import client.model.product.Field.NumericalField;
import client.model.product.Field.OptionalField;
import client.model.product.category.SubCategory;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;
import javafx.scene.image.Image;
import server.controller.Main;
import client.model.bank.BankReceiptType;
import server.network.server.Server;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SellerController extends Server implements AccountController {
    private static SellerController sellerController = null;

    private SellerController() {
        super(4000);
        setMethods();
        System.out.println("seller controller run");
    }


    @Override
    protected void handleClient(Client client) {
        clients.add(client);
        client.writeMessage(new Message("client accepted"));
        while (true) {
            Main.storeData();
            Message message = client.readMessage();
/*
            try {
                protector.isMessageSecure(message, client.getSocket());
            } catch (Exception e) {
                Message insecureMessage = new Message("Error");
                e.printStackTrace();
                insecureMessage.addToObjects(e);
                client.writeMessage(insecureMessage);
                return;
            }

 */


            System.out.println(message.getText());
            if (message.getText().equals("buy")) {
                clients.remove(client);
                return;
            }
            System.out.println(message);
            if (message.getAuthToken() != null)
                System.out.println(message.getAuthToken().getKey());
            try {
                if (message.getAuthToken().authenticate()) {
                    message.addToObjects(message.getAuthToken());
                    GeneralAccount generalAccount = Main.getAccountWithToken(message.getAuthToken());
                    if (generalAccount instanceof Account) {
                        client.setAccount((Account) generalAccount);
                    }
                    if (message.getText().equals("createFileProduct"))
                        message.addToObjects(client);
                    client.writeMessage(callCommand(message.getText(), message));
                } else {
                    client.writeMessage(new Message("token is invalid"));
                    clients.remove(client);
                    return;
                }
            } catch (InvalidCommand invalidCommand) {
                invalidCommand.printStackTrace();
            } catch (NullPointerException nullPointerException) {
                try {
                    if (message.getText().equals("createFileProduct"))
                        message.addToObjects(client);
                    Message answer = callCommand(message.getText(), message);
                    client.setAuthToken(answer.getAuthToken());
                    client.writeMessage(answer);
                    System.out.println(client);
                } catch (InvalidCommand invalidCommand) {
                    //invalidCommand.printStackTrace();
                    return;
                }
            }
        }
    }

    //i add it
    public Message chargeWallet(double money ,String username,String password , String sourceId , String destId ,AuthToken authToken) throws Exception {
        Client client = new Client(9000);
        client.readMessage();//todo
        Message message1 = new Message("getToken");
        message1.addToObjects(username);
        message1.addToObjects(password);
        client.writeMessage(message1);
        Message answer1 = client.readMessage();
        AuthToken authToken1 = answer1.getAuthToken();
        //not sure
        Message message2 = new Message("createReceipt");
        BankReceiptType bankReceiptType = BankReceiptType.MOVE;
        message2.addToObjects(authToken1);
        message2.addToObjects(bankReceiptType);
        message2.addToObjects(money);
        message2.addToObjects(sourceId);
        message2.addToObjects(destId);
        message2.addToObjects("ERPShop");//description
        client.writeMessage(message2);
        Message answer2 = client.readMessage();
        if(answer2.getText().equals("Error")){
            Message message = new Message("Error");
            message.addToObjects(answer2.getObjects().get(0));
            client.disconnect();
            return message;
        }
        String receiptId = (String) answer2.getObjects().get(0);

        Message message3 = new Message("pay");
        message3.addToObjects(authToken1);
        message3.addToObjects(receiptId);
        client.writeMessage(message3);
        Message answer3 = client.readMessage();
        if(answer3.getText().equals("Error")){
            Message message = new Message("Error");
            message.addToObjects(answer3.getObjects().get(0));
            client.disconnect();
            return message;
        }
        //increase credit
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        //debug
        System.out.println(currentSeller.getCredit());
        currentSeller.increaseCredit(money);
        client.disconnect();
        return new Message("Confirmation");
    }
    //i add it
    public Message withdrawMoneyFromWallet(double money ,String username,String password , String destId ,AuthToken authToken) throws Exception {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        if(money < currentSeller.getCredit()-100000 ) {
            Client client = new Client(9000);
            client.readMessage();
            Message message1 = new Message("getToken");
            message1.addToObjects(username);
            message1.addToObjects(password);
            client.writeMessage(message1);
            Message answer1 = client.readMessage();
            AuthToken authToken1 = answer1.getAuthToken();
            //not sure
            Message message2 = new Message("createReceipt");
            BankReceiptType bankReceiptType = BankReceiptType.DEPOSIT;
            message2.addToObjects(authToken1);
            message2.addToObjects(bankReceiptType);
            message2.addToObjects(money);
            message2.addToObjects("-1");
            message2.addToObjects(destId);
            message2.addToObjects("nothing");//description
            client.writeMessage(message2);
            Message answer2 = client.readMessage();
            if(answer2.getText().equals("Error")){
                Message message6 = new Message("Error");
                message6.addToObjects(answer2.getObjects().get(0));
                return message6;
            }
            String receiptId = (String) answer2.getObjects().get(0);
            //
            Message message3 = new Message("pay");
            message3.addToObjects(authToken1);
            message3.addToObjects(receiptId);
            client.writeMessage(message3);
            Message answer3 = client.readMessage();
            if (answer1.getText().equals("Error")) {
                Message message6 = new Message("Error");
                message6.addToObjects(answer3.getObjects().get(0));
                return message6;
            }
            currentSeller.decreaseCredit(money);
            Message message5 = new Message("Confirmation");
            return message5;
        }
        else{
            Message message6 = new Message("Error");
            message6.addToObjects("you don't have enough money");
            return message6;
        }
    }

    public static SellerController getInstance() {
        if (sellerController == null)
            sellerController = new SellerController();
        return sellerController;
    }

    public Message addAdvertisement(String productId, String sellerUsername, String text, AuthToken authToken) {
        Message message = new Message("addAdvertisement");
        try {
            Seller seller = Seller.getSellerWithUsername(sellerUsername);
            if (seller.getCredit() < 500000)
                throw new Account.notEnoughMoneyException();
            Advertisement adv = new Advertisement(seller, Product.getProductById(productId), LocalDateTime.now().plusDays(5), text);
            Manager.addRequest(adv);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message viewCompanyInformation(AuthToken authToken) {
        Message message = new Message("viewCompanyInformation");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getDetails());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message viewSalesHistory(AuthToken authToken) {
        Message message = new Message("viewSalesHistory");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getSaleHistory());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getAllProducts(AuthToken authToken) {
        Message message = new Message("getAllProducts");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(Product.getAllProducts());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getSellerProducts(AuthToken authToken) {
        Message message = new Message("getSellerProducts");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getProducts());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message viewProduct(String productId, AuthToken authToken) {
        Message message = new Message("viewProduct");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(Product.getProductById(productId));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message viewBuyers(String productId, AuthToken authToken) {
        Message message = new Message("viewBuyers");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            Product product = Product.getProductById(productId);
            message.addToObjects(product.getBuyers());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message editProduct(String productId, ArrayList<String> details, ArrayList<String> numericalFieldsToRemove,
                               HashMap<String, Double> numericalFieldsToAdd,
                               ArrayList<String> optionalFieldsTORemove,
                               HashMap<String, ArrayList<String>> optionalFieldsToAdd, AuthToken authToken) {
        Message message = new Message("editProduct");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
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
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
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


    public Message addToProduct(String id, int count, double price, AuthToken authToken) {
        Message message = new Message("addToProduct");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            if (seller.hasProduct(id)) {
                throw new AlreadyHaveThisProductException();
            } else {
                Product product = Product.getProductById(id);
                AddSellerRequest request = new AddSellerRequest(product, seller, count, price);
                Manager.addRequest(request);
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message createProduct(ArrayList<String> details, HashMap<String, Double> numericalFields,
                                 HashMap<String, ArrayList<String>> optionalFields, String path, AuthToken authToken) {
        Message message = new Message("createProduct");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            String name = details.get(0), description = details.get(1);
            int count = Integer.parseInt(details.get(2));
            double price = Double.parseDouble(details.get(3));
            ArrayList<Field> fields = new ArrayList<>();
            fields.addAll(createNumericalFields(numericalFields));
            fields.addAll(createOptionalFields(optionalFields));
            Product product = new Product(fields, seller, name, description, count, price);
            product.setImagePath(path);
            Manager.addRequest(product);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message createAuction(ArrayList<String> details, String path, LocalDateTime endDate,AuthToken authToken) {
        Message message = new Message("createAuction");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            String name = details.get(0), description = details.get(1);
            int count = 1;
            double price = 0;
            ArrayList<Field> fields = new ArrayList<>();
            Product product = new Product(fields, seller, name, description, count, price);
            product.setImagePath(path);

            new Auction(product, endDate, seller.getUsername());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message createFileProduct(ArrayList<String> details, HashMap<String, Double> numericalFields,
                                     HashMap<String, ArrayList<String>> optionalFields, String fileType, String imgPath, AuthToken authToken
            , Client client) {
        Message message = new Message("createFileProduct");
        client.writeMessage(new Message("in create file product"));
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            String name = details.get(0), description = details.get(1);
            double price = Double.parseDouble(details.get(2));
            //TODO check for read (connect to client)
            File file = client.readFile(name, fileType);
            ArrayList<Field> fields = new ArrayList<>();
            fields.addAll(createNumericalFields(numericalFields));
            fields.addAll(createOptionalFields(optionalFields));
            FileProduct fileProduct = new FileProduct(fields, description, price, seller, file, fileType, name);
            fileProduct.setImagePath(imgPath);
            message.addToObjects(fileProduct.getId());
            Manager.addRequest(fileProduct);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
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

    public Message deleteProduct(String productId, AuthToken authToken) {
        Message message = new Message("deleteProduct");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            Product product = Product.getProductById(productId);
            seller.removeFromProductsToSell(product);
            product.removeSeller(seller.getUsername());
            Product.removeProduct(productId);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message showCategories(AuthToken authToken) {
        Message message = new Message("showCategories");
        message.addToObjects(SubCategory.getAllSubCategories());
        return message;
    }

    public Message viewOffs(AuthToken authToken) {
        Seller seller = (Seller) Main.getAccountWithToken(authToken);
        Message message = new Message("viewOffs");
        message.addToObjects(seller.getSales());
        return message;
    }

    public Message viewOff(String offId, AuthToken authToken) {
        Message message = new Message("viewOff");
        try {
            message.addToObjects(getSaleWithId(offId, authToken));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message editOff(String offId, LocalDateTime startDate, LocalDateTime endDate, String salePercentageStr, ArrayList<String> productIdsToRemove,
                           ArrayList<String> productIdsToAdd, AuthToken authToken) {
        Message message = new Message("editOff");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            Sale sale = getSaleWithId(offId, authToken);
            ArrayList<Product> productsToRemove = Product.getProductsWithIds(productIdsToRemove),
                    productsToAdd = Product.getProductsWithIds(productIdsToAdd), newProducts = getSaleProducts(offId, authToken);
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
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private ArrayList<Product> getSaleProducts(String offId, AuthToken authToken) throws Exception {
        Sale sale = getSaleWithId(offId, authToken);
        return new ArrayList<>(sale.getProducts());
    }

    public Message addOff(LocalDateTime startDate, LocalDateTime endDate, double percentage, ArrayList<String> productIds,
                          AuthToken authToken) {
        Message message = new Message("addOff");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
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
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message viewBalance(AuthToken authToken) {
        Message message = new Message("viewBalance");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getCredit());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private Sale getSaleWithId(String offId, AuthToken authToken) throws SaleUnavailableException {
        Seller seller = (Seller) Main.getAccountWithToken(authToken);
        for (Sale sale : seller.getSales()) {
            if (sale.getId().equals(offId))
                return sale;
        }
        throw new SaleUnavailableException();
    }

    public Message getCredit(AuthToken authToken) {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        Message message = new Message("get credit");
        message.addToObjects(currentSeller.getCredit());
        return message;
    }

    @Override
    protected void setMethods() {
        methods.add("addAdvertisement");
        methods.add("chargeWallet");
        methods.add("withdrawMoneyFromWallet");
        methods.add("viewCompanyInformation");
        methods.add("viewSalesHistory");
        methods.add("getAllProducts");
        methods.add("getCredit");
        methods.add("getSellerProducts");
        methods.add("viewProduct");
        methods.add("viewBuyers");
        methods.add("editProduct");
        methods.add("editFields");
        methods.add("addToProduct");
        methods.add("createProduct");
        methods.add("createFileProduct");
        methods.add("deleteProduct");
        methods.add("showCategories");
        methods.add("viewOffs");
        methods.add("viewOff");
        methods.add("editOff");
        methods.add("addOff");
        methods.add("viewBalance");
        methods.add("getProductsToSell");
        methods.add("getProductCount");
        methods.add("getAccountInfo");
        methods.add("editField");
        methods.add("setProfileImage");
        methods.add("createAuction");
        methods.add("changeMainImage");
        methods.add("logout");
        methods.add("getSeller");
        methods.add("getCredit");
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

    public Message getProductsToSell(AuthToken authToken) {
        Message message = new Message("account info");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getProductsToSell());
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getProductCount(String productId, AuthToken authToken) {
        Message message = new Message("getProductCount");
        try {
            Seller seller = (Seller) Main.getAccountWithToken(authToken);
            message.addToObjects(seller.getProductCount(Product.getProductById(productId)));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    public Message getAccountInfo(AuthToken authToken) {
        Message message = new Message("account info");
        try {
            message.addToObjects(Account.getAccountWithUsername(authToken.getUsername()));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    public Message editField(String field, String context, AuthToken authToken) {
        Seller seller = (Seller) Main.getAccountWithToken(authToken);
        switch (field) {
            case "name":
                seller.changeStateEdited(context, seller.getLastName(), seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(),seller.getDetails());
                break;
            case "lastName":
                seller.changeStateEdited(seller.getName(), context, seller.getEmail(), seller.getPhoneNumber(),
                        seller.getPassword(), seller.getDetails());
                break;
            case "email":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), context, seller.getPhoneNumber(),
                        seller.getPassword(),seller.getDetails());
                break;
            case "phoneNumber":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(), context,
                        seller.getPassword(), seller.getDetails());
                break;
            case "password":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), context, seller.getDetails());
                break;
            case "companyInfo":
                seller.changeStateEdited(seller.getName(), seller.getLastName(), seller.getEmail(),
                        seller.getPhoneNumber(), seller.getPassword(),context);
                break;
            default:
                Message message = new Message("Error");
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

    public Message getSeller(AuthToken authToken) {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        Message message = new Message("seller");
        message.addToObjects(currentSeller);
        return message;
    }

    public Message setProfileImage(String path, AuthToken authToken) {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        currentSeller.setImagePath(path);
        return new Message("set profile image was successful");
    }

    @Override
    public Message changeMainImage(Image image, AuthToken authToken) {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        currentSeller.getGraphicPackage().setMainImage(image);
        return new Message("change main image");
    }

    @Override
    public Message logout(AuthToken authToken) {
        Seller currentSeller = (Seller) Main.getAccountWithToken(authToken);
        Main.removeFromTokenHashMap(authToken, currentSeller);
        return new Message("logout was successful");
    }
}
