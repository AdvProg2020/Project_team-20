package server.network;

import server.model.account.Account;
import server.controller.Main;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

public class AuthToken {
    private String username;
    private int key;
    private LocalDateTime timeCreated;

    private AuthToken(String username, int key) {
        this.username = username;
        this.key = key;
        timeCreated = LocalDateTime.now();
    }


    public static AuthToken generateAuth(String username) {
        StringBuilder toBeHashed = new StringBuilder();
        int key = toBeHashed.append(generateRandomInt(username.length())).append(username).toString().hashCode();
        return new AuthToken(username, key);
    }


    //is valid
    public boolean authenticate() {
        HashMap<AuthToken, Account> hashMap = Main.getAuthTokenAccountHashMap();
        //TODO edit this field
        return hashMap.containsKey(this);
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

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

