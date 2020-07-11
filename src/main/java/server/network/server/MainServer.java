package server.network.server;

import client.network.Client;

public class MainServer extends Server{
    private static final int DEFAULT_PORT = 8000;

    public MainServer() {
        super(DEFAULT_PORT);
    }

    @Override
    protected void handleClient(Client client) {
        while (true) {

        }
    }

    @Override
    protected void setMethods() {

    }
}

