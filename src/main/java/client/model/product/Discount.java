package client.model.product;

import com.gilecode.yagson.YaGson;
import client.model.account.Account;
import client.model.account.Buyer;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private static int numberOfDiscounts = 0;
    private int discountCode;
    private double discountPercentage;
    private int maxNumberOfUsage;
    private ArrayList<String> buyersWithDiscountIDs;
    private HashMap<String, Integer> numberOfUsageForEachBuyerUserName = new HashMap<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Discount(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage, int maxNumberOfUsage,
                    ArrayList<Buyer> buyersWithDiscount) throws Exception {
        if (discountPercentage > 100) {
            throw new IncorrectPercentage();
        }
        if (startDate.isAfter(endDate)) {
            throw new StartDateIsAfterEndDate();
        }
        numberOfDiscounts++;
        this.discountCode = numberOfDiscounts;
        this.discountPercentage = discountPercentage;
        this.maxNumberOfUsage = maxNumberOfUsage;
        addTOBuyersWithDiscount(buyersWithDiscount);
        this.startDate = startDate;
        this.endDate = endDate;
        allDiscounts.add(this);
        setNumberOfUsageForBuyers(maxNumberOfUsage, buyersWithDiscount);
    }

    private void addTOBuyersWithDiscount(ArrayList<Buyer> buyersWithDiscount) {
        this.buyersWithDiscountIDs = new ArrayList<>();
        for (Buyer buyer : buyersWithDiscount) {
            buyersWithDiscountIDs.add(buyer.getUsername());
        }
    }

    public void editDiscount(LocalDateTime startDate, LocalDateTime endDate, double discountPercentage,
                             int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) throws Exception {
        if (discountPercentage > 100) {
            throw new IncorrectPercentage();
        }
        if (startDate.isAfter(endDate)) {
            throw new StartDateIsAfterEndDate();
        }
        this.discountPercentage = discountPercentage;
        this.maxNumberOfUsage = maxNumberOfUsage;
        addTOBuyersWithDiscount(buyersWithDiscount);
        this.startDate = startDate;
        this.endDate = endDate;
        setNumberOfUsageForBuyers(maxNumberOfUsage, buyersWithDiscount);
    }

    public static ArrayList<Discount> getAllDiscountsBuyer(Buyer buyer) {
        ArrayList<Discount> allDiscountsBuyer = new ArrayList<>();
        for (Discount discount : allDiscounts) {
            if (discount.hasBuyer(buyer)) {
                allDiscountsBuyer.add(discount);
            }
        }
        return allDiscountsBuyer;
    }

    public static boolean validDiscountCodeBuyer(Buyer buyer, int discountCode) throws Exception {
        for (Discount discount : allDiscounts) {
            if (discount.getDiscountCode() == discountCode && discount.getBuyersWithDiscount().contains(buyer)) {
                if (discount.getNumberOfUsageForEachBuyer().get(buyer) == 0)
                    throw new discountUsedException();
                else
                    return true;
            }
        }
        throw new discountCodeNotFoundException();
    }

    public static int decreaseDiscountCodeUsageBuyer(Buyer buyer, int discountCode) {
        for (Discount discount : allDiscounts) {
            if (discount.getDiscountCode() == discountCode && discount.getBuyersWithDiscount().contains(buyer)
                    && discount.getNumberOfUsageForEachBuyer().get(buyer) > 0){
                discount.getNumberOfUsageForEachBuyer().replace(buyer,
                        discount.getNumberOfUsageForEachBuyer().get(buyer) - 1);
                return 1;
            }
        }
        return 0;
    }

    private void setNumberOfUsageForBuyers(int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) {
        for (int i = 0; i < buyersWithDiscount.size(); i++)
            this.numberOfUsageForEachBuyerUserName.put(buyersWithDiscount.get(i).getUsername(), maxNumberOfUsage);
    }

    public static Discount getDiscountByBuyer(client.model.account.Buyer buyer) {
        for (Discount discount : allDiscounts) {
            for (String username : discount.buyersWithDiscountIDs) {
                if (username.equals(buyer.getUsername())) {
                    return discount;
                }
            }
        }
        return null;
    }

    public static Discount getDiscountByDiscountCode(int discountCode) throws Exception {
        for (Discount discount : allDiscounts) {
            if (discount.getDiscountCode() == discountCode)
                return discount;
        }
        throw new discountCodeNotFoundException();
    }

    public void decreaseNumberOfUsageForBuyer(Buyer buyer) {
        Discount buyerSDiscount = getDiscountByBuyer(buyer);
        if (buyerSDiscount != null) {
            int number = buyerSDiscount.numberOfUsageForEachBuyerUserName.get(buyer.getUsername());
            if (number > 0)
                buyerSDiscount.numberOfUsageForEachBuyerUserName.replace(buyer.getUsername(), number - 1);
            else
                buyerSDiscount.numberOfUsageForEachBuyerUserName.replace(buyer.getUsername(), 0);
        }
    }

    public void deleteDiscount(Discount discount) {
        allDiscounts.remove(discount);
    }

    public Boolean isTheDiscountPeriodOver() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate) && now.isBefore(endDate))
            return false;
        return true;
    }

    public boolean hasBuyer(Buyer buyer) {
        for (String buyer1 : buyersWithDiscountIDs) {
            if (buyer1.equals(buyer.getUsername()))
                return true;
        }
        return false;
    }

    public static ArrayList<Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public int getDiscountCode() {
        return discountCode;
    }

    public HashMap<Buyer, Integer> getNumberOfUsageForEachBuyer() {
        HashMap<Buyer, Integer> hashMap = new HashMap<>();
        for (String username : numberOfUsageForEachBuyerUserName.keySet()) {
            try {
                Buyer buyer = Account.getBuyerWithUsername(username);
                hashMap.put(buyer, numberOfUsageForEachBuyerUserName.get(username));
            } catch (Exception e) {
            }
        }
        return hashMap;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public int getMaxNumberOfUsage() {
        return maxNumberOfUsage;
    }

    public ArrayList<Buyer> getBuyersWithDiscount() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (String buyerName : buyersWithDiscountIDs) {
            try {
                Buyer buyer = (Buyer) Account.getAccountWithUsername(buyerName);
                buyers.add(buyer);
            } catch (Exception e) {
            }
        }
        return buyers;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setDiscountPercentage(double discountPercentage) throws IncorrectPercentage {
        if (discountPercentage > 100) {
            throw new IncorrectPercentage();
        }
        this.discountPercentage = discountPercentage;
    }

    public void setMaxNumberOfUsage(int maxNumberOfUsage) {
        this.maxNumberOfUsage = maxNumberOfUsage;
    }

    public void setBuyersWithDiscount(ArrayList<Buyer> buyersWithDiscount) {
        addTOBuyersWithDiscount(buyersWithDiscount);
    }

    public void setStartDate(LocalDateTime startDate) throws StartDateIsAfterEndDate {
        if (startDate.isAfter(endDate)) {
            throw new StartDateIsAfterEndDate();
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) throws StartDateIsAfterEndDate {
        if (startDate.isAfter(endDate)) {
            throw new StartDateIsAfterEndDate();
        }
        this.endDate = endDate;
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public static void removeDiscountCode(int discountCode) throws Exception {
        for (Discount discount : allDiscounts) {
            if (discount.getDiscountCode() == discountCode) {
                allDiscounts.remove(discount);
                return;
            }
        }
        throw new discountCodeNotFoundException();
    }

    public static class discountCodeNotFoundException extends Exception {
        public discountCodeNotFoundException() {
            super("discount code doesn't exist");
        }
    }

    public static class discountUsedException extends Exception {
        public discountUsedException() {
            super("Discount has reached its maximum number of usage");
        }
    }

    public void removeBuyerFromBuyersList(Buyer buyer) {
        this.buyersWithDiscountIDs.remove(buyer.getUsername());
        this.numberOfUsageForEachBuyerUserName.remove(buyer.getUsername());
    }

    public void addBuyerToBuyersList(Buyer buyer) {
        this.buyersWithDiscountIDs.add(buyer.getUsername());
        this.numberOfUsageForEachBuyerUserName.put(buyer.getUsername(), maxNumberOfUsage);
    }

    public int getNumberOfUsageBuyer(Buyer buyer) {
        return this.numberOfUsageForEachBuyerUserName.get(buyer.getUsername());
    }


    @Override
    public String toString() {
        String users = "";
        int i = 1;
        for (String buyersWithDiscountID : buyersWithDiscountIDs) {
            users += (i++) + ": " + buyersWithDiscountID + "\n";
        }
        return "discountCode:     " + discountCode + "\n" +
                "discountPercentage:    " + discountPercentage + "\n" +
                "maxNumberOfUsage:    " + maxNumberOfUsage + "\n" +
                "buyersWithDiscountIDs:    " + users +
                "startDate:    " + startDate + "\n" +
                "endDate:    " + endDate;
    }

    public static class IncorrectPercentage extends Exception {
        public IncorrectPercentage() {
            super("your percentage is over 100!!");
        }
    }

    public static class StartDateIsAfterEndDate extends Exception {
        public StartDateIsAfterEndDate() {
            super("start date is after end date!!!");
        }
    }


    public static void store() {
        storeDiscounts();
        storeNumberOfDiscounts();
    }

    public static void load() {
        loadDiscounts();
        loadNumberOfDiscounts();
    }

    public static void storeDiscounts() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutDiscount/discounts.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Discount discount : allDiscounts) {
                fileWriter.write(yaGson.toJson(discount) + "\n");
            }
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadDiscounts() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutDiscount/discounts.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Discount discount = yaGson.fromJson(fileScanner.nextLine(), Discount.class);
                allDiscounts.add(discount);
            }
        } catch (Exception ignored) {
        }
    }

    public static void storeNumberOfDiscounts() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutDiscount/numberOfDiscounts.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(yaGson.toJson(numberOfDiscounts) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public static void loadNumberOfDiscounts() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream(
                    "src/main/resources/aboutDiscount/numberOfDiscounts.txt");
            Scanner fileScanner = new Scanner(inputStream);
            numberOfDiscounts = yaGson.fromJson(fileScanner.nextLine(), Integer.class);
            fileScanner.close();
        } catch (IOException ignored) {
        }
    }
}
