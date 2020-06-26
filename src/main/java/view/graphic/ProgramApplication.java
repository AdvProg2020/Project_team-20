package view.graphic;

import controller.MediaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.graphic.fxml.allProductsMenu.FxmlAllProductsMenu;
import view.graphic.fxml.mainMenu.FxmlMainMenu;
import view.graphic.fxml.registerAndLoginMenu.FxmlRegisterAndLoginMenu;
import view.graphic.fxml.registerAndLoginMenu.ManagerLoginController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProgramApplication extends Application {

    private static ArrayList<MenuNames> history = new ArrayList<>();

    private static Scene mainMenu, buyerMenu, managerMenu, registerAndLoginMenu, sellerMenu, allProductsMenu, loginManager;
    private static Stage mainStage;
    private static boolean firstManager;
    private static MediaController mediaController = new MediaController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScenes();
        mainStage = primaryStage;
        mainStage.initStyle(StageStyle.UNDECORATED);
        if (firstManager)
            setMenu(MenuNames.MAINMENU);
        else
            setMenu(MenuNames.LOGIN_MANAGER);
    }

    private void initializeScenes() throws Exception {
        Parent root;
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/mainMenu/mainMenu.fxml").toURI().toURL());
        mainMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/allProducts.fxml").toURI().toURL());
        allProductsMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/registerAndLoginMenu.fxml").toURI().toURL());
        registerAndLoginMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/FxmlManagerLogin.fxml").toURI().toURL());
        loginManager = new Scene(root, 994, 666);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void back() {
        if (history.size() == 0)
            return;
        MenuNames currentScene = history.get(history.size() - 2);
        history.remove(history.size() - 1);
        setMenu(currentScene);
    }

    public static void setMenu(MenuNames name) {
        mediaController.stop();
        history.add(name);
        switch (name) {
            case MAINMENU:
                mainStage.setTitle("main menu");
                FxmlMainMenu.setWindow(mainStage);
                mainStage.setScene(mainMenu);
                mainStage.show();
                new Thread(() -> mediaController.mainTheme()).start();
                break;
            case REGISTERANDLOGINMENU:
                mainStage.setTitle("register and login menu");
                FxmlRegisterAndLoginMenu.setWindow(mainStage);
                mainStage.setScene(registerAndLoginMenu);
                mainStage.show();
                new Thread(() -> mediaController.registerAndLogin()).start();
                break;
            case ALLPRODUCTSMENU:
                mainStage.setTitle("all products menu");
                Parent root;
                try {
                    root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/allProducts.fxml").toURI().toURL());
                    allProductsMenu = new Scene(root, 994, 666);
                    FxmlAllProductsMenu.setMainWindow(mainStage);
                    mainStage.setScene(allProductsMenu);
                    mainStage.show();
                    new Thread(() -> mediaController.allProducts()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case LOGIN_MANAGER:
                mainStage.setTitle("Sign up manager");
                mainStage.setScene(loginManager);
                ManagerLoginController.setWindow(mainStage);
                mainStage.show();
                new Thread(() -> mediaController.mainTheme()).start();
                break;
        }
    }

    public static void setFirstManager(boolean firstManager) {
        ProgramApplication.firstManager = firstManager;
    }

    public static void startApp(String[] args) {
        launch(args);
    }

    public static void addToHistory(MenuNames name) {
        history.add(name);
    }

}
