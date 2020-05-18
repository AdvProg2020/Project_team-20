package model;

import model.product.RequestType;
import model.product.RequestableState;

public interface Requestable {
    void changeStateAccepted();
    void changeStateRejected();
    String toString();
    void edit();
    RequestableState getState();
    RequestType getRequestType();
}
