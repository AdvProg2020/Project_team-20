package server.network.security;
import java.lang.instrument.Instrumentation;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import client.network.Message;
import client.network.AuthToken;

public class Protector {
    private static Instrumentation instrumentation;
    private ArrayList<DangerousIp> dangerousIps;
    private HashMap<String, Integer> allClientsIp = new HashMap<>();
    private HashMap<String, Integer> loginClientsIp = new HashMap<>();



    public boolean isMessageSecure(Message message, Socket socket) {
        getAddIp(socket);
        return true;
    }

    public void getAddIp(Socket socket) {
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        String ip =((InetSocketAddress)socketAddress).getAddress().toString();
        if (allClientsIp.get(ip)==null)
            allClientsIp.put(ip, 1);
        else
            allClientsIp.replace(ip, allClientsIp.get(ip)+1);
    }

    // Brute force attack
    private boolean isIpDangerous(Socket socket) {
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        String ip =((InetSocketAddress)socketAddress).getAddress().toString();
        DangerousIp dangerousIp = getDangerousIpByIp(ip);
        if (dangerousIp==null)
            return false;
        else if (!dangerousIp.isStillDangerous()) {
            dangerousIps.remove(dangerousIp);
            return false;
        }
        return true;
    }




    // Broken Authentication
    private boolean isAuthenticationSecure(Message message) {
        return checkTokenTime(message.getAuthToken());
    }
    // -> Password is wick!


    private boolean checkTokenTime(AuthToken authToken) {
        return authToken.validTime();
    }


    // Improper inputs
    public boolean isMessageImproper(Message message) {
        if (!checkSizes(message))
            return false;
        return checkMessagePattern(message);
    }

    private boolean checkMessagePattern(Message message) {
        if (message.getText()==null)
            return false;
        return message.getText().matches("\\w+");
    }

    private boolean checkSizes(Message message) {
        if (message.getText().length()>100)
            return false;
        if (message.getObjects().size()>100)
            return false;
        return instrumentation.getObjectSize(message) <= 1000000000;
    }

    public void removeIp(Socket socket) {
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        String ip =((InetSocketAddress)socketAddress).getAddress().toString();
        allClientsIp.remove(ip);
    }

    private DangerousIp getDangerousIpByIp(String ip) {
        for (DangerousIp dangerousIp:dangerousIps) {
            if (dangerousIp.getIp().equals(ip))
                return dangerousIp;
        }
        return null;
    }
}
