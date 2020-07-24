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
    private ArrayList<DangerousIp> dangerousIps = new ArrayList<>();
    private HashMap<String, ActivityDensity> allClientsIp = new HashMap<>();
    private HashMap<String, Integer> loginClientsIp = new HashMap<>();



    public void isMessageSecure(Message message, Socket socket) throws Exception {
        String ip = getIp(socket);
        isClientOverload();
        getAddIp(message, ip);
        if (message.getText().equals("login"))
            checkLoginAttempts(ip);
        checkActivityDensity(ip);
        isIpDangerous(ip);
        isAuthenticationSecure(message);
        isMessageImproper(message);
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

    public void removeIpFromAllIps(String ip) throws Exception{
        for (String ip1:allClientsIp.keySet()) {
            if (ip.equals(ip1)) {
                allClientsIp.remove(ip1);
                return;
            }
        }
        throw new IpNotFoundException();
    }




    // Brute force & Denial of Service (DoS) attack
    private void isIpDangerous(String ip) throws Exception{
        DangerousIp dangerousIp = getDangerousIpByIp(ip);
        if (dangerousIp==null)
            return;
        else if (!dangerousIp.isStillDangerous()) {
            dangerousIps.remove(dangerousIp);
            return;
        }
        throw new BlockUserException();
    }

    public void getAddIp(Message message, String ip) {
        if (allClientsIp.get(ip)==null)
            allClientsIp.put(ip, new ActivityDensity());
        else
            allClientsIp.get(ip).addActivity();
        if (message.getText().equals("login")) {
            if (loginClientsIp.get(ip)==null)
                loginClientsIp.put(ip, 1);
            else
                loginClientsIp.replace(ip, loginClientsIp.get(ip)+1);
        }
    }

    public void checkLoginAttempts(String ip) {
        int attempts = loginClientsIp.get(ip);
        if (attempts>3) {
            loginClientsIp.remove(ip);
            dangerousIps.add(new DangerousIp(ip, DangerousIpType.LOGIN_DANGER));
        }
    }

    public void checkActivityDensity(String ip) {
        ActivityDensity activityDensity = allClientsIp.get(ip);
        if (activityDensity.getActivityDensity()>100) {
            allClientsIp.remove(ip);
            dangerousIps.add(new DangerousIp(ip, DangerousIpType.EXCESSIVE_ACTIVITY));
        }
    }

    public void isClientOverload() throws Exception{
        if (allClientsIp.size() > 1000000)
            throw new BusyServerException();
    }




    // Broken Authentication & Replay Attacks
    private void isAuthenticationSecure(Message message) throws Exception {
        if (!checkTokenTime(message.getAuthToken()))
            throw new AuthenticationTimeOutException();
    }
    // -> Password is weak!

    private boolean checkTokenTime(AuthToken authToken) {
        if (authToken==null)
            return true;
        return authToken.validTime();
    }




    // Improper inputs
    public void isMessageImproper(Message message) throws Exception {
        if (!checkSizes(message))
            throw new MalMessageException();
        checkMessagePattern(message);
    }

    private synchronized void checkMessagePattern(Message message) throws Exception{
        if (message.getText()==null)
            throw new MalMessageException();
        /*
        if (!message.getText().matches("\\w+")) {
            throw new MalMessageException();
        }
        */
    }

    private boolean checkSizes(Message message) {
         Instrumentation instrumentation;
        if (message.getText().length()>1000000000)
            return false;
        if (message.getObjects().size()>1000000000)
            return false;
        return message.toYaGson().length() <= 1000000000;
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

    public static class BusyServerException extends Exception {
        public BusyServerException() {
            super("Unfortunately our servers are really busy. Come back soon!");
        }
    }

    public static class MalMessageException extends Exception {
        public MalMessageException() {
            super("Your message is not suitable. Please try again later. (long names, ...)");
        }
    }
}
