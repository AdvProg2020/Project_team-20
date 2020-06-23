package view.graphic.fxml.accountMenus.buyer;

import controller.account.user.BuyerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import model.product.Discount;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiscountCodesFxml implements Initializable {

    public TextArea textArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BuyerController buyerController = BuyerController.getInstance();
        ArrayList<Discount> discounts = buyerController.getAllDiscounts();
        for(Discount discount : discounts){
            String line = "discount code: "+discount.getDiscountCode()+"\n"+"discount percentage: "+discount.getDiscountPercentage()+"\n" + "\n";
            textArea.appendText(line);
        }
    }
}
