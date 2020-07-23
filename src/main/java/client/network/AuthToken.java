package client.network;

import client.model.account.GeneralAccount;
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
        this.timeCreated = LocalDateTime.now();
    }

    private AuthToken(int key) {
        this.key = key;
        this.timeCreated = LocalDateTime.now();
    }

    public boolean validTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("TIME: " + timeCreated);
        return (now.getMinute() - timeCreated.getMinute()) <= 58;
    }

    public static AuthToken generateAuth(String username) {
        StringBuilder toBeHashed = new StringBuilder();
        int key = toBeHashed.append(generateRandomInt(username.length())).append(username).toString().hashCode();
        return new AuthToken(username, key);
    }

    public static AuthToken generateAuth() {
        int key = generateRandomInt(20);
        return new AuthToken(key);
    }


    //is valid
    public boolean authenticate() {
        HashMap<String, GeneralAccount> hashMap = Main.getAuthTokenAccountHashMap();
        //TODO edit this field

        return hashMap.containsKey(String.valueOf(key));
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

