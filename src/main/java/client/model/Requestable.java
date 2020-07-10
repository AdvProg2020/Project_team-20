package client.model;

import client.model.product.RequestType;
import client.model.product.RequestableState;

public interface Requestable {
    void changeStateAccepted();

    void changeStateRejected();

    String toString();

    void edit();

    RequestableState getState();

    RequestType getRequestType();
}
