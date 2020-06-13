package view.graphic.fxml.accountMenus.manager.discount;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.account.Account;
import model.account.Buyer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateDiscountController implements Initializable {

    public TextField maxNumberOfUsage;
    public TableColumn<String, Buyer> username;
    public TableView<Buyer> table;
    public TextField percent;
    public TableView<Buyer> chosenTable;
    public TableColumn<String, Buyer> chosenUsers;
    public JFXDatePicker datePicker;
    public JFXTimePicker timePicker;
    ManagerController managerController = ManagerController.getInstance();
    ArrayList<Buyer> buyers = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(Account.getAllBuyers());
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void selectRequest(MouseEvent mouseEvent) throws Exception {
        Buyer buyer = table.getSelectionModel().getSelectedItem();
        if (!buyers.contains(buyer))
            buyers.add(buyer);
        chosenTable.getItems().removeAll();
        chosenTable.getItems().setAll(buyers);
        chosenUsers.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    public void create(ActionEvent actionEvent) {
        //managerController.createDiscountCode();
    }
}
