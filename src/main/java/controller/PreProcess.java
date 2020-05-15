package controller;

import controller.account.user.BuyerController;
import controller.gift.Action;
import controller.gift.Event;
import controller.gift.GiftController;
import model.account.Account;
import model.account.Buyer;
import model.product.Discount;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PreProcess {
    private BuyerController buyerController;
    private ArrayList<Buyer> buyers;
    private ArrayList<Account> allBuyers;
    private ArrayList<GiftController> giftControllersArrayList;
    ArrayList<Event> events;
    ArrayList<Action> actions;

    public PreProcess() {
        this.buyers = new ArrayList<>();
        this.allBuyers = Buyer.getAllAccounts();
        this.giftControllersArrayList = new ArrayList<>();
        this.events = new ArrayList<>();
        this.actions = new ArrayList<>();
    }


    public void purchaseGift() {
        Action[] actions = {() -> {
            Buyer buyer = buyerController.getCurrentBuyer();
            ArrayList<Buyer> buyerArrayList = new ArrayList<>();
            buyerArrayList.add(buyer);
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 20,
                    1, buyerArrayList);
        }, () -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS), 5,
                    5, buyers);
            buyers.clear();
        }, () -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS), 3,
                    1, buyers);
            buyers.clear();
        }};
        Event[] events = {() -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 1000000;
        }, () -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 10000000;
        }, () -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 100000000;
        }};
        GiftController[] giftControllers = {new GiftController(actions[0], events[2]),
                new GiftController(actions[1], events[1]),
                new GiftController(actions[2], events[0])
        };
        for (GiftController giftController : giftControllers) {
            giftController.perform();
        }
    }

    public void initialEvents() {
        events.add(() -> {
            int count = 0;
            for (Account buyer : allBuyers) {
                if (buyer.getUsername().contains("E")) {
                    buyers.add((Buyer) buyer);
                    count += 1;
                } else if (buyer.getPhoneNumber().startsWith("44")) {
                    buyers.add((Buyer) buyer);
                    count += 1;
                }
            }
            return count != 0;
        });
    }

    public void initialActions() {
        actions.add(() -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.MONTHS), 5,
                    5, buyers);
            buyers.clear();
        });
    }

    public void processOnlyOneTime() {
        initialEvents();
        initialActions();
        giftControllersArrayList.add(new GiftController(actions.get(0), events.get(0)));
        for (GiftController giftController : giftControllersArrayList) {
            giftController.perform();
        }
    }

    public void setBuyerController(BuyerController buyerController) {
        this.buyerController = buyerController;
    }
}
