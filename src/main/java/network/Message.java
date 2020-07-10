package network;

import java.util.ArrayList;

public class Message {
    private String text;
    private ArrayList<Object> objects = new ArrayList<>();
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

    public void addToObjects(Object carry) {
        this.objects.add(carry);
    }

    public String getText() {
        return text;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public static Message getFailedMessage() {
        return new Message("failed!");
    }

    public static Message getDoneMessage() {
        return new Message("Done!");
    }

}