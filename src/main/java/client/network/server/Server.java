package client.network.server;

import client.network.Client;
import client.network.Message;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;

import static client.network.AuthToken.generateAuth;

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
        client.writeMessage(new Message("client accepted", generateAuth(client.getAccount().getUsername())));
        while (true) {
            try {
                Message message = client.readMessage();
                if (message.getAuthToken().authenticate())
                    client.writeMessage(callCommand(message.getText(), message));
                else {
                    client.writeMessage(new Message("token is invalid"));
                    clients.remove(client);
                    return;
                }
            } catch (Server.InvalidCommand invalidCommand) {
                invalidCommand.printStackTrace();
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

    public static class InvalidCommand extends Exception {
        public InvalidCommand() {
            super("invalid command");
        }
    }

    protected abstract void setMethods();
}
