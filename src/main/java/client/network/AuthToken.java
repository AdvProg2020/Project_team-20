package client.network;

public class AuthToken {
    private String username;
    private int key;

    private AuthToken(String username, int key) {
        this.username = username;
        this.key = key;
    }


   /* public static AuthToken generateAuth(String username){

    }

    //is valid
    public boolean authenticate() {

    }

    */


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

