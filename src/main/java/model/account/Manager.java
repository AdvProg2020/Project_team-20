package model.account;

import model.Requestable;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager extends Account {
    private boolean firstManager;
    public static int numberOfRequests;
    private static ArrayList<Requestable> requests = new ArrayList<>();
    private static HashMap<Integer,Requestable> requestWithIds = new HashMap<>();

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
        requestWithIds.put(numberOfRequests , request);
        requests.add(request);
    }

    public static void deleteRequest(Requestable request, int requestId) {
        requests.remove(request);
        requestWithIds.remove(requestId, request);
    }

    public static ArrayList<Requestable> getRequests() {
        return requests;
    }

    public static Requestable findRequestWithId(String requestId){
        int ID=Integer.parseInt(requestId);
        return requestWithIds.get(ID);
    }

}
