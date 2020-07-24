package server.network.server;

import client.controller.account.user.seller.SellerNetwork;
import client.network.Client;
import client.network.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

public class DNS {
    private  static DNS dns = null;
    private final HashMap<String, SellerNetwork> sellerPorts = new HashMap<>();
    private ServerSocket serverSocket;

    public static DNS getInstance() {
        if (dns == null)
            dns = new DNS();
        return dns;
    }

    private DNS() {
        try {
            serverSocket = new ServerSocket(1673);
            System.out.println("dns server is run");
            new Thread(this::handleConnection).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SellerNetwork getSellerNetwork(String sellerUsername) throws SellerIsNotOnline {
        if (sellerPorts.get(sellerUsername) == null)
            throw new SellerIsNotOnline();
        return sellerPorts.get(sellerUsername);
    }

    private void handleConnection() {
        while (true) {
            try {
                Client client = new Client(serverSocket.accept());
                client.writeMessage(new Message("welcome to DNS"));
                Message message = client.readMessage();
                String sellerUsername = (String) message.getObjects().get(0);
                int port = (int) message.getObjects().get(1);
                InetAddress inetAddress = (InetAddress) message.getObjects().get(2);
                sellerPorts.put(sellerUsername, new SellerNetwork(inetAddress, port));
                client.writeMessage(new Message("added"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SellerIsNotOnline extends Exception {
        public SellerIsNotOnline() {
            super("seller is not online for receive files");
        }
    }
}
