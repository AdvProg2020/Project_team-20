package view.graphic.fxml.accountMenus;

import controller.account.user.BuyerController;
import controller.account.user.ManagerController;
import controller.account.user.SellerController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.account.Account;
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



    public void handleOk(ActionEvent actionEvent, Account account) throws Exception {
        Object source = actionEvent.getSource();
        if (gmailOk.equals(source)) {
            editGmail(account);
        } else if (phoneOk.equals(source)) {
            editPhoneNumber(account);
        } else if (nameOk.equals(source)) {
            editName(account);
        } else if (lastNameOk.equals(source)) {
            editLastName(account);
        } else if (passwordOk.equals(source)) {
            editPassword(account);
        }
    }

    private void editPassword(Account account) throws Exception {
        if (passwordEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            switch (account.getAccountType()) {
                case SELLER:
                    SellerController.getInstance().editField("password", passwordEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
                    break;
                case MANAGER:
                    ManagerController.getInstance().editField("password", passwordEdit.getText());
                    password.setText(passwordEdit.getText());
                    break;
                case BUYER:
                    BuyerController.getInstance().editField("password", passwordEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
            }
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editLastName(Account account) throws Exception {
        if (lastNameEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            switch (account.getAccountType()) {
                case BUYER:
                    BuyerController.getInstance().editField("lastName", lastNameEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
                    break;
                case MANAGER:
                    ManagerController.getInstance().editField("lastName", lastNameEdit.getText());
                    lastname.setText(lastNameEdit.getText());
                    break;
                case SELLER:
                    SellerController.getInstance().editField("lastName", lastNameEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
            }

        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editName(Account account) throws Exception {
        if (nameEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            switch (account.getAccountType()) {
                case SELLER:
                    SellerController.getInstance().editField("name", nameEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
                    break;
                case MANAGER:
                    ManagerController.getInstance().editField("name", nameEdit.getText());
                    name.setText(nameEdit.getText());
                    break;
                case BUYER:
                    BuyerController.getInstance().editField("name", nameEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
            }
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editPhoneNumber(Account account) throws Exception {
        if (phoneNumberEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            switch (account.getAccountType()) {
                case BUYER:
                    BuyerController.getInstance().editField("phoneNumber", phoneNumberEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
                    break;
                case MANAGER:
                    ManagerController.getInstance().editField("phoneNumber", phoneNumberEdit.getText());
                    phone.setText(phoneNumberEdit.getText());
                    break;
                case SELLER:
                    SellerController.getInstance().editField("phoneNumber", phoneNumberEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
            }

        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    private void editGmail(Account account) throws Exception {
        if (gmailEdit.getText().isEmpty()) {
            new AlertController().create(AlertType.ERROR, "field is empty");
            return;
        }
        try {
            switch (account.getAccountType()) {
                case SELLER:
                    SellerController.getInstance().editField("email", gmailEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
                    break;
                case MANAGER:
                    ManagerController.getInstance().editField("email", gmailEdit.getText());
                    gmail.setText(gmailEdit.getText());
                    break;
                case BUYER:
                    BuyerController.getInstance().editField("email", gmailEdit.getText());
                    new AlertController().create(AlertType.CONFIRMATION, "request sent");
            }

        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }
}
