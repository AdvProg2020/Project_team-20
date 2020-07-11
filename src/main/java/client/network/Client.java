package client.network;

import client.model.account.Account;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "127.0.0.1";

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private Account account;
    private AuthToken authToken;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(int port) {
        try {
            System.out.println("in client constructor");
            this.socket = new Socket(HOST, port);
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("in cacth");
            e.printStackTrace();
        }
    }

    public Message readMessage() {
        try {
            return new YaGson().fromJson(dataInputStream.readUTF(), Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message("read failed");
    }


    public void writeMessage(Message massage) {
        try {
            massage.setAuth(authToken);
            System.out.println(authToken);
            if (authToken != null)
            System.out.println(authToken.getKey());
            dataOutputStream.writeUTF(massage.toYaGson());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
