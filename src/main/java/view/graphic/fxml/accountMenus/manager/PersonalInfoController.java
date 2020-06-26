package view.graphic.fxml.accountMenus.manager;

import controller.MediaController;
import controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.account.Manager;
import view.graphic.ProgramApplication;
import view.graphic.fxml.accountMenus.MotherPersonalInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfoController extends MotherPersonalInfo implements Initializable {

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
    public TextField passwordEdit;
    public Button gmailOk;
    public Button phoneOk;
    public Button nameOk;
    public Button lastNameOk;
    public Button passwordOk;
    public Button creditOk;
    public TextField gmailEdit;
    public TextField creditEdit;
    public TextField credit;


    ManagerController managerController = ManagerController.getInstance();
    MediaController mediaController = ProgramApplication.getMediaController();
    Manager manager = (Manager) managerController.getAccountInfo();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.appendText(manager.getName());
        lastname.appendText(manager.getLastName());
        gmail.appendText(manager.getEmail());
        phone.appendText(manager.getPhoneNumber());
        username.appendText(manager.getUsername());
        password.appendText(manager.getPassword());
        credit.appendText(String.valueOf(manager.getCredit()));
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

    private void clear() {
        passwordEdit.setText("");
        nameEdit.setText("");
        lastNameEdit.setText("");
        phoneNumberEdit.setText("");
        gmailEdit.setText("");
        creditEdit.setText("");
    }

    public void removeEditPanel() {
        gmailEdit.setOpacity(0);
        phoneNumberEdit.setOpacity(0);
        nameEdit.setOpacity(0);
        lastNameEdit.setOpacity(0);
        creditEdit.setOpacity(0);
        passwordEdit.setOpacity(0);
        gmailOk.setOpacity(0);
        lastNameOk.setOpacity(0);
        nameOk.setOpacity(0);
        passwordOk.setOpacity(0);
        phoneOk.setOpacity(0);
        creditOk.setOpacity(0);
    }

    public void showEditPanel() {
        gmailEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        phoneNumberEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        nameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        lastNameEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        creditEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        passwordEdit.setStyle("-fx-text-inner-color: white; -fx-background-color: #45546e;");
        gmailEdit.setOpacity(0.71);
        phoneNumberEdit.setOpacity(0.71);
        nameEdit.setOpacity(0.71);
        lastNameEdit.setOpacity(0.71);
        creditEdit.setOpacity(0.71);
        passwordEdit.setOpacity(0.71);
        gmailOk.setOpacity(0.71);
        lastNameOk.setOpacity(0.71);
        nameOk.setOpacity(0.71);
        passwordOk.setOpacity(0.71);
        phoneOk.setOpacity(0.71);
        creditOk.setOpacity(0.71);
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

    public void handleOk(ActionEvent actionEvent) throws Exception {
        super.handleOk(actionEvent, manager);
    }
}
