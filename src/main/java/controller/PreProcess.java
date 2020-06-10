package controller;

import com.gilecode.yagson.YaGson;
import controller.account.user.BuyerController;
import controller.gift.Action;
import controller.gift.Event;
import controller.gift.GiftController;
import model.account.Account;
import model.account.Buyer;
import model.product.Discount;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class PreProcess {
    private static int period;
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
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS),
                    20, 1, buyerArrayList);
        }, () -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS),
                    5, 5, buyers);
            buyers.clear();
        }, () -> {
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.DAYS),
                    3, 1, buyers);
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
            new Discount(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.MONTHS),
                    5, 5, buyers);
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

    public static int getPeriod() {
        return period;
    }

    public static void AddPeriod() {
        PreProcess.period += 1;
    }

    public static void store() {
        storePeriods();
    }

    public static void load() {
        loadPeriods();
    }

    private static void storePeriods() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutGift/numberOfPeriods.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(period) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    private static void loadPeriods() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutGift/numberOfPeriods.txt");
            Scanner fileScanner = new Scanner(inputStream);
            PreProcess.period = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }
}
