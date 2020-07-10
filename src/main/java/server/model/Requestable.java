package server.model;

import server.model.product.RequestType;
import server.model.product.RequestableState;

public interface Requestable {
    void changeStateAccepted();

    void changeStateRejected();

    String toString();

    void edit();

    RequestableState getState();

    RequestType getRequestType();
}
