package client.view.graphic.fxml.accountMenus.manager.discount;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import client.controller.MediaController;
import client.controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import client.model.account.Account;
import client.model.account.Buyer;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateDiscountController implements Initializable {

    public TextField maxNumberOfUsage;
    public TableColumn<String, Buyer> username;
    public TableView<Buyer> table;
    public TextField percent;
    public TableView<Buyer> chosenTable;
    public TableColumn<String, Buyer> chosenUsers;
    public JFXDatePicker startDatePicker;
    public JFXTimePicker startTimePicker;
    public JFXDatePicker endDatePicker;
    public JFXTimePicker endTimePicker;
    ManagerController managerController = ManagerController.getInstance();
    public ArrayList<Buyer> buyers;

    MediaController mediaController = ProgramApplication.getMediaController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyers = new ArrayList<>();
        table.getItems().setAll(Account.getAllBuyers());
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void selectBuyer(MouseEvent mouseEvent) throws Exception {
        Buyer buyer = table.getSelectionModel().getSelectedItem();
        if (!buyers.contains(buyer))
            buyers.add(buyer);
        chosenTable.getItems().removeAll();
        chosenTable.getItems().setAll(buyers);
        chosenUsers.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void create(ActionEvent actionEvent) throws Exception {
        LocalTime startTime = startTimePicker.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String percentString = percent.getText(), maxNumberOfUsageString = maxNumberOfUsage.getText();
        if (startDate == null || startTime == null || endDate == null || endTime == null || percentString.isEmpty() ||
                maxNumberOfUsageString.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            return;
        }
        double percentageDouble;
        int numberOfUsages;
        try {
            percentageDouble = Double.parseDouble(percentString);
            numberOfUsages = Integer.parseInt(maxNumberOfUsageString);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, "percentage and maxUsages are number!");
            return;
        }
        LocalDateTime startLocalDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endLocalDateTime = LocalDateTime.of(endDate, endTime);
        ArrayList<String> userNames = new ArrayList<>();
        for (Buyer buyer : buyers) {
            userNames.add(buyer.getUsername());
        }
        try {
            managerController.createDiscountCode(startLocalDateTime, endLocalDateTime, percentageDouble, numberOfUsages, userNames);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
            return;
        }
        new AlertController().create(AlertType.CONFIRMATION, "discount created!");
        clear();
    }

    private void clear() {
        maxNumberOfUsage.setText("");
        percent.setText("");
        startDatePicker.setValue(null);
        startTimePicker.setValue(null);
        endDatePicker.setValue(null);
        endTimePicker.setValue(null);
        chosenTable.getItems().clear();
    }
}
