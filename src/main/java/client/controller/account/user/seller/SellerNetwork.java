package client.controller.account.user.seller;
import java.net.InetAddress;

public class SellerNetwork {
    private final InetAddress inetAddress;
    private final int port;

    public SellerNetwork(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getHostAddress() {
        return inetAddress.getHostAddress();
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
}

