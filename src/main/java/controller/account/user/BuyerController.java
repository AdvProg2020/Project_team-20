package controller.account.user;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.account.Seller;
import model.product.*;
import model.receipt.BuyerReceipt;
import model.receipt.SellerReceipt;

import java.util.ArrayList;

public class BuyerController implements AccountController {
    MainController mainController;
    Buyer currentBuyer;

    public BuyerController() {
        this.mainController = MainController.getInstance();
        currentBuyer = (Buyer)mainController.getAccount();
    }

    public ArrayList<BuyerReceipt> viewOrders() {
        return currentBuyer.getPurchaseHistory();
    }

    public BuyerReceipt getBuyerReceiptById(String id) {
        return null;
    }

    public void purchase(String address, String phoneNumber, String discountCode) throws Exception{
        receiveInformation(address, phoneNumber);
        double discountPercentage = 0;
        double totalPrice = getTotalPrice();
        if (Discount.validDiscountCodeBuyer(currentBuyer, discountCode)) {
            discountPercentage = Discount.getDiscountByDiscountCode(discountCode).getDiscountPercentage();
            totalPrice *= discountPercentage;
            Discount.decreaseDiscountCodeUsageBuyer(currentBuyer, discountCode);
        }
        pay(totalPrice);
        makeReceipt(totalPrice, discountPercentage);
    }

    private void makeReceipt(double totalPrice, double discountPercentage) {
        makeBuyerReceipt(totalPrice, discountPercentage);
        makeSellerReceipt(discountPercentage);
    }

    private void makeSellerReceipt(double discountPercentage) {
        Cart cart = currentBuyer.getCart();
        ArrayList<Seller> sellers = cart.getAllSellers();
        for (Seller seller:sellers) {
            seller.addToSaleHistory(new SellerReceipt(Integer.toString(SellerReceipt.getSellerReceiptCount()), discountPercentage, cart.getAllProductsOfSeller(seller),
                    false, getTotalPriceTotalDiscountSeller(seller, 0), currentBuyer, getTotalPriceTotalDiscountSeller(seller, 1)));
        }
    }

    private double getTotalPriceTotalDiscountSeller(Seller seller, int type) {
        Cart cart = currentBuyer.getCart();
        double totalPriceSeller = 0;
        double totalDiscount = 0;
        ArrayList<Product> allProductsSeller = cart.getAllProductsOfSeller(seller);
        for (Product product:allProductsSeller) {
            Sale sale = getSaleForProductOfSeller(product, seller);
            if (sale!=null) {
                totalPriceSeller += product.getPrice(seller) * sale.getSalePercentage();
                totalDiscount += product.getPrice(seller) * (1-sale.getSalePercentage());
            }
            else
                totalPriceSeller += product.getPrice(seller);
        }
        if (type==0)
            return totalPriceSeller;
        else
            return totalDiscount;
    }

    private void makeBuyerReceipt(double totalPrice, double discountPercentage) {
        Cart cart = currentBuyer.getCart();
        currentBuyer.addReceipt(new BuyerReceipt(Integer.toString(BuyerReceipt.getBuyerReceiptCount()), discountPercentage, cart.getAllProducts(), false, totalPrice, cart.getAllSellers()));
    }

    private void pay(double totalPrice) throws Exception{
        currentBuyer.decreaseCredit(totalPrice);
    }

    private void receiveInformation(String address, String phoneNumber) {
        currentBuyer.setAddress(address);
        currentBuyer.setPhoneNumber(phoneNumber);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (SelectedProduct selectedProduct:currentBuyer.getCart().getSelectedProducts()) {
            Sale saleForProduct = getSaleForProduct(selectedProduct);
            Seller seller = selectedProduct.getSeller();
            if (saleForProduct!=null && saleForProduct.validSaleTime())
                totalPrice += selectedProduct.getProduct().getPrice(seller)*saleForProduct.getSalePercentage();
            else
                totalPrice += selectedProduct.getProduct().getPrice(seller);
        }
        return totalPrice;
    }

    private Sale getSaleForProductOfSeller(Product product, Seller seller) {
        for (Sale sale:seller.getSales()) {
            if (sale.hasProduct(product))
                return sale;
        }
        return null;
    }

    private Sale getSaleForProduct(SelectedProduct selectedProduct) {
        for (Sale sale:selectedProduct.getSeller().getSales()) {
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

    public void increaseProduct(String id, int number) throws Exception{
        currentBuyer.getCart().increaseProduct(id, number);
    }

    public void decreaseProduct(String id, int number) throws Exception{
        currentBuyer.getCart().decreaseProduct(id, number);
    }

    public void editBuyer(String fieldToChangeName, String editedField) {
        switch (fieldToChangeName) {
            case "name":
                currentBuyer.changeStateEdited(editedField, currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "lastName":
                currentBuyer.changeStateEdited(currentBuyer.getName(), editedField, currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
            case "email":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), editedField, currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "phoneNumber":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), editedField, currentBuyer.getPassword(), currentBuyer.getCredit());
                break;
            case "password":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), editedField, currentBuyer.getCredit());
                break;
            case "credit":
                currentBuyer.changeStateEdited(currentBuyer.getName(), currentBuyer.getLastName(), currentBuyer.getEmail(), currentBuyer.getPhoneNumber(), currentBuyer.getPassword(), Integer.parseInt(editedField));
                break;
        }
        Manager.addRequest(currentBuyer);
    }

    @Override
    public Account getAccountInfo() {
        return null;
    }

    @Override
    public void editField(String field, String context) {

    }

    @Override
    public void logout() {

    }

    private Buyer getInfo() {
        return currentBuyer;
    }
}
