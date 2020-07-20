package client.controller.account.user.seller;

import client.network.Client;
import client.network.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SellerServer {
    private final int port;
    private ServerSocket serverSocket;
    private Socket socket;

    public SellerServer(String username) {
        this.port = generatePort();
        try {
            this.serverSocket = new ServerSocket(port);
            Client client = new Client(1673);
            Message message = new Message("connect to DNS");
            message.addToObjects(username);
            message.addToObjects(port);
            client.writeMessage(message);
            client.readMessage();
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
