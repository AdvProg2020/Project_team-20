package controller.account.user;

import controller.MainController;
import controller.PreProcess;
import javafx.scene.image.Image;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.*;
import model.product.comment.Score;
import model.receipt.BuyerReceipt;
import model.receipt.SellerReceipt;

import java.util.ArrayList;

public class BuyerController implements AccountController {
    private MainController mainController;
    private static Buyer currentBuyer;

    private static BuyerController buyerController = null;

    private BuyerController() {
        this.mainController = MainController.getInstance();
    }

    public static BuyerController getInstance() {
        if (buyerController == null)
            buyerController = new BuyerController();
        currentBuyer = (Buyer) MainController.getInstance().getAccount();
        return buyerController;
    }

    public ArrayList<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public ArrayList<Discount> getAllDiscounts() {
        PreProcess preProcess = new PreProcess();
        preProcess.setBuyerController(buyerController);
        preProcess.purchaseGift();
        return Discount.getAllDiscountsBuyer(currentBuyer);
    }

    public ArrayList<BuyerReceipt> viewOrders() {
        return currentBuyer.getPurchaseHistory();
    }

    public void rate(String productId, double score) throws Exception {
        if (currentBuyer.hasBoughtProduct(productId)) {
            Product product = Product.getProductById(productId);
            product.addScore(new Score(currentBuyer, score, product));
        } else
            throw new buyerHasNotBought();
    }

    public BuyerReceipt getBuyerReceiptById(String id) throws Exception {
        return currentBuyer.getReceiptById(id);
    }

    private void addBuyerToProductsBuyers() {
        for (Product product : currentBuyer.getCart().getAllProducts().keySet()) {
            product.addBuyer(currentBuyer);
        }
    }

    public void purchase(String address, String phoneNumber, String discountCode) throws Exception {
        receiveInformation(address, phoneNumber);
        double discountPercentage = 0;
        double totalPrice = getTotalPrice();
        if (!discountCode.equals("") && Discount.validDiscountCodeBuyer(currentBuyer, Integer.parseInt(discountCode))) {
            discountPercentage = Discount.getDiscountByDiscountCode(Integer.parseInt(discountCode))
                    .getDiscountPercentage();
            if(Discount.decreaseDiscountCodeUsageBuyer(currentBuyer, Integer.parseInt(discountCode))==0){
                System.out.println(discountPercentage);
                totalPrice *= (1 - discountPercentage);
            }
        }
        pay(totalPrice);
        decreaseAllProductBought();
        makeReceipt(totalPrice, discountPercentage);
        addBuyerToProductsBuyers();
        currentBuyer.getCart().resetCart();
    }

    private void decreaseAllProductBought() {
        for (SelectedProduct selectedProduct : currentBuyer.getCart().getSelectedProducts()) {
            decreaseProductSeller(selectedProduct.getProduct(), selectedProduct.getCount(), selectedProduct.getSeller());
        }
    }

    private void decreaseProductSeller(Product product, int number, Seller seller) {
        seller.decreaseProduct(product, number);
        product.decreaseCountSeller(seller, number);
    }

    private void makeReceipt(double totalPrice, double discountPercentage) {
        makeBuyerReceipt(totalPrice, discountPercentage);
        makeSellerReceipt(discountPercentage);
    }

    private void makeSellerReceipt(double discountPercentage) {
        Cart cart = currentBuyer.getCart();
        ArrayList<Seller> sellers = cart.getAllSellers();
        for (Seller seller : sellers) {
            seller.addToSaleHistory(new SellerReceipt(Integer.toString(SellerReceipt.getSellerReceiptCount()),
                    discountPercentage, cart.getAllProductsSeller(seller),
                    false, getTotalPriceTotalDiscountSeller(seller, 0), currentBuyer,
                    getTotalPriceTotalDiscountSeller(seller, 1)));
        }
    }

    private double getTotalPriceTotalDiscountSeller(Seller seller, int type) {
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

    private void makeBuyerReceipt(double totalPrice, double discountPercentage) {
        Cart cart = currentBuyer.getCart();
        currentBuyer.addReceipt(new BuyerReceipt(Integer.toString(BuyerReceipt.getBuyerReceiptCount()),
                discountPercentage, cart.getAllProducts(), false, totalPrice, cart.getAllSellers()));
    }

    private void pay(double totalPrice) throws Exception {
        currentBuyer.decreaseCredit(totalPrice);
        for (Seller seller : currentBuyer.getCart().getAllSellers()) {
            seller.increaseCredit(getTotalPriceTotalDiscountSeller(seller, 0));
        }
    }

    private void receiveInformation(String address, String phoneNumber) {
        currentBuyer.setAddress(address);
        currentBuyer.setPhoneNumber(phoneNumber);
    }

    public double getTotalPrice() {
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
        return totalPrice;
    }

    public double getDiscount() {
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
        return priceWithoutDiscount - totalPrice;
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

    public Cart viewCart() {
        return currentBuyer.getCart();
    }

    public Product getProductById(String id) throws Exception {
        return (currentBuyer.getCart()).getProductById(id);
    }

    public void increaseProduct(String id, int number) throws Exception {
        currentBuyer.getCart().increaseProduct(id, number);
    }

    public void decreaseProduct(String id, int number) throws Exception {
        currentBuyer.getCart().decreaseProduct(id, number);
    }

    public double getCredit() {
        return currentBuyer.getCredit();
    }

    public Buyer getCurrentBuyer() {
        return currentBuyer;
    }

    public static class buyerHasNotBought extends Exception {
        public buyerHasNotBought() {
            super("Buyer has not bought product");
        }
    }

    @Override
    public Account getAccountInfo() {
        return currentBuyer;
    }

    @Override
    public void editField(String field, String context) throws Exception {
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
                throw new ManagerController.fieldIsInvalidException();
        }
        Manager.addRequest(currentBuyer);
    }

    public void setProfileImage(String path) {
        currentBuyer.setImagePath(path);
    }

    @Override
    public void changeMainImage(Image image) {
        currentBuyer.getGraphicPackage().setMainImage(image);
    }

    @Override
    public void logout() {
        mainController.logout();
    }

}
