package model.product;

import model.account.Buyer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private String discountCode;
    private double discountPercentage;
    private int maxNumberOfUsage;
    private ArrayList<model.account.Buyer> buyersWithDiscount;
    private HashMap<Buyer,Integer> numberOfUsageForEachBuyer = new HashMap<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Discount(LocalDateTime startDate,LocalDateTime endDate,String discountCode, double discountPercentage, int maxNumberOfUsage, ArrayList<Buyer> buyersWithDiscount) {
        this.discountCode = discountCode;
        this.discountPercentage = discountPercentage;
        this.maxNumberOfUsage = maxNumberOfUsage;
        this.buyersWithDiscount = buyersWithDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        allDiscounts.add(this);
        setNumberOfUsageForBuyers(maxNumberOfUsage,buyersWithDiscount);
    }

    public static boolean validDiscountCodeBuyer(Buyer buyer, String discountCode) {
        for (Discount discount:allDiscounts) {
            if (discount.getDiscountCode().equals(discountCode) && discount.getBuyersWithDiscount().contains(buyer) && discount.getNumberOfUsageForEachBuyer().get(buyer)>0)
                return true;
        }
        return false;
    }

    public static void decreaseDiscountCodeUsageBuyer(Buyer buyer, String discountCode) {
        for (Discount discount:allDiscounts) {
            if (discount.getDiscountCode().equals(discountCode) && discount.getBuyersWithDiscount().contains(buyer) && discount.getNumberOfUsageForEachBuyer().get(buyer)>0)
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

    public static Discount getDiscountByDiscountCode(String discountCode){
        for(Discount discount:allDiscounts) {
                if (discount.getDiscountCode().equals(discountCode))
                    return discount;
        }
        return null;
        //throw new doesNotExitThisDiscountCodeException();
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

    public static ArrayList<Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public String getDiscountCode() {
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

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
