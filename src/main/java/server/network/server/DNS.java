package server.network.server;

import client.network.Client;
import client.network.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class DNS {
    private final HashMap<String, Integer> sellerPorts = new HashMap<>();
    private ServerSocket serverSocket;

    public DNS() {
        try {
            serverSocket = new ServerSocket(1673);
            System.out.println("dns server is run");
            new Thread(this::handleConnection).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSellerPort(String sellerUsername) {
        if (sellerPorts.get(sellerUsername) == null)
            return -1;
        return sellerPorts.get(sellerUsername);
    }

    private void handleConnection() {
        while (true) {
            try {
                Client client = new Client(serverSocket.accept());
                Message message = client.readMessage();
                String sellerUsername = (String) message.getObjects().get(0);
                int port = (int) message.getObjects().get(1);
                sellerPorts.put(sellerUsername, port);
                client.writeMessage(new Message("added"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
