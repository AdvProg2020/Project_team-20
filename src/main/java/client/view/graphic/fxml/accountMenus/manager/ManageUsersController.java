package client.view.graphic.fxml.accountMenus.manager;

import com.jfoenix.controls.JFXButton;
import client.controller.MediaController;
import client.controller.account.user.ManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import client.model.account.Account;
import client.model.account.AccountType;
import client.view.graphic.ProgramApplication;
import client.view.graphic.alert.AlertController;
import client.view.graphic.alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {
    public TableView<Account> table;
    public ImageView imageView;
    public TextField newUsername;
    public TextField newName;
    public TableColumn<AccountType, Account> type;
    public TableColumn<String, Account> username;
    public TextField newPhoneNumber;
    public TextArea message;
    public TextField newCredit;
    public TextField newPassword;
    public TextField newLastName;
    public TextField newEmail;
    public Button deleteUser;
    public JFXButton createManager;
    public Text title;
    public JFXButton managerText;
    public Button signUpButton;
    public String createState="Manager";

    private Account account;
    ManagerController managerController = ManagerController.getInstance();
    MediaController mediaController = ProgramApplication.getMediaController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeCreatePanel();
        table.getItems().setAll(managerController.manageUsers());
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        type.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        removeManageUsersPanel();
        table.setOpacity(1);
    }

    private void removeCreatePanel() {
        imageView.setOpacity(0);
        newCredit.setOpacity(0);
        newEmail.setOpacity(0);
        newLastName.setOpacity(0);
        newName.setOpacity(0);
        newPassword.setOpacity(0);
        newPhoneNumber.setOpacity(0);
        newUsername.setOpacity(0);
        managerText.setOpacity(0);
        signUpButton.setOpacity(0);
    }

    private void showCreatePanel() {
        imageView.setOpacity(1);
        newCredit.setOpacity(1);
        newEmail.setOpacity(1);
        newLastName.setOpacity(1);
        newName.setOpacity(1);
        newPassword.setOpacity(1);
        newPhoneNumber.setOpacity(1);
        newUsername.setOpacity(1);
        managerText.setOpacity(1);
        signUpButton.setOpacity(1);
    }

    private void showManageUsersPanel() {
        table.setOpacity(1);
        table.getItems().setAll(managerController.manageUsers());
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        type.setCellValueFactory(new PropertyValueFactory<>("accountType"));
    }

    private void removeManageUsersPanel() {
        table.setOpacity(0);
        message.setOpacity(0);
        deleteUser.setOpacity(0);
        title.setOpacity(0);
    }

    public void handleSignUp(ActionEvent actionEvent) throws Exception {
        String username = newUsername.getText(), name = newName.getText(), password = newPassword.getText(),
                lastName = newLastName.getText(), email = newEmail.getText(), creditString = newCredit.getText(),
                phoneNumber = newPhoneNumber.getText();
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || creditString.isEmpty() || phoneNumber.isEmpty()) {
            try {
                new AlertController().create(AlertType.ERROR, "please fill all of the boxes");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            if (createState.equals("Manager")) {
                managerController.createManagerProfile(name, lastName, email, phoneNumber, username, password, creditString);
                new AlertController().create(AlertType.CONFIRMATION, "create manager was successful");
            } else {
                managerController.createSupporterProfile(name, lastName, email, phoneNumber, username, password);
                new AlertController().create(AlertType.CONFIRMATION, "create supporter was successful");
            }
            removeCreatePanel();
            showManageUsersPanel();
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void selectUser(MouseEvent mouseEvent) throws Exception {
        account = table.getSelectionModel().getSelectedItem();
        message.setText(account.toString());
        title.setOpacity(1);
        message.setOpacity(0.7);
        deleteUser.setOpacity(1);
    }


    public void createManager(ActionEvent actionEvent) throws Exception {
        removeManageUsersPanel();
        showCreatePanel();
    }

    public void deleteUser(ActionEvent actionEvent) throws Exception {
        try {
            managerController.deleteUser(account.getUsername());
            title.setOpacity(0);
            message.setOpacity(0);
            deleteUser.setOpacity(0);
            table.getItems().setAll(managerController.manageUsers());
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            type.setCellValueFactory(new PropertyValueFactory<>("accountType"));
            new AlertController().create(AlertType.CONFIRMATION, "delete user was successful");
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void changeCreateState(ActionEvent actionEvent) {
        if (createState.equals("Manager")) {
            createState = "Supporter";
            managerText.setText("Supporter");
        } else {
            createState = "Manager";
            managerText.setText("Manager");
        }
    }
}
