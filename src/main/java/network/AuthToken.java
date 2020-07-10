package network;

import model.account.Account;

public class AuthToken {
    private String username;
    private int key;

    private AuthToken(String username, int key) {
        this.username = username;
        this.key = key;
    }


    public static AuthToken generateAuth(String username){
        int i=0;
        for (Account acc : Account.getAllAccounts()) {
            if(acc.getUsername().equals(username))break;
            i++;
        }
        StringBuilder toBeHashed = new StringBuilder();
        int key = toBeHashed.append(i).append(username).toString().hashCode();
        System.out.println("key = " + key);
        return new AuthToken(username, key);
    }

    //is valid
    public boolean authenticate() {
        int i = 0;
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(this.getUsername())) break;
            i++;
        }
        return (i + this.getUsername()).hashCode() == this.getKey();
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

