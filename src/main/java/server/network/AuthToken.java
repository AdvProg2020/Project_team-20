package server.network;

import java.util.Random;

public class AuthToken {
    private String username;
    private int key;

    private AuthToken(String username, int key) {
        this.username = username;
        this.key = key;
    }


    public static AuthToken generateAuth(String username) {
        StringBuilder toBeHashed = new StringBuilder();
        int key = toBeHashed.append(generateRandomInt(username.length())).append(username).toString().hashCode();
        return new AuthToken(username, key);
    }


    //is valid
    public boolean authenticate() {
        //TODO edit this field
        return true;
    }

    private static int generateRandomInt(int upperbound) {
        Random rand = new Random();
        return rand.nextInt(upperbound);
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

