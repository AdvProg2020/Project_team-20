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
        getAddIp(message, socket);
        return true;
    }

    public void checkLoginAttempts(Socket socket) {
        String ip = getIp(socket);
        int attempts = loginClientsIp.get(ip);
        if (attempts>3) {
            loginClientsIp.remove(ip);
            dangerousIps.add(new DangerousIp(ip, DangerousIpType.LOGIN_DANGER));
        }
    }

    public void getAddIp(Message message, Socket socket) {
        String ip = getIp(socket);
        if (allClientsIp.get(ip)==null)
            allClientsIp.put(ip, 1);
        else
            allClientsIp.replace(ip, allClientsIp.get(ip)+1);
        if (message.getText().equals("login")) {
            if (allClientsIp.get(ip)==null)
                loginClientsIp.put(ip, 1);
            else
                loginClientsIp.replace(ip, loginClientsIp.get(ip)+1);
        }
    }

    public void removeIpFromLoginIps(Socket socket) throws Exception{
        String ip = getIp(socket);
        for (String ip1:allClientsIp.keySet()) {
            if (ip.equals(ip1)) {
                loginClientsIp.remove(ip1);
                return;
            }
        }
    }

    // Brute force attack
    private boolean isIpDangerous(Socket socket) {
        String ip = getIp(socket);
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
        String ip = getIp(socket);
        allClientsIp.remove(ip);
    }

    private String getIp(Socket socket) {
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        return ((InetSocketAddress)socketAddress).getAddress().toString();
    }

    private DangerousIp getDangerousIpByIp(String ip) {
        for (DangerousIp dangerousIp:dangerousIps) {
            if (dangerousIp.getIp().equals(ip))
                return dangerousIp;
        }
        return null;
    }

    public static class IpNotFound extends Exception {
        public IpNotFound() {
            super("IP not found");
        }
    }
}
