package model.product;

import model.Requestable;
import model.account.Seller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Advertisement implements Requestable {
    private static ArrayList<Advertisement> allAdds = new ArrayList<>();

    private Seller seller;
    private Product product;
    private LocalDateTime endDate;
    private RequestableState state;
    private String text;

    public Advertisement(Seller seller, Product product, LocalDateTime endDate, String text) {
        this.seller = seller;
        this.product = product;
        this.endDate = endDate;
        state = RequestableState.CREATED;
        this.text = text;
    }

    public static ArrayList<Advertisement> getAdds(){
        ArrayList<Advertisement> selectedAdds = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        int max = allAdds.size();
        int min = 0;
        if(allAdds.size()>5) {
            max = ThreadLocalRandom.current().nextInt(5, allAdds.size());
            min = max-5;
        }
        for (int i=min; i<max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i=min; i<max; i++)
            selectedAdds.add(allAdds.get(list.get(i)));
        return selectedAdds;
    }

    public void addAdvertisement () {
        Advertisement add = getAddBySeller(seller);
        //if (add!=null)
            //allAdds.remove(add);
        allAdds.add(this);
    }

    public static void removeAdvertisement (Advertisement advertisement) {
        allAdds.remove(advertisement);
    }

    public static boolean validAdd(Advertisement advertisement) {
        LocalDateTime now = LocalDateTime.now();
        if (advertisement.getEndDate().isBefore(now)) {
            allAdds.remove(advertisement);
            return false;
        }
        return true;
    }

    public static Advertisement getAddBySeller(Seller seller) {
        for (Advertisement add:allAdds) {
            if (add.getSeller().equals(seller))
                return add;
        }
        return null;
    }

    public String getText() {
        return text;
    }

    @Override
    public void changeStateAccepted() {
        state = RequestableState.ACCEPTED;
        addAdvertisement();
        seller.setCredit(seller.getCredit()-500000);
    }

    @Override
    public void changeStateRejected() {
        state = RequestableState.REJECTED;
    }

    @Override
    public void edit() {
        System.out.println("It is not editable!");
    }

    @Override
    public RequestableState getState() {
        return state;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Advertisement;
    }

    public static ArrayList<Advertisement> getAllAdds() {
        return allAdds;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Product: " + product.getName() + "\n\n" +
                "RequestType: Advertisement" + "\n\n" +
                "Seller: " + seller.getUsername() + "\n\n" +
                "endDate: " + endDate +"\n\n" +
                "Text: " + text;
    }
}
