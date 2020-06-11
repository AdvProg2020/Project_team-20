package view.graphic.fxml.registerAndLoginMenu;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;

public class ManagerLoginController {
    public void handleSignIn() {
        //Controller To DO


        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    public void enterMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #14b5a8; -fx-background-radius: 10");
    }

    public void exitMouse(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-color: #02f5e1; -fx-background-radius: 10");;
    }

}
