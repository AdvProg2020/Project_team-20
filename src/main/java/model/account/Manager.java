package model.account;

import com.gilecode.yagson.YaGson;
import model.Requestable;
import model.product.*;

import java.io.*;
import java.util.*;

public class Manager extends Account {
    private boolean firstManager;
    public static int numberOfRequests;
    private static boolean hasFirstManger = false;
    private static ArrayList<Requestable> requests = new ArrayList<>();
    private static HashMap<Integer, Requestable> requestWithIds = new HashMap<>();

    public Manager(String name, String lastName, String email, String phoneNumber, String username, String password,
                   double credit, boolean firstManager) {
        super(name, lastName, email, phoneNumber, username, password, credit, AccountType.MANAGER);
        this.firstManager = firstManager;
    }

    public boolean isFirstManager() {
        return firstManager;
    }

    public void setFirstManager(boolean firstManager) {
        this.firstManager = firstManager;
    }

    //TODO check this rustin ;)
    public void deleteAccount(String username) throws Exception {
        Account account = getAccountWithUsername(username);
        try {
            Manager manager = (Manager) account;
            if (manager.isFirstManager())
                return;
            Account.deleteAccount(account);
        } catch (Exception e) {
            Account.deleteAccount(account);
        }
    }

    public static void addRequest(Requestable request) {
        numberOfRequests++;
        requestWithIds.put(numberOfRequests, request);
        requests.add(request);
    }

    public static void deleteRequest(Requestable request, int requestId) {
        requests.remove(request);
        requestWithIds.remove(requestId, request);
    }

    public static ArrayList<Requestable> getRequests() {
        return requests;
    }

    public static Requestable findRequestWithId(int requestId) throws Exception {
        Requestable requestable = requestWithIds.get(requestId);
        if (requestable != null)
            return requestWithIds.get(requestId);
        throw new requestNotFoundException();
    }

    public static class requestNotFoundException extends Exception {
        public requestNotFoundException() {
            super("request doesn't exist!");
        }
    }

    public static boolean isHasFirstManger() {
        return hasFirstManger;
    }

    public static void setHasFirstManger(boolean hasFirstManger) {
        Manager.hasFirstManger = hasFirstManger;
    }

    public static HashMap<Integer, Requestable> getRequestWithIds() {
        return requestWithIds;
    }

    public static void store() {
        storeHasFirstManger();
        storeNumberOfRequests();
        storeRequestsWithId();
        storeAccount();
    }

    public static void load() {
        loadHasFirstManger();
        loadNumberOfRequests();
        loadRequestsWithId();
        loadAccount();
    }

