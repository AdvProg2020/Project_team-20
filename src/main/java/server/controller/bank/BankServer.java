package server.controller.bank;

import client.model.account.Account;
import server.model.bank.BankAccount;
import server.model.bank.BankReceipt;
import server.model.bank.BankReceiptType;
import server.network.AuthToken;
import server.network.Client;
import server.network.Message;
import server.network.server.Server;
import sun.tools.jstat.Token;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BankServer {
    ArrayList<BankAccount> bankAccounts;
    private static int bankCount=0;
    protected ServerSocket serverSocket;
    protected ArrayList<Client> clients;
    protected HashMap<AuthToken, BankAccount> loggedInAccounts;
    protected ArrayList<String> methods;

    public BankServer() {
        bankAccounts = new ArrayList<>();
        try {
            this.serverSocket = new ServerSocket(9000);
            this.clients = new ArrayList<>();
            this.methods = new ArrayList<>();
            this.loggedInAccounts = new HashMap<>();
            new Thread(this::handleClients).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handleClients() {
        while (true) {
            try {
                Client client = new Client(serverSocket.accept());
                new Thread(() -> handleClient(client)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void handleClient(Client client) {
        clients.add(client);
        client.writeMessage(new Message("client accepted"));
        while (true) {
            Message message = client.readMessage();
            try {
                client.writeMessage(callCommand(message.getText(), message));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Message callCommand(String command, Message message) throws Server.InvalidCommand {
        for (String method : methods) {
            if (method.equals(command)) {
                try {
                    return (Message) (Objects.requireNonNull(getMethodByName(method))).
                            invoke(this, message.getObjects().toArray());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Server.InvalidCommand();
    }

    private Method getMethodByName(String name) {
        Class clazz = this.getClass();
        for (Method declaredMethod : clazz.getDeclaredMethods())
            if (declaredMethod.getName().equals(name)) return declaredMethod;
        return null;
    }

    protected void setMethods() {
        methods.add("createAccount");
        methods.add("getToken");
        methods.add("createReceipt");
        methods.add("getTransactions");
        methods.add("pay");
        methods.add("getBalance");
    }

    public Message pay(AuthToken authToken, String receiptID) {
        Message message;
        BankAccount bankAccount = loggedInAccounts.get(authToken);
        if (!isTokenExists(authToken)) {
            message = new Message("Error");
            message.addToObjects("token is invalid");
            return message;
        }
        if (!validTokenTime(authToken)) {
            message = new Message("Error");
            message.addToObjects("token expired");
            return message;
        }
        if (!bankAccount.hasBankReceipt(receiptID)) {
            message = new Message("Error");
            message.addToObjects("invalid receipt id");
            return message;
        }
        return validPayReceipt(bankAccount, receiptID);
    }

    public Message getBalance(AuthToken authToken) {
        Message message;
        BankAccount bankAccount = loggedInAccounts.get(authToken);
        if (!isTokenExists(authToken)) {
            message = new Message("Error");
            message.addToObjects("token is invalid");
            return message;
        }
        if (!validTokenTime(authToken)) {
            message = new Message("Error");
            message.addToObjects("token expired");
            return message;
        }
        message = new Message("Confirmation");
        message.addToObjects(bankAccount.getMoney());
        return message;
    }

    private Message validPayReceipt(BankAccount bankAccount, String receiptID) {
        Message message;
        String result = payReceipt(bankAccount, receiptID);
        if (result.equals("wasPaid")) {
            message = new Message("Error");
            message.addToObjects("receipt is paid before");
            return message;
        }
        if (result.equals("notEnoughMoney")) {
            message = new Message("Error");
            message.addToObjects("source account does not have enough money");
            return message;
        }
        if (result.equals("invalidAccount")) {
            message = new Message("Error");
            message.addToObjects("invalid account id");
            return message;
        }
        message = new Message("Confirmation");
        return message;
    }

    public String payReceipt(BankAccount bankAccount, String receiptId) {
        BankReceipt bankReceipt = bankAccount.getBankReceiptByID(receiptId);
        if (bankReceipt.wasPaid())
            return "wasPaid";
        BankAccount sourceBankAccount = getBankAccountByID(bankReceipt.getSourceID());
        BankAccount destBankAccount = getBankAccountByID(bankReceipt.getDestID());
        if (sourceBankAccount==null || destBankAccount==null) {
            return "invalidAccount";
        }
        if (sourceBankAccount.getMoney()<bankReceipt.getMoney())
            return "notEnoughMoney";
        sourceBankAccount.decreaseMoney(bankReceipt.getMoney());
        destBankAccount.increaseMoney(bankAccount.getMoney());
        bankReceipt.setReceiptState(true);
        return "Confirmation";
    }

    public Message getToken(String username, String password) {
        Message message;
        BankAccount bankAccount = loginAccount(username, password);
        if (bankAccount==null) {
            message = new Message("Error");
            message.addToObjects("username is not available");
            return message;
        }
        AuthToken authToken = AuthToken.generateAuth(bankAccount.getUsername());
        loggedInAccounts.put(authToken, bankAccount);
        message = new Message("Confirmation");
        message.addToObjects(authToken);
        return message;
    }

    private BankAccount loginAccount(String username, String password) {
        BankAccount bankAccount = getBankAccountByUsername(username);
        if (bankAccount==null)
            return null;
        if (!(password.equals(bankAccount.getPassword())))
            return null;
        return bankAccount;
    }

    public Message getTransactions(AuthToken authToken, BankReceiptType bankReceiptType) {
        Message message;
        BankAccount account = loggedInAccounts.get(authToken);
        if (!isTokenExists(authToken)) {
            message = new Message("Error");
            message.addToObjects("token is invalid");
            return message;
        }
        if (!validTokenTime(authToken)) {
            message = new Message("Error");
            message.addToObjects("token expired");
            return message;
        }
        switch (bankReceiptType) {
            case MOVE:
                return getMoves(account);
            case DEPOSIT:
                return getDeposits(account);
            default:
                return getWithdraws(account);
        }
    }

    public Message getDeposits(BankAccount bankAccount) {
        Message message;
        ArrayList<BankReceipt> bankReceipts = bankAccount.getDeposits();
        message = new Message("Confirmation");
        message.addToObjects(bankReceipts);
        return message;
    }

    public Message getWithdraws(BankAccount bankAccount) {
        Message message;
        ArrayList<BankReceipt> bankReceipts = bankAccount.getWithdraws();
        message = new Message("Confirmation");
        message.addToObjects(bankReceipts);
        return message;
    }

    public Message getMoves(BankAccount bankAccount) {
        Message message;
        ArrayList<BankReceipt> bankReceipts = bankAccount.getMoves();
        message = new Message("Confirmation");
        message.addToObjects(bankReceipts);
        return message;
    }

    public Message createAccount(String firstName, String lastName, String username, String password, String repeatPassword) {
        Message message;
        if (!password.equals(repeatPassword)) {
            message = new Message("Error");
            message.addToObjects("passwords do not match");
            return message;
        } else if (getBankAccountByUsername(username)!=null) {
            message = new Message("Error");
            message.addToObjects("username is not available");
            return message;
        } else {
            BankAccount account = new BankAccount(firstName, lastName, username, password);
            bankAccounts.add(account);
            message = new Message("Confirmation");
            LocalDateTime now = LocalDateTime.now();
            int random = ThreadLocalRandom.current().nextInt(10,  100);
            String accountNumber = now.getYear() + now.getMonth().toString() + now.getDayOfMonth() + random + bankCount;
            account.setAccountNumber(accountNumber);
            message.addToObjects(accountNumber);
            bankCount++;
            return message;
        }
    }

    public Message createReceipt(AuthToken authToken, BankReceiptType receiptType, double money, String sourceID, String destID, String description) {
        Message message;
        if (receiptType!=BankReceiptType.DEPOSIT && receiptType!=BankReceiptType.MOVE
        && receiptType!=BankReceiptType.WITHDRAW) {
            message = new Message("Error");
            message.addToObjects("invalid receipt type");
            return message;
        }
        if (money<=0) {
            message = new Message("Error");
            message.addToObjects("invalid money");
            return message;
        }
        message = checkValidAccounts(destID, sourceID);
        if (message!=null)
            return message;
        if (!description.matches("\\w*")) {
            message = new Message("Error");
            message.addToObjects("your input contains invalid characters");
            return message;
        }
        if (!isTokenExists(authToken)) {
            message = new Message("Error");
            message.addToObjects("token is invalid");
            return message;
        }
        BankAccount account = loggedInAccounts.get(authToken);
        if (!validAccountMakeReceipt(account, sourceID, destID, receiptType)) {
            message = new Message("Error");
            message.addToObjects("token is invalid");
            return message;
        }
        if (!validTokenTime(authToken)) {
            message = new Message("Error");
            message.addToObjects("token expired");
            return message;
        }
        BankReceipt bankReceipt = new BankReceipt(receiptType, money, sourceID, destID, description);
        message = new Message("Confirmation");
        message.addToObjects(bankReceipt.getID());
        return message;
    }

    private boolean isTokenExists(AuthToken authToken) {
        for (AuthToken authToken1:loggedInAccounts.keySet()) {
            if (authToken.equals(authToken1))
                return true;
        }
        return false;
    }

    private boolean validTokenTime(AuthToken authToken) {
        LocalDateTime time = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(authToken.getTimeCreated(), time);
        return hours <= 1;
    }

    private boolean validAccountMakeReceipt(BankAccount account, String sourceID, String destID, BankReceiptType receiptType) {
        if (receiptType==BankReceiptType.MOVE || receiptType==BankReceiptType.WITHDRAW) {
            return sourceID.equals(account.getAccountNumber());
        } else {
            return destID.equals(account.getAccountNumber());
        }
    }

    private Message checkValidAccounts(String destID, String sourceID) {
        Message message;
        BankAccount sourceBankAccount = getBankAccountByID(sourceID);
        BankAccount destBankAccount = getBankAccountByID(destID);
        if (getBankAccountByID(sourceID)==null) {
            message = new Message("Error");
            message.addToObjects("source account id is invalid");
            return message;
        }

        else if (getBankAccountByID(destID)==null) {
            message = new Message("Error");
            message.addToObjects("dest account id is invalid");
            return message;
        }

        else if (destID.equals(sourceID)) {
            message = new Message("Error");
            message.addToObjects("equal source and dest account");
            return message;
        }

        return null;
    }

    private BankAccount getBankAccountByID(String ID) {
        for (BankAccount bankAccount:bankAccounts) {
            if (bankAccount.getAccountNumber().equals(ID))
                return bankAccount;
        }
        return null;
    }

    private BankAccount getBankAccountByUsername(String username) {
        for (BankAccount bankAccount:bankAccounts) {
            if (bankAccount.getUsername().equals(username))
                return bankAccount;
        }
        return null;
    }


}
