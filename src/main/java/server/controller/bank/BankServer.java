package server.controller.bank;

import server.network.server.Server;

public class BankServer extends Server {
    public BankServer() {
        super(9000);
    }

    @Override
    protected void setMethods() {
        methods.add("createAccount");
    }

    public void createAccount(String username, String password) {

    }
}
