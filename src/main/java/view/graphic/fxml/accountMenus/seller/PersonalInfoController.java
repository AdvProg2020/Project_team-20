package view.graphic.fxml.accountMenus.seller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PersonalInfoController {
    public TextField gmail;
    public TextField phone;
    public TextField username;
    public TextField password;
    public TextField name;
    public TextField lastname;
    public Button gmailOk;
    public Button phoneOk;
    public Button nameOk;
    public Button lastNameOk;
    public Button usernameOk;
    public Button passwordOk;
    public TextField gmailEdit;
    public TextField phoneNumberEdit;
    public TextField nameEdit;
    public TextField lastNameEdit;
    public TextField usernameEdit;
    public TextField passwordEdit;
    public TextField companyInfo;
    public TextField companyInfoEdit;
    public Button companyInfoOk;
    static boolean leave = false;

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
            removeEditPanel();
        }
    }

    public void removeEditPanel() {
        gmailEdit.setOpacity(0);
        phoneNumberEdit.setOpacity(0);
        nameEdit.setOpacity(0);
        lastNameEdit.setOpacity(0);
        usernameEdit.setOpacity(0);
        passwordEdit.setOpacity(0);
        companyInfoEdit.setOpacity(0);
        gmailOk.setOpacity(0);
        lastNameOk.setOpacity(0);
        nameOk.setOpacity(0);
        passwordOk.setOpacity(0);
        phoneOk.setOpacity(0);
        usernameOk.setOpacity(0);
        companyInfoOk.setOpacity(0);
    }

    public void showEditPanel() {
        gmailEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        phoneNumberEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        nameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        lastNameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        usernameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        passwordEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        companyInfoEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        gmailEdit.setOpacity(0.71);
        phoneNumberEdit.setOpacity(0.71);
        nameEdit.setOpacity(0.71);
        lastNameEdit.setOpacity(0.71);
        usernameEdit.setOpacity(0.71);
        passwordEdit.setOpacity(0.71);
        companyInfoEdit.setOpacity(0.71);
        gmailOk.setOpacity(0.71);
        lastNameOk.setOpacity(0.71);
        nameOk.setOpacity(0.71);
        passwordOk.setOpacity(0.71);
        phoneOk.setOpacity(0.71);
        usernameOk.setOpacity(0.71);
        companyInfoOk.setOpacity(0.71);
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

    public void handleOk(ActionEvent actionEvent) {
    }

}
