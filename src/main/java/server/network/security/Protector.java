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
    private HashMap<String, ActivityDensity> allClientsIp = new HashMap<>();
    private HashMap<String, Integer> loginClientsIp = new HashMap<>();



    public boolean isMessageSecure(Message message, Socket socket) throws Exception {
        getAddIp(message, socket);
        return true;
    }

    public void removeIpFromLoginIps(Socket socket) {
        String ip = getIp(socket);
        for (String ip1:loginClientsIp.keySet()) {
            if (ip.equals(ip1)) {
                loginClientsIp.remove(ip1);
                return;
            }
        }
    }

    public void removeIpFromAllIps(Socket socket) {
        String ip = getIp(socket);
        for (String ip1:allClientsIp.keySet()) {
            if (ip.equals(ip1)) {
                allClientsIp.remove(ip1);
                return;
            }
        }
    }




    // Brute force & Denial of Service (DoS) attack
    private void isIpDangerous(Socket socket) throws Exception{
        String ip = getIp(socket);
        DangerousIp dangerousIp = getDangerousIpByIp(ip);
        if (dangerousIp==null)
            return;
        else if (!dangerousIp.isStillDangerous()) {
            dangerousIps.remove(dangerousIp);
            return;
        }
        throw new BlockUserException();
    }

    public void getAddIp(Message message, Socket socket) {
        String ip = getIp(socket);
        if (allClientsIp.get(ip)==null)
            allClientsIp.put(ip, new ActivityDensity());
        else
            allClientsIp.get(ip).addActivity();
        if (message.getText().equals("login")) {
            if (allClientsIp.get(ip)==null)
                loginClientsIp.put(ip, 1);
            else
                loginClientsIp.replace(ip, loginClientsIp.get(ip)+1);
        }
    }

    public void checkLoginAttempts(Socket socket) {
        String ip = getIp(socket);
        int attempts = loginClientsIp.get(ip);
        if (attempts>3) {
            loginClientsIp.remove(ip);
            dangerousIps.add(new DangerousIp(ip, DangerousIpType.LOGIN_DANGER));
        }
    }

    public void checkActivityDensity(Socket socket) {
        String ip = getIp(socket);
        ActivityDensity activityDensity = allClientsIp.get(ip);
        if (activityDensity.getActivityDensity()>100) {
            allClientsIp.remove(ip);
            dangerousIps.add(new DangerousIp(ip, DangerousIpType.EXCESSIVE_ACTIVITY));
        }
    }

    public boolean isClientOverload() {
        return allClientsIp.size() > 1000000;
    }




    // Broken Authentication
    private void isAuthenticationSecure(Message message) throws Exception {
        if (!checkTokenTime(message.getAuthToken()))
            throw new AuthenticationTimeOutException();
    }
    // -> Password is weak!

    private boolean checkTokenTime(AuthToken authToken) {
        return authToken.validTime();
    }




    // Improper inputs
    public void isMessageImproper(Message message) throws Exception {
        if (!checkSizes(message))
            throw new MalMessageException();
        checkMessagePattern(message);
    }

    private void checkMessagePattern(Message message) throws Exception{
        if (message.getText()==null)
            throw new MalMessageException();
        if (!message.getText().matches("\\w+"))
            throw new MalMessageException();
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

    public static class IpNotFoundException extends Exception {
        public IpNotFoundException() {
            super("IP not found");
        }
    }

    public static class BlockUserException extends Exception {
        public BlockUserException() {
            super("You are currently blocked. Please try again later.");
        }
    }

    public static class AuthenticationTimeOutException extends Exception {
        public AuthenticationTimeOutException() {
            super("Your authentication is expired. Please exit and try again.");
        }
    }


    public static class MalMessageException extends Exception {
        public MalMessageException() {
            super("Your message is not suitable. Please try again later. (long names, ...)");
        }
    }
}
