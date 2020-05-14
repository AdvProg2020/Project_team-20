package model.product;

import model.account.Buyer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    public static int numberOfDiscounts;
    private int discountCode;
    private double discountPercentage;
    private int maxNumberOfUsage;
    private ArrayList<Buyer> buyersWithDiscount;
    private HashMap<Buyer,Integer> numberOfUsageForEachBuyer = new HashMap<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Discount(LocalDateTime startDate,LocalDateTime endDate,double discountPercentage, int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) {
        numberOfDiscounts++;
        this.discountCode = numberOfDiscounts;
        this.discountPercentage = discountPercentage;
        this.maxNumberOfUsage = maxNumberOfUsage;
        this.buyersWithDiscount = buyersWithDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        allDiscounts.add(this);
        setNumberOfUsageForBuyers(maxNumberOfUsage,buyersWithDiscount);
    }

    public void editDiscount(LocalDateTime startDate,LocalDateTime endDate, double discountPercentage, int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount){
        this.discountPercentage = discountPercentage;
        this.maxNumberOfUsage = maxNumberOfUsage;
        this.buyersWithDiscount = buyersWithDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        setNumberOfUsageForBuyers(maxNumberOfUsage,buyersWithDiscount);
    }

    public static ArrayList<Discount> getAllDiscountsBuyer (Buyer buyer) {
        ArrayList<Discount> allDiscountsBuyer = new ArrayList<>();
        for (Discount discount:allDiscounts) {
            if (discount.hasBuyer(buyer)) {
                allDiscountsBuyer.add(discount);
            }
        }
        return allDiscountsBuyer;
    }

    public static boolean validDiscountCodeBuyer(Buyer buyer, int discountCode) throws Exception{
        for (Discount discount:allDiscounts) {
            if (discount.getDiscountCode()==discountCode && discount.getBuyersWithDiscount().contains(buyer))
                if (discount.getNumberOfUsageForEachBuyer().get(buyer)==0)
                    throw new discountUsedException();
                else
                    return true;
        }
        throw  new discountCodeNotFoundException();
    }

    public static void decreaseDiscountCodeUsageBuyer(Buyer buyer, int discountCode) {
        for (Discount discount:allDiscounts) {
            if (discount.getDiscountCode()==discountCode && discount.getBuyersWithDiscount().contains(buyer) && discount.getNumberOfUsageForEachBuyer().get(buyer)>0)
                discount.getNumberOfUsageForEachBuyer().put(buyer, discount.getNumberOfUsageForEachBuyer().get(buyer)-1);
        }
    }

    private void setNumberOfUsageForBuyers(int maxNumberOfUsage,ArrayList<Buyer> buyersWithDiscount){
        for(int i =0 ; i<buyersWithDiscount.size() ; i++)
            this.numberOfUsageForEachBuyer.put(buyersWithDiscount.get(i), maxNumberOfUsage);
    }

    public static Discount getDiscountByBuyer(model.account.Buyer buyer){
        for(Discount discount:allDiscounts) {
            for (Buyer buyer1 : discount.buyersWithDiscount) {
                if (buyer1.equals(buyer)) {
                    return discount;
                }
            }
        }
        return null;
    }

    public static Discount getDiscountByDiscountCode(int discountCode) throws Exception {
        for(Discount discount:allDiscounts) {
                if (discount.getDiscountCode()==discountCode)
                    return discount;
        }
        throw new discountCodeNotFoundException();
    }

    public void decreaseNumberOfUsageForBuyer(Buyer buyer){
        Discount buyerSDiscount = getDiscountByBuyer(buyer);
        if(buyerSDiscount!=null){
            int number = buyerSDiscount.numberOfUsageForEachBuyer.get(buyer);
            if(number>0)
                buyerSDiscount.numberOfUsageForEachBuyer.replace(buyer, number-1);
            else
                buyerSDiscount.numberOfUsageForEachBuyer.replace(buyer, 0);
        }
    }

    public void deleteDiscount(Discount discount){
        allDiscounts.remove(discount);
    }

    public Boolean isTheDiscountPeriodOver(){
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(startDate) && now.isBefore(endDate))
            return false;
        return true;
    }

    public boolean hasBuyer(Buyer buyer) {
        for (Buyer buyer1:buyersWithDiscount) {
            if (buyer1.equals(buyer))
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
        return numberOfUsageForEachBuyer;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public int getMaxNumberOfUsage() {
        return maxNumberOfUsage;
    }

    public ArrayList<Buyer> getBuyersWithDiscount() {
        return buyersWithDiscount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setMaxNumberOfUsage(int maxNumberOfUsage) {
        this.maxNumberOfUsage = maxNumberOfUsage;
    }

    public void setBuyersWithDiscount(ArrayList<Buyer> buyersWithDiscount) {
        this.buyersWithDiscount = buyersWithDiscount;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public static void removeDiscountCode(int discountCode) throws Exception{
        for(Discount discount : allDiscounts){
            if(discount.getDiscountCode()==discountCode){
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

    public void removeBuyerFromBuyersList(Buyer buyer){
        this.buyersWithDiscount.remove(buyer);
        this.numberOfUsageForEachBuyer.remove(buyer);
    }

    public void addBuyerToBuyersList(Buyer buyer){
        this.buyersWithDiscount.add(buyer);
        this.numberOfUsageForEachBuyer.put(buyer,maxNumberOfUsage);
    }
    
    public int getNumberOfUsageBuyer(Buyer buyer){
        return this.numberOfUsageForEachBuyer.get(buyer);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
