package controller.account.user;

import controller.MainController;
import model.account.Account;
import model.account.Buyer;
import model.account.Manager;
import model.product.*;

import java.util.ArrayList;

public class BuyerController implements AccountController {
    MainController mainController;
    Buyer currentBuyer;

    public BuyerController() {
        this.mainController = MainController.getInstance();
        currentBuyer = (Buyer)mainController.getAccount();
    }

    public void purchase(String address, String phoneNumber, String discountCode) throws Exception{
        receiveInformation(address, phoneNumber);
        double totalPrice = getTotalPrice();
        if (Discount.validDiscountCodeBuyer(currentBuyer, discountCode)) {
            totalPrice *= Discount.getDiscountByDiscountCode(discountCode).getDiscountPercentage();
            Discount.decreaseDiscountCodeUsageBuyer(currentBuyer, discountCode);
        }
        pay(totalPrice);
    }

    private void pay(double totalPrice) {

    }

    private void receiveInformation(String address, String phoneNumber) {
        currentBuyer.setAddress(address);
        currentBuyer.setPhoneNumber(phoneNumber);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        ArrayList<Sale> salesForProduct;
        for (SelectedProduct selectedProduct:currentBuyer.getCart().getSelectedProducts()) {
            salesForProduct = getSalesForProduct(selectedProduct);
            for (Sale sale:salesForProduct) {
                if (sale.validSaleTime())
                    totalPrice += selectedProduct.getProduct().getPrice()*sale.getSalePercentage();
                else
                    totalPrice += selectedProduct.getProduct().getPrice();
            }
        }
        return totalPrice;
    }

    private ArrayList<Sale> getSalesForProduct(SelectedProduct selectedProduct) {
        ArrayList<Sale> salesForProduct = new ArrayList<>();
        for (Sale sale:selectedProduct.getSeller().getSales()) {
            if (sale.hasProduct(selectedProduct.getProduct()))
                salesForProduct.add(sale);
        }
        return salesForProduct;
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
