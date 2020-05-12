package main.java.model;

public abstract class Requestable {
       public enum type{
           SELLER,
           PRODUCT,
           SALE,
       }
       public void changeStateAccepted(){}
       public void changeStateRejected(){}
}
