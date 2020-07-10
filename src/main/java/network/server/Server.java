package network.server;

import network.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public abstract class Server {
    private static final int DEFAULT_PORT = 8000;

    private ServerSocket serverSocket;
    private ArrayList<Client> clients;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
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
        while (true){
            //TODO commands
        }
    }
}
