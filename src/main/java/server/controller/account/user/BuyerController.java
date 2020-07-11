package server.controller.account.user;

import server.controller.Main;
import server.controller.PreProcess;
import javafx.scene.image.Image;
import server.model.account.*;
import server.model.product.*;
import server.model.product.comment.Score;
import server.model.receipt.BuyerReceipt;
import server.model.receipt.SellerReceipt;
import server.network.AuthToken;
import server.network.Message;
import server.network.server.Server;

import java.util.ArrayList;

public class BuyerController extends Server implements AccountController {


    private static BuyerController buyerController = null;

    private BuyerController() {
        super(6000);
    }

    public static BuyerController getInstance() {
        if (buyerController == null)
            buyerController = new BuyerController();
        return buyerController;
    }

    public Message getAllProducts(AuthToken authToken) {
        Message message = new Message("all products");
        message.addToObjects(Product.getAllProducts());
        return message;
    }

    public Message getAllDiscounts(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        PreProcess preProcess = new PreProcess();
        preProcess.setBuyerController(buyerController);
        preProcess.purchaseGift(currentBuyer);
        Message message = new Message("all discounts");
        message.addToObjects(Discount.getAllDiscountsBuyer(currentBuyer));
        return message;
    }

    public Message viewOrders(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("view orders");
        message.addToObjects(currentBuyer.getPurchaseHistory());
        return message;
    }

    public Message rate(String productId, double score, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        if (currentBuyer.hasBoughtProduct(productId)) {
            try {
                Product product = Product.getProductById(productId);
                product.addScore(new Score(currentBuyer, score, product));
                return new Message("rate succeeded");
            } catch (Exception e) {
                Message message = new Message("Error");
                message.addToObjects(e);
                return message;
            }
        } else {
            Message message = new Message("Error");
            message.addToObjects(new buyerHasNotBought());
            return message;
        }
    }

