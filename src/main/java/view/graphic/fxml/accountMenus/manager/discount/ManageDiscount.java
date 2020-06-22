package view.graphic.fxml.accountMenus.manager.discount;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.Discount;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageDiscount implements Initializable {
    public TextArea message;
    public Text title;
    public JFXTimePicker endTimePicker;
    public JFXDatePicker endDatePicker;
    public JFXTimePicker startTimePicker;
    public JFXDatePicker startDatePicker;
    public JFXTextField percent;
    public JFXTextField maxNumberOfUsage;
    public TableColumn<Object, Object> discountCode;
    public TableView<Discount> table;
    public Button endTimePickerOk;
    public Button endDatePickerOk;
    public Button startTimePickerOk;
    public Button startDatePickerOk;
    public Button percentOk;
    public Button maxNumberOfUsageOk;

    ManagerController managerController = ManagerController.getInstance();
    Discount discount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(managerController.viewDiscountCodes());
        discountCode.setCellValueFactory(new PropertyValueFactory<>("discountCode"));
    }


    public void handleEnterAccept(MouseEvent mouseEvent) {
    }

    public void handleExitAccept(MouseEvent mouseEvent) {
    }

    public void handleEdit(ActionEvent actionEvent) {
    }

    public void handleExitRemove(MouseEvent mouseEvent) {
    }

    public void handleRemove(ActionEvent actionEvent) {
    }

    public void handleEnterRemove(MouseEvent mouseEvent) {
    }

    public void selectCategory(MouseEvent mouseEvent) {
    }

    public void handleEnterOk(MouseEvent mouseEvent) {
    }

    public void handleExitOk(MouseEvent mouseEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
    }

    private void clear() {
        maxNumberOfUsage.setText("");
        percent.setText("");
        startDatePicker.setValue(null);
        startTimePicker.setValue(null);
        endDatePicker.setValue(null);
        endTimePicker.setValue(null);
    }
}
