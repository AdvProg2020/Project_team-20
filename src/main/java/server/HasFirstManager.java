package server;

import client.model.account.Manager;
import client.network.Message;
import server.network.server.Server;

public class HasFirstManager extends Server {

    public HasFirstManager() {
        super(777);
        setMethods();
        System.out.println("has first manager run");
    }

    @Override
    protected void setMethods() {
        methods.add("sendHasFirstManager");
    }

    public Message sendHasFirstManager() {
        Message message = new Message("has first manager");
        message.addToObjects(Manager.isHasFirstManger());
        return message;
    }
}
