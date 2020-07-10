package client.network;

import com.gilecode.yagson.YaGson;
import server.network.AuthToken;

import java.util.ArrayList;

public class Message {
    private String text;
    private ArrayList<Object> objects = new ArrayList<>();
    private server.network.AuthToken authToken = null;

    public Message(String text, server.network.AuthToken authToken) {
        this.text = text;
        this.authToken = authToken;
    }

    public Message(String text) {
        this.text = text;
    }

    public server.network.AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuth(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void addToObjects(Object carry) {
        this.objects.add(carry);
    }

    public String getText() {
        return text;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public static server.network.Message getFailedMessage() {
        return new server.network.Message("failed!");
    }

    public static server.network.Message getDoneMessage() {
        return new server.network.Message("Done!");
    }

    public String toYaGson() {
        return (new YaGson()).toJson(this);
    }
}
