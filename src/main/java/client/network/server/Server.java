package client.network.server;

import server.network.Client;
import server.network.Message;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;

import static server.network.AuthToken.generateAuth;

public abstract class Server {


    protected ServerSocket serverSocket;
    protected ArrayList<server.network.Client> clients;
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
                server.network.Client client = new server.network.Client(serverSocket.accept());
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
            } catch (server.network.server.Server.InvalidCommand invalidCommand) {
                invalidCommand.printStackTrace();
            }
        }
    }

    public Message callCommand(String command, Message message) throws server.network.server.Server.InvalidCommand {
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
        throw new server.network.server.Server.InvalidCommand();
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
