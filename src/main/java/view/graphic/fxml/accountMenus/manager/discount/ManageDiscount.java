package view.graphic.fxml.accountMenus.manager.discount;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.product.Discount;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ManageDiscount implements Initializable {
    static boolean leave = false;

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
    public Button removeButton;
    public Button editButton;

    ManagerController managerController = ManagerController.getInstance();
    Discount discount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getItems().setAll(managerController.viewDiscountCodes());
        discountCode.setCellValueFactory(new PropertyValueFactory<>("discountCode"));
    }

    public void selectDiscount(MouseEvent mouseEvent) throws Exception {
        discount = table.getSelectionModel().getSelectedItem();
        message.setOpacity(0.7);
        title.setOpacity(1);
        removeButton.setOpacity(1);
        editButton.setOpacity(1);
        message.setText(discount.toString());
    }


    public void handleEnterAccept(MouseEvent mouseEvent) {
    }

    public void handleExitAccept(MouseEvent mouseEvent) {
    }

    public void handleExitRemove(MouseEvent mouseEvent) {
    }

    public void handleRemove(ActionEvent actionEvent) {
        try {
            managerController.removeDiscountCodes(discount.getDiscountCode());
            new AlertController().create(AlertType.CONFIRMATION, "delete was successful");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleEnterRemove(MouseEvent mouseEvent) {
    }

    public void handleOk(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (maxNumberOfUsageOk.equals(source)) {
            editMaxNumber();
        } else if (percentOk.equals(source)) {
            editPercent();
        } else if (startDatePickerOk.equals(source)) {
            editStartDate();
        } else if (startTimePickerOk.equals(source)) {
            editStartTime();
        } else if (endTimePickerOk.equals(source)) {
            editEndDate();
        } else if (endDatePickerOk.equals(source)) {
            editEndTime();
        }
    }

    private void editEndTime() {
        LocalTime time = endTimePicker.getValue();
        if (time == null) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            LocalDate localDate = discount.getEndDate().toLocalDate();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
            managerController.editEndDateOfDiscount(discount.getDiscountCode(), localDateTime);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editEndDate() {
        LocalDate date = endDatePicker.getValue();
        if (date == null) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            LocalTime localTime = discount.getEndDate().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
            managerController.editEndDateOfDiscount(discount.getDiscountCode(), localDateTime);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editStartDate() {
        LocalTime time = endTimePicker.getValue();
        if (time == null) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            LocalDate localDate = discount.getEndDate().toLocalDate();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
            managerController.editStartDateOfDiscountCode(discount.getDiscountCode(), localDateTime);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editStartTime() {
        LocalTime time = endTimePicker.getValue();
        if (time == null) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            LocalDate localDate = discount.getEndDate().toLocalDate();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, time);
            managerController.editStartDateOfDiscountCode(discount.getDiscountCode(), localDateTime);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editPercent() {
        String percentText = percent.getText();
        if (percentText.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editDiscountPercentage(discount.getDiscountCode(), percentText);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editMaxNumber() {
        String maxNumberOfUsageText = maxNumberOfUsage.getText();
        if (maxNumberOfUsageText.isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editMaxDiscountUsage(discount.getDiscountCode(), maxNumberOfUsageText);
            new AlertController().create(AlertType.CONFIRMATION, "successful");
            message.setText(discount.toString());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void clear() {
        maxNumberOfUsage.setText("");
        percent.setText("");
        startDatePicker.setValue(null);
        startTimePicker.setValue(null);
        endDatePicker.setValue(null);
        endTimePicker.setValue(null);
    }

    public void handleEdit(ActionEvent actionEvent) {
        Button button = ((Button) actionEvent.getSource());
        if (!leave) {
            button.setStyle("-fx-background-color: #ff826f;");
            button.setText("Leave");
            leave = true;
            showEditPanel();
        } else {
            button.setStyle("-fx-background-color: #009f9c;");
            button.setText("Edit");
            leave = false;
            clear();
            removeEditPanel();
        }
    }

    private void removeEditPanel() {
        percent.setOpacity(0);
        maxNumberOfUsage.setOpacity(0);
        startTimePicker.setOpacity(0);
        startDatePicker.setOpacity(0);
        endTimePicker.setOpacity(0);
        endDatePicker.setOpacity(0);
        percentOk.setOpacity(0);
        maxNumberOfUsageOk.setOpacity(0);
        startTimePickerOk.setOpacity(0);
        startDatePickerOk.setOpacity(0);
        endTimePickerOk.setOpacity(0);
        endDatePickerOk.setOpacity(0);
    }

    private void showEditPanel() {
        maxNumberOfUsage.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        percent.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        endTimePicker.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        endDatePicker.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        startTimePicker.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        startDatePicker.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        percent.setOpacity(0.71);
        maxNumberOfUsage.setOpacity(0.71);
        endDatePicker.setOpacity(0.71);
        endTimePicker.setOpacity(0.71);
        startDatePicker.setOpacity(0.71);
        startTimePicker.setOpacity(0.71);
        startTimePickerOk.setOpacity(0.71);
        startDatePickerOk.setOpacity(0.71);
        percentOk.setOpacity(0.71);
        maxNumberOfUsageOk.setOpacity(0.71);
        endTimePickerOk.setOpacity(0.71);
        endDatePickerOk.setOpacity(0.71);
    }


    public void handleEnter(MouseEvent event) {
        if (!leave)
            ((Button) event.getSource()).setStyle("-fx-background-color: #00bfbc;");
        else
            ((Button) event.getSource()).setStyle("-fx-background-color: #dd7160;");
    }

    public void handleExit(MouseEvent event) {
        if (!leave)
            ((Button) event.getSource()).setStyle("-fx-background-color: #009f9c;");
        else
            ((Button) event.getSource()).setStyle("-fx-background-color: #ff826f;");
    }

    public void handleEnterOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #4caf50;");
    }

    public void handleExitOk(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #60d520;");
    }

}
