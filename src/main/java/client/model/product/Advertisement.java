package client.model.product;

import com.gilecode.yagson.YaGson;
import client.model.Requestable;
import client.model.account.Seller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Advertisement implements Requestable {
    private static ArrayList<Advertisement> allAdds = new ArrayList<>();

    private String sellerId;
    private String productId;
    private LocalDateTime endDate;
    private RequestableState state;
    private String text;

    public Advertisement(Seller seller, Product product, LocalDateTime endDate, String text) {
        this.sellerId = seller.getUsername();
        this.productId = product.getId();
        this.endDate = endDate;
        state = RequestableState.CREATED;
        this.text = text;
    }

    public static ArrayList<Advertisement> getAdds() {
        ArrayList<Advertisement> selectedAdds = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        int max = allAdds.size();
        int min = 0;
        if (allAdds.size() > 5) {
            max = ThreadLocalRandom.current().nextInt(5, allAdds.size());
            min = max - 5;
        }
        for (int i = min; i < max; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = min; i < max; i++)
            selectedAdds.add(allAdds.get(list.get(i)));
        return selectedAdds;
    }

    private void addAdvertisement() {
        try {
            Advertisement add = getAddBySeller((Seller) Seller.getAccountWithUsername(sellerId));
            if (add != null)
                allAdds.remove(add);
            allAdds.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeAdvertisement(Advertisement advertisement) {
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

    private static Advertisement getAddBySeller(Seller seller) {
        for (Advertisement add : allAdds) {
            if (add.getSeller().equals(seller))
                return add;
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getProductId() {
        return productId;
    }

    public String getSellerId() {
        return sellerId;
    }

    @Override
    public void changeStateAccepted() {
        Seller seller = null;
        try {
            seller = Seller.getSellerWithUsername(sellerId);
            state = RequestableState.ACCEPTED;
            addAdvertisement();
            seller.setCredit(seller.getCredit() - 500000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private Seller getSeller() {
        try {
            return Seller.getSellerWithUsername(sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getProduct() {
        try {
            return Product.getProductById(productId);
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public static void store() {
        YaGson yaGson = new YaGson();
        File file = new File("src/main/resources/aboutAdvertisement/advertisement.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            for (Advertisement advertisement : allAdds)
                fileWriter.write(yaGson.toJson(advertisement) + "\n");
            fileWriter.close();
        } catch (IOException ignored) {
        }

    }

    public static void load() {
        YaGson yaGson = new YaGson();
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/aboutAdvertisement/advertisement.txt");
            Scanner fileScanner = new Scanner(inputStream);
            while (fileScanner.hasNextLine()) {
                Advertisement advertisement = yaGson.fromJson(fileScanner.nextLine(), Advertisement.class);
                allAdds.add(advertisement);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public String toString() {
        try {
            Product product = Product.getProductById(productId);
            Seller seller = Seller.getSellerWithUsername(sellerId);
            return "Product: " + product.getName() + "\n\n" +
                    "RequestType: Advertisement" + "\n\n" +
                    "Seller: " + seller.getUsername() + "\n\n" +
                    "endDate: " + endDate + "\n\n" +
                    "Text: " + text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
