package server.network.server;

import server.network.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public abstract class Server {
    protected ServerSocket serverSocket;
    protected ArrayList<Client> clients;

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
