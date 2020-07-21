package server.network;
import java.lang.instrument.Instrumentation;

import client.network.Message;
import client.network.AuthToken;

public class Protector {
    private static Instrumentation instrumentation;


    // Broken Authentication
    public boolean isAuthenticationSecure(Message message) {
        if (!checkTokenTime(message.getAuthToken()))
            return false;
        return true;
    }

    private boolean checkTokenTime(AuthToken authToken) {
        return authToken.validTime();
    }


    // Improper inputs
    public boolean isMessageSecure(Message message) {
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
}