    public static void storeAccount() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutManager/managers.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Account account : allAccounts) {
                if (account.getAccountType().equals(AccountType.MANAGER))
                    fileWriter.write(yaGson.toJson(account) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadAccount() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/managers.txt");
            Scanner fileScanner = new Scanner(inputStream);
            String managerStr;
            while (fileScanner.hasNextLine()) {
                managerStr = fileScanner.nextLine();
                allAccounts.add(yaGson.fromJson(managerStr, Manager.class));
            }
            fileScanner.close();
        } catch (IOException e) {
        }
    }

    public static void storeNumberOfRequests() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutManager/numberOfRequests.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(numberOfRequests) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadNumberOfRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/numberOfRequests.txt");
            Scanner fileScanner = new Scanner(inputStream);
            numberOfRequests = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException e) {
        }
    }

    public static void storeHasFirstManger() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutManager/hasFirstManger.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(hasFirstManger) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadHasFirstManger() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/hasFirstManger.txt");
            Scanner fileScanner = new Scanner(inputStream);
            hasFirstManger = yaGson.fromJson(fileScanner.nextLine(), boolean.class);
            fileScanner.close();
        } catch (IOException e) {
        }
    }

    public static void storeRequestsWithId() {
        YaGson yaGson = new YaGson();
        Requestable request;
        int id;
        Set<Map.Entry<Integer, Requestable>> optionalSet = requestWithIds.entrySet();
        for (Map.Entry<Integer, Requestable> mentry : optionalSet) {
            id = mentry.getKey();
            request = mentry.getValue();
            switch (request.getRequestType()) {
                case Sale:
                    storeSaleRequests(yaGson, (Sale) request, id);
                    break;
                case Buyer:
                    storeBuyerRequests(yaGson, (Buyer) request, id);
                    break;
                case Score:
                    storeScoreRequests(yaGson, (Score) request, id);
                    break;
                case Seller:
                    storeSellerRequests(yaGson, (Seller) request, id);
                    break;
                case Comment:
                    storeCommentRequests(yaGson, (Comment) request, id);
                    break;
                case Product:
                    storeProductRequests(yaGson, (Product) request, id);
                    break;
                case AddSellerRequest:
                    storeAddSellerToProductRequests(yaGson, (AddSellerRequest) request, id);
            }
        }
    }

    public static void loadRequestsWithId() {
        loadAddSellerToProductRequests();
        loadProductRequests();
        loadCommentRequests();
        loadSellerRequests();
        loadScoreRequests();
        loadBuyerRequests();
        loadSaleRequests();
    }

    public static void storeAddSellerToProductRequests(YaGson yaGson, AddSellerRequest addSellerRequest, int id) {
        File file = new File("src/main/resources/aboutManager/requests/AddSellerToProduct.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(addSellerRequest) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadAddSellerToProductRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/AddSellerToProduct.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                AddSellerRequest addSellerRequest = yaGson.fromJson(fileScanner.nextLine(), AddSellerRequest.class);
                requestWithIds.put(id, addSellerRequest);
                requests.add(addSellerRequest);
            }
        } catch (Exception e) {
        }
    }

    public static void storeProductRequests(YaGson yaGson, Product product, int id) {
        File file = new File("src/main/resources/aboutManager/requests/Product.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(product) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadProductRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/Product.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Product product = yaGson.fromJson(fileScanner.nextLine(), Product.class);
                requestWithIds.put(id, product);
                requests.add(product);
            }
        } catch (Exception e) {
        }
    }

    public static void storeCommentRequests(YaGson yaGson, Comment comment, int id) {
        File file = new File("src/main/resources/aboutManager/requests/Comment.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(comment) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadCommentRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/Comment.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Comment comment = yaGson.fromJson(fileScanner.nextLine(), Comment.class);
                requestWithIds.put(id, comment);
                requests.add(comment);
            }
        } catch (Exception e) {
        }
    }

    public static void storeSellerRequests(YaGson yaGson, Seller seller, int id) {
        File file = new File("src/main/resources/aboutManager/requests/Seller.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(seller) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadSellerRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/Seller.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Seller seller = yaGson.fromJson(fileScanner.nextLine(), Seller.class);
                requestWithIds.put(id, seller);
                requests.add(seller);
            }
        } catch (Exception e) {
        }
    }

    public static void storeScoreRequests(YaGson yaGson, Score score, int id) {
        File file = new File("src/main/resources/aboutManager/requests/Score.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(score) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadScoreRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/Score.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Score score = yaGson.fromJson(fileScanner.nextLine(), Score.class);
                requestWithIds.put(id, score);
                requests.add(score);
            }
        } catch (Exception e) {
        }
    }

    public static void storeBuyerRequests(YaGson yaGson, Buyer buyer, int id) {
        File file = new File("src/main/resources/aboutManager/requests/Buyer.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(buyer) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadBuyerRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/Buyer.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Buyer buyer = yaGson.fromJson(fileScanner.nextLine(), Buyer.class);
                requestWithIds.put(id, buyer);
                requests.add(buyer);
            }
        } catch (Exception e) {
        }
    }

    public static void storeSaleRequests(YaGson yaGson, Sale sale, int id) {
        File file = new File("src/main/resources/aboutManager/requests/sale.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(id) + "\n");
            fileWriter.write(yaGson.toJson(sale) + "\n");
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public static void loadSaleRequests() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutManager/requests/sale.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                int id = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
                Sale sale = yaGson.fromJson(fileScanner.nextLine(), Sale.class);
                requestWithIds.put(id, sale);
                requests.add(sale);
            }
        } catch (Exception e) {
        }
    }
}
