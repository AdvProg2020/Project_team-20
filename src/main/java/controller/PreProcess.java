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
    private BuyerController buyerController = BuyerController.getInstance();
    private ArrayList<Buyer> buyers = new ArrayList<>();
    private ArrayList<Account> allBuyers = Buyer.getAllAccounts();
    private ArrayList<GiftController> giftControllers;

    {
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Action> actions = new ArrayList<>();
        events.add(() -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 1000000;
        });

        events.add(() -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 10000000;
        });

        events.add(() -> {
            double totalPrice = buyerController.getTotalPrice();
            return totalPrice >= 100000000;
        });

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


        actions.add(() -> {
            Buyer buyer = buyerController.getCurrentBuyer();
            buyers.add(buyer);
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 20,
                    1, buyers);
            buyers.clear();
        });

        actions.add(() -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS), 3,
                    1, buyers);
            buyers.clear();
        });

        actions.add(() -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS), 5,
                    5, buyers);
            buyers.clear();
        });


        actions.add(() -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.MONTHS), 5,
                    5, buyers);
            buyers.clear();
        });


        giftControllers.add(new GiftController(actions.get(1), events.get(0)));
        giftControllers.add(new GiftController(actions.get(2), events.get(1)));
        giftControllers.add(new GiftController(actions.get(3), events.get(2)));
        giftControllers.add(new GiftController(actions.get(0), events.get(3)));

        giftControllers.get(3).perform();
    }

    public void doPreProcess() {
        giftControllers.get(0).perform();
        giftControllers.get(1).perform();
        giftControllers.get(2).perform();
    }
}
