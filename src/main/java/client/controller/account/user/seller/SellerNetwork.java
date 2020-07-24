package client.controller.account.user.seller;
import java.net.InetAddress;

public class SellerNetwork {
    //private final InetAddress inetAddress;
    String ip;
    private final int port;

    public SellerNetwork(InetAddress inetAddress, int port) {
        this.ip = inetAddress.getHostAddress();
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getHostAddress() {
        return ip;
    }

   /* public InetAddress getInetAddress() {
        return inetAddress;
    }

    */
}

