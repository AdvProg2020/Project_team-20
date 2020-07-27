package client.network;

import client.controller.account.user.seller.SellerNetwork;
import client.model.account.Account;
import com.gilecode.yagson.YaGson;
//import com.oracle.tools.packager.IOUtils;
import sourceCode.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String HOST = "192.168.43.183";
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

    public Client(InetAddress inetAddress, int port) {
        try {
            this.socket = new Socket(inetAddress, port);
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(SellerNetwork sellerNetwork) {
        try {
            this.socket = new Socket(sellerNetwork.getHostAddress(), sellerNetwork.getPort());
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message readMessage() {
        try {

            return new YaGson().fromJson(dataInputStream.readUTF(), Message.class);

              /*
            byte[] byteArray = new byte[999999999];
            int bytesRead = dataInputStream.read(byteArray, 0, byteArray.length);
            ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
            ObjectInputStream is = new ObjectInputStream(in);
            return (Message) is.readObject();

             */
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message("read failed");
    }


    public void writeMessage(Message message) {
        try {
            /*
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(message);
            oos.flush();
            byte [] data = bos.toByteArray();
            dataOutputStream.write(data, 0, data.length);
            dataOutputStream.flush();

             */

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
        return readFile(deliveredFile);
    }

    public File readFileForBuyer(String name, String fileType) {
        File deliveredFile = new File("src/main/resources/aboutBuyer/BuyerFiles/"
                + name + "." + fileType);
        return readFile(deliveredFile);
    }

    private File readFile(File deliveredFile) {
        try {
            byte[] byteArray = new byte[999999999];
            /*
            int bytesRead;
            int current = 0;
            bytesRead = dataInputStream.read(byteArray, 0, byteArray.length);
            current = bytesRead;
            do {
                bytesRead = dataInputStream.read(byteArray, current, (byteArray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);
            dataInputStream.readByte();
             */
            FileOutputStream fileOutputStream = new FileOutputStream(deliveredFile, false);
            int bytesRead = dataInputStream.read(byteArray, 0, byteArray.length);
            fileOutputStream.write(byteArray, 0, bytesRead);
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

    public void receiveImage(String path) {
        readFile(new File(path));
    }
}
