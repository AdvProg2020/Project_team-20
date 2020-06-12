package view.graphic.fxml.accountMenus;

import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.account.Manager;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;

public abstract class MotherPersonalInfo {
    public TextField name;
    public TextField lastname;
    public TextField gmail;
    public TextField phone;
    public TextField username;
    public TextField password;
    static boolean leave = false;


    public TextField phoneNumberEdit;
    public TextField nameEdit;
    public TextField lastNameEdit;
    public TextField usernameEdit;
    public TextField passwordEdit;
    public Button gmailOk;
    public Button phoneOk;
    public Button nameOk;
    public Button lastNameOk;
    public Button usernameOk;
    public Button passwordOk;
    public TextField gmailEdit;

    ManagerController managerController = ManagerController.getInstance();
    Manager manager = (Manager) managerController.getAccountInfo();


    public void handleOk(ActionEvent actionEvent) throws Exception {
        Object source = actionEvent.getSource();
        if (gmailOk.equals(source)) {
            editGmail();
        } else if (phoneOk.equals(source)) {
            editPhoneNumber();
        } else if (nameOk.equals(source)) {
            editName();
        } else if (lastNameOk.equals(source)) {
            editLastName();
        } else if (passwordOk.equals(source)) {
            editPassword();
        }
    }

    private void editPassword() throws Exception {
        if (passwordEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editField("password", passwordEdit.getText());
            password.setText(passwordEdit.getText());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editLastName() throws Exception {
        if (lastNameEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editField("lastName", lastNameEdit.getText());
            lastname.setText(lastNameEdit.getText());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editName() throws Exception {
        if (nameEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editField("name", nameEdit.getText());
            name.setText(nameEdit.getText());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editPhoneNumber() throws Exception {
        if (phoneNumberEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editField("phoneNumber", phoneNumberEdit.getText());
            phone.setText(phoneNumberEdit.getText());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editGmail() throws Exception {
        if (gmailEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            managerController.editField("email", gmailEdit.getText());
            gmail.setText(gmailEdit.getText());
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }
}
