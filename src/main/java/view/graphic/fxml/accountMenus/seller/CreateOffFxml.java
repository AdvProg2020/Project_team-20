package view.graphic.fxml.accountMenus.seller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.MediaController;
import controller.account.user.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.account.Seller;
import model.product.Product;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateOffFxml implements Initializable {
    public JFXDatePicker startDatePicker;
    public JFXTimePicker startTimePicker;
    public JFXDatePicker endDatePicker;
    public JFXTimePicker endTimePicker;
    public JFXTextField percent;
    public TableView<Product> chosenTable;
    public TableColumn<String, Product> chosenProducts;
    public TableView<Product> table;
    public TableColumn<String, Product> productNames;
    public ArrayList<Product> products;

    private SellerController sellerController = SellerController.getInstance();
    private Seller seller = (Seller) sellerController.getAccountInfo();

    MediaController mediaController = ProgramApplication.getMediaController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        products = new ArrayList<>();
        table.getItems().setAll(seller.getProducts());
        productNames.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void create(ActionEvent actionEvent) {
        LocalTime startTime = startTimePicker.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String percentString = percent.getText();

        if (startDate == null || startTime == null || endDate == null || endTime == null || percentString.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        double percentageDouble;
        try {
            percentageDouble = Double.parseDouble(percentString);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, "percentage is a number!");
            return;
        }
        LocalDateTime startLocalDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endLocalDateTime = LocalDateTime.of(endDate, endTime);
        ArrayList<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        try {
            sellerController.addOff(startLocalDateTime, endLocalDateTime, percentageDouble, productIds);
            clear();
            new AlertController().create(AlertType.CONFIRMATION,"Off created!");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void selectRequest(MouseEvent event) {
        Product product = table.getSelectionModel().getSelectedItem();
        if (!products.contains(product))
            products.add(product);
        chosenTable.getItems().removeAll();
        chosenTable.getItems().setAll(products);
        chosenProducts.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void clear() {
        percent.setText("");
        startDatePicker.setValue(null);
        startTimePicker.setValue(null);
        endDatePicker.setValue(null);
        endTimePicker.setValue(null);
        chosenTable.getItems().clear();
    }
}
