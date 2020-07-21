package client.network;

import client.model.account.Account;
import com.gilecode.yagson.YaGson;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8000;

    //private static final int SEND_SIZE = ;
    private static final String END_MESSAGE = "END_MESSAGE";


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
            this.socket = new Socket(HOST, port);
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
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


    public void writeMessage(Message message) {
        try {
            message.setAuth(authToken);
            dataOutputStream.writeUTF(message.toYaGson());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            writeMessage(new Message("buy"));
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File readFile(String name, String fileType) {
        File deliveredFile = new File("src/main/resources/files/"
                + name + "." + fileType);
        int bytesRead;
        int current = 0;
        try {
            byte[] byteArray = new byte[999999999];
            bytesRead = dataInputStream.read(byteArray, 0, byteArray.length);
            current = bytesRead;
            do {
                bytesRead = dataInputStream.read(byteArray, current, (byteArray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);
            FileOutputStream fileOutputStream = new FileOutputStream(deliveredFile, false);
            dataInputStream.readByte();
            fileOutputStream.write(byteArray);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveredFile;
    }

    public void writeFile(File file) {
        try {
            byte[] myByteArray = IOUtils.readFully(file);
            dataOutputStream.write(myByteArray, 0, myByteArray.length);
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

    @Override
    public String toString() {
        return "Client{" +
                "account=" + account +
                ", authToken=" + authToken +
                '}';
    }
}
