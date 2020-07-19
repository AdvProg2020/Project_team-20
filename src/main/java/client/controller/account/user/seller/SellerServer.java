package client.controller.account.user.seller;

import client.network.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class SellerServer {
    private final int port;
    private ServerSocket serverSocket;

    public SellerServer() {
        this.port = generatePort();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // for p2p:
    public void run() {
        try {
            new Thread(this::handleClients).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClients() {
        while (true) {
            try {
                Client client = new Client(serverSocket.accept());
                new Thread(() -> handleClient(client)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Client client) {
        
    }

    private int generatePort() {
        Random rand = new Random();
        return rand.nextInt(20000) + 1000;
    }
}
