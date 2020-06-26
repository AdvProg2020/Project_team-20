package view.graphic.popUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.product.Sale;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class PopUpControllerFxml implements Initializable {
    public Text offPercentageTxt;
    public Text productsTxt;
    static Stage mainStage;

    public void create() {
        mainStage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/java/view/graphic/popUp/popUpFxml.fxml").toURI().toURL());
            Scene alertScene = new Scene(root, 600, 400);
            mainStage.setScene(alertScene);
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleClose(ActionEvent actionEvent) {
        mainStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Sale> sales = Sale.getAllSales();
        int rand = ThreadLocalRandom.current().nextInt(0, sales.size());
        Sale sale = sales.get(rand);
        offPercentageTxt.setText(sale.getSalePercentage() * 100 + "%");
        StringBuilder products = new StringBuilder();
        int size = sale.getProducts().size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1)
                products.append(sale.getProducts().get(i).getName()).append(", ");
            else
                products.append(sale.getProducts().get(i).getName());
        }
        productsTxt.setText(products.toString());
    }
}
