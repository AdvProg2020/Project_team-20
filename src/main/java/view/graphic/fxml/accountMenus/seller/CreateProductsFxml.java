package view.graphic.fxml.accountMenus.seller;

import controller.account.user.SellerController;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateProductsFxml {
    public TextField productName;
    public TextField productPrice;
    public TextField productCompany;
    public TextArea numerical;
    public TextArea optional;
    public Button create;
    public TextField count;
    public String path;

    public void createProductButton() {
        String name = productName.getText();
        String price = productPrice.getText();
        String company = productCompany.getText();
        String countP = count.getText();
        //numerical fields
        String numericalF = numerical.getText();
        String[] nf = numericalF.split("\\n");
        HashMap<String, Double> hashMapNF = new HashMap<>();
        for (int i = 0; i < nf.length; i++) {
            String[] nFields = nf[i].split("\\s+");
            hashMapNF.put(nFields[0], Double.parseDouble(nFields[2]));
        }
        //optional fields
        String optionalF = optional.getText();
        String[] of = optionalF.split("\\n");
        HashMap<String, ArrayList<String>> hashMapOF = new HashMap<>();
        for (int i = 0; i < of.length; i++) {
            String[] oFields = of[i].split("\\s+");
            ArrayList<String> options = new ArrayList<>();
            for (int j = 2; j < oFields.length; j++) {
                options.add(oFields[j]);
            }
            hashMapOF.put(oFields[0], options);
        }
        //create details array list
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(company);
        details.add(countP);
        details.add(price);
        //create product
        SellerController.getInstance().createProduct(details,hashMapNF,hashMapOF,path);
    }
}
