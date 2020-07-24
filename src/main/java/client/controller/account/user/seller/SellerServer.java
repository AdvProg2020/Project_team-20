package client.controller.account.user.seller;

import client.model.product.FileProduct;
import client.model.product.Product;
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
            client.readMessage();
            Message message = new Message("connect to DNS");
            message.addToObjects(username);
            message.addToObjects(port);
            message.addToObjects(serverSocket.getInetAddress());
            client.writeMessage(message);
            client.readMessage();
            client.disconnect();
            System.out.println("connected to dns");
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
        System.out.println("in handle client");
        client.readMessage();
        client.writeMessage(new Message("in receive file from seller"));
        Message message = client.readMessage();
        String productId = (String) message.getObjects().get(0);
        for (Product sellerProduct : SellerController.getInstance().getSellerProducts()) {
            if (sellerProduct.getId().equals(productId)) {
                if (sellerProduct instanceof FileProduct) {
                    String path = "src/main/resources/aboutSeller/sellerFiles/" +
                            sellerProduct.getId() + "." + ((FileProduct) sellerProduct).getFileType();
                    client.writeFile(new File(path));
                    return;
                }
            }
        }
    }

    private int generatePort() {
        Random rand = new Random();
        return rand.nextInt(20000) + 1000;
    }

    private static class ProductIsNotFile extends Exception {
        public ProductIsNotFile() {
            super("product is not file");
        }
    }
}
