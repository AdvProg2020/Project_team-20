package model.account;

import com.gilecode.yagson.YaGson;
import model.Requestable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.util.Set;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }


    public static void storeRequests() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutManager/requests.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Requestable request : requests) {
                fileWriter.write(yaGson.toJson(request) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storeRequestsWithId() {
        YaGson yaGson = new YaGson();
        Requestable request;
        int id;
        File file = new File("src/main/resources/aboutManager/requestsWithId.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            Set<Map.Entry<Integer, Requestable>> optionalSet = requestWithIds.entrySet();
            for (Map.Entry<Integer, Requestable> mentry : optionalSet) {
                id = mentry.getKey();
                request = mentry.getValue();
                fileWriter.write(yaGson.toJson(id) + "\n");
                fileWriter.write(yaGson.toJson(request) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/Manager.txt");
            Scanner fileScanner = new Scanner(fis);
            while (fileScanner.hasNextLine()) {
                String managerStr = fileScanner.nextLine();
                Manager manager = yaGson.fromJson(managerStr, Manager.class);
                Account.addAccount(manager);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