    public Message getBuyerReceiptById(String id, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("buyer receipt");
        try {
            message.addToObjects(currentBuyer.getReceiptById(id));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    private void addBuyerToProductsBuyers(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        for (Product product : currentBuyer.getCart().getAllProducts().keySet()) {
            product.addBuyer(currentBuyer);
        }
    }

    public Message purchase(String address, String phoneNumber, String discountCode, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message;
        receiveInformation(address, phoneNumber, currentBuyer);
        double discountPercentage = 0;
        double totalPrice = (double) getTotalPrice(authToken).getObjects().get(0);
        try {
            if (!discountCode.equals("") && Discount.validDiscountCodeBuyer(currentBuyer, Integer.parseInt(discountCode))) {
                discountPercentage = Discount.getDiscountByDiscountCode(Integer.parseInt(discountCode))
                        .getDiscountPercentage();
                if (Discount.decreaseDiscountCodeUsageBuyer(currentBuyer, Integer.parseInt(discountCode)) == 0) {
                    System.out.println(discountPercentage);
                    totalPrice *= (1 - discountPercentage);
                }
            }
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        try {
            pay(totalPrice, currentBuyer);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
            return message;
        }
        decreaseAllProductBought(authToken);
        makeReceipt(totalPrice, discountPercentage, currentBuyer);
        addBuyerToProductsBuyers(authToken);
        currentBuyer.getCart().resetCart();
        message = new Message("purchase was successful");
        return message;
    }

    private void decreaseAllProductBought(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        for (SelectedProduct selectedProduct : currentBuyer.getCart().getSelectedProducts()) {
            decreaseProductSeller(selectedProduct.getProduct(), selectedProduct.getCount(), selectedProduct.getSeller());
        }
    }

    private void decreaseProductSeller(Product product, int number, Seller seller) {
        seller.decreaseProduct(product, number);
        product.decreaseCountSeller(seller, number);
    }

    private void makeReceipt(double totalPrice, double discountPercentage, Buyer currentBuyer) {
        makeBuyerReceipt(totalPrice, discountPercentage, currentBuyer);
        makeSellerReceipt(discountPercentage, currentBuyer);
    }

    private void makeSellerReceipt(double discountPercentage, Buyer currentBuyer) {
        Cart cart = currentBuyer.getCart();
        ArrayList<Seller> sellers = cart.getAllSellers();
        for (Seller seller : sellers) {
            seller.addToSaleHistory(new SellerReceipt(Integer.toString(SellerReceipt.getSellerReceiptCount()),
                    discountPercentage, cart.getAllProductsSeller(seller),
                    false, getTotalPriceTotalDiscountSeller(seller, 0, currentBuyer), currentBuyer,
                    getTotalPriceTotalDiscountSeller(seller, 1, currentBuyer)));
        }
    }

    private double getTotalPriceTotalDiscountSeller(Seller seller, int type, Buyer currentBuyer) {
        Cart cart = currentBuyer.getCart();
        double totalPriceSeller = 0;
        double totalDiscount = 0;
        ArrayList<SelectedProduct> allSelectedProductsSeller = cart.getAllProductsOfSeller(seller);
        for (SelectedProduct selectedProduct : allSelectedProductsSeller) {
            Product product = selectedProduct.getProduct();
            Sale sale = getSaleForProductOfSeller(product, seller);
            if (sale != null) {
                totalPriceSeller += product.getPrice(seller) * selectedProduct.getCount() *
                        (1 - sale.getSalePercentage());
                totalDiscount += product.getPrice(seller) * selectedProduct.getCount() * sale.getSalePercentage();
            } else
                totalPriceSeller += product.getPrice(seller) * selectedProduct.getCount();
        }
        if (type == 0)
            return totalPriceSeller;
        else
            return totalDiscount;
    }

    private void makeBuyerReceipt(double totalPrice, double discountPercentage, Buyer currentBuyer) {
        Cart cart = currentBuyer.getCart();
        currentBuyer.addReceipt(new BuyerReceipt(Integer.toString(BuyerReceipt.getBuyerReceiptCount()),
                discountPercentage, cart.getAllProducts(), false, totalPrice, cart.getAllSellers()));
    }

    private void pay(double totalPrice, Buyer currentBuyer) throws Exception {
        currentBuyer.decreaseCredit(totalPrice);
        for (Seller seller : currentBuyer.getCart().getAllSellers()) {
            seller.increaseCredit(getTotalPriceTotalDiscountSeller(seller, 0, currentBuyer));
        }
    }

    private void receiveInformation(String address, String phoneNumber, Buyer currentBuyer) {
        currentBuyer.setAddress(address);
        currentBuyer.setPhoneNumber(phoneNumber);
    }

    public Message getTotalPrice(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        double totalPrice = 0;
        for (SelectedProduct selectedProduct : currentBuyer.getCart().getSelectedProducts()) {
            Sale saleForProduct = getSaleForProduct(selectedProduct);
            Seller seller = selectedProduct.getSeller();
            if (saleForProduct != null && saleForProduct.validSaleTime())
                totalPrice += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount() *
                        (1 - saleForProduct.getSalePercentage());
            else
                totalPrice += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount();
        }
        Message message = new Message("totalPrice");
        message.addToObjects(totalPrice);
        return message;
    }

    public Message getDiscount(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        double totalPrice = 0;
        double priceWithoutDiscount = 0;
        for (SelectedProduct selectedProduct : currentBuyer.getCart().getSelectedProducts()) {
            Sale saleForProduct = getSaleForProduct(selectedProduct);
            Seller seller = selectedProduct.getSeller();
            if (saleForProduct != null && saleForProduct.validSaleTime()) {
                totalPrice += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount() *
                        (1 - saleForProduct.getSalePercentage());
                priceWithoutDiscount += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount();
            } else {
                totalPrice += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount();
                priceWithoutDiscount += selectedProduct.getProduct().getPrice(seller) * selectedProduct.getCount();
            }
        }
        Message message = new Message("discount");
        message.addToObjects(priceWithoutDiscount - totalPrice);
        return message;
    }

    private Sale getSaleForProductOfSeller(Product product, Seller seller) {
        for (Sale sale : seller.getSales()) {
            if (sale.hasProduct(product))
                return sale;
        }
        return null;
    }

    private Sale getSaleForProduct(SelectedProduct selectedProduct) {
        for (Sale sale : selectedProduct.getSeller().getSales()) {
            if (sale.hasProduct(selectedProduct.getProduct()))
                return sale;
        }
        return null;
    }

    public Message viewCart(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("cart");
        message.addToObjects(currentBuyer.getCart());
        return message;
    }

    public Message getProductById(String id, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("get product");
        try {
            message.addToObjects((currentBuyer.getCart()).getProductById(id));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message increaseProduct(String id, int number, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("increase product");
        try {
            currentBuyer.getCart().increaseProduct(id, number);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message decreaseProduct(String id, int number, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("decrease product");
        try {
            currentBuyer.getCart().decreaseProduct(id, number);
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    public Message getCredit(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Message message = new Message("get credit");
        message.addToObjects(currentBuyer.getCredit());
        return message;
    }

    @Override
    protected void setMethods() {
        // todo add method names
    }

    public static class buyerHasNotBought extends Exception {
        public buyerHasNotBought() {
            super("Buyer has not bought product");
        }
    }

    @Override
    public Message getAccountInfo(AuthToken authToken) {
        Message message = new Message("account info");
        try {
            message.addToObjects(Account.getAccountWithUsername(authToken.getUsername()));
        } catch (Exception e) {
            message = new Message("Error");
            message.addToObjects(e);
        }
        return message;
    }

    @Override
    public Message editField(String field, String context, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        switch (field) {
            case "name":
                currentBuyer.changeStateEdited(context, currentBuyer.getLastName(), currentBuyer.getEmail(),
                        currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "lastName":
                currentBuyer.changeStateEdited(currentBuyer.getName(), context, currentBuyer.getEmail(),
                        currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "email":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), context,
                        currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "phoneNumber":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(),
                        currentBuyer.getEmail(), context, currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "password":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(),
                        currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), context, currentBuyer.getCredit());
                break;
            case "credit":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(),
                        currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(),
                        Double.parseDouble(context));
                break;
            default:
                Message message = new Message("edit request sent");
                message.addToObjects(new ManagerController.fieldIsInvalidException());
                return message;
        }
        Manager.addRequest(currentBuyer);
        return new Message("edit request sent");
    }

    public Message setProfileImage(String path, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        currentBuyer.setImagePath(path);
        return new Message("set profile image was successful");
    }

    @Override
    public Message changeMainImage(Image image, AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        currentBuyer.getGraphicPackage().setMainImage(image);
        return new Message("change main image");
    }

    @Override
    public Message logout(AuthToken authToken) {
        Buyer currentBuyer = (Buyer) Main.getAccountWithToken(authToken);
        Main.removeFromTokenHashMap(authToken, currentBuyer);
        return new Message("logout was successful");
    }

    //todo complete connect to supporter
    public void connectToSupporter(String supporterUsername) throws Exception {
        Supporter supporter = (Supporter) Account.getAccountWithUsername(supporterUsername);

    }

    public void sendMessageToSupporter(String message) {

    }

    public String showSupporterMessage() {

        return "";
    }

}
