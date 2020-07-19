package client.view.graphic.fxml.accountMenus.seller;

import client.controller.MediaController;
import client.controller.account.user.seller.SellerController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import client.model.account.Manager;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateProductsFxml {
    public TextField productName;
    public TextField productPrice;
    public TextField productCompany;
    public TextArea numerical;
    public TextArea optional;
    public Button create;
    public TextField count;
    public String path;
    public ImageView productImg;

    MediaController mediaController = ProgramApplication.getMediaController();

    public void createProductButton() throws Exception{
        String name = productName.getText();
        String price = productPrice.getText();
        String company = productCompany.getText();
        String countP = count.getText();
        //numerical fields
        String numericalF = numerical.getText();
        HashMap<String, Double> hashMapNF = new HashMap<>();
        String[] nf = numericalF.split("\\n");
        if (!nf[0].equals("")) {
            for (int i = 0; i < nf.length; i++) {
                String[] nFields = nf[i].split("\\s+");
                hashMapNF.put(nFields[0], Double.parseDouble(nFields[2]));
            }
        }
        //optional fields
        String optionalF = optional.getText();
        HashMap<String, ArrayList<String>> hashMapOF = new HashMap<>();
        String[] of = optionalF.split("\\n");
        if(!of[0].equals("")) {
            for (int i = 0; i < of.length; i++) {
                String[] oFields = of[i].split("\\s+");
                ArrayList<String> options = new ArrayList<>();
                for (int j = 2; j < oFields.length; j++) {
                    options.add(oFields[j]);
                }
                hashMapOF.put(oFields[0], options);
            }
        }
        //create details array list
        ArrayList<String> details = new ArrayList<>();
        details.add(name);
        details.add(company);
        details.add(countP);
        details.add(price);
        //create product
        if (name.equals("") || price.equals("") || countP.equals("") || company.equals("")) {
            new AlertController().create(AlertType.ERROR, "Please fill the blanks");
            return;
        }
        SellerController.getInstance().createProduct(details,hashMapNF,hashMapOF,path);
        resetMenu();
    }

    private void resetMenu() {
        productName.setText("");
        productPrice.setText("");
        productCompany.setText("");
        numerical.setText("");
        optional.setText("");
        productImg.setImage(null);
        count.setText("");
        path = null;
    }

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) throws Exception{
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream((files.get(0))));
        productImg.setImage(img);
        File outputFile = new File("src/main/resources/Images/product" + Manager.getNumberOfRequests() + ".png");
        path = "product" + Manager.getNumberOfRequests() + ".png";
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
