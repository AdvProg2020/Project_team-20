package server.network;

import java.util.ArrayList;
import java.util.HashMap;

public class Message {
    private String text;
    private HashMap<String, Object> objects = new HashMap<>();
    private AuthToken authToken = null;

    public Message(String text, AuthToken authToken) {
        this.text = text;
        this.authToken = authToken;
    }

    public Message(String text) {
        this.text = text;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuth(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void addToObjects(String key, Object carry) {
        this.objects.put(key, carry);
    }

    public String getText() {
        return text;
    }

    public HashMap<String,Object> getObjects() {
        return objects;
    }

    public Object getObjectWithKey(String key) {
        return objects.get(key);
    }

    public static Message getFailedMessage() {
        return new Message("failed!");
    }

    public static Message getDoneMessage() {
        return new Message("Done!");
    }

}
