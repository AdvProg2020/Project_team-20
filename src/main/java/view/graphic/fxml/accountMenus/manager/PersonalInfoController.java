package view.graphic.fxml.accountMenus.manager;

import controller.account.user.ManagerController;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.account.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfoController implements Initializable {

    public TextField name;
    public TextField lastname;
    public TextField gmail;
    public TextField phone;
    public TextField username;
    public TextField password;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ManagerController managerController = ManagerController.getInstance();
        Manager manager = (Manager) managerController.getAccountInfo();
        name.appendText(manager.getName());
        lastname.appendText(manager.getLastName());
        gmail.appendText(manager.getEmail());
        phone.appendText(manager.getPhoneNumber());
        username.appendText(manager.getUsername());
        password.appendText(manager.getPassword());
    }
}
