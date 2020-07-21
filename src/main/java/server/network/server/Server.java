package server.network.server;

import client.model.account.Account;
import client.model.account.GeneralAccount;
import client.network.AuthToken;
import client.network.Client;
import client.network.Message;
import server.controller.Main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Server {


    protected ServerSocket serverSocket;
    protected ArrayList<Client> clients;
    protected ArrayList<String> methods;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.clients = new ArrayList<>();
            this.methods = new ArrayList<>();
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
            Main.storeData();
            Message message = client.readMessage();
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
                        Main.addToClientHashMap(client, (Account) generalAccount);
                    }
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

    public Message callCommand(String command, Message message) throws InvalidCommand {
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
        throw new InvalidCommand();
    }

    protected  Client getClientWithToken(AuthToken authToken) throws InvalidToken {
        for (Client client : clients) {
            if (client.getAuthToken() != null && client.getAuthToken().getKey() == authToken.getKey())
                return client;
        }
        throw new InvalidToken();
    }


    private Method getMethodByName(String name) {
        Class clazz = this.getClass();
        for (Method declaredMethod : clazz.getDeclaredMethods())
            if (declaredMethod.getName().equals(name)) return declaredMethod;
        return null;
    }

    public static class InvalidCommand extends Exception {
        public InvalidCommand() {
            super("invalid command");
        }
    }

    public static class InvalidToken extends Exception {
        public InvalidToken() {
            super("token is invalid");
        }
    }

    protected abstract void setMethods();
}
