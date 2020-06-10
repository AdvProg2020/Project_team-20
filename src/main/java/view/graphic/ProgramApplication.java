package view.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.graphic.fxml.mainMenu.FxmlMainMenu;
import view.graphic.fxml.saleMenu.FxmlSaleMenu;

import java.io.File;
import java.util.ArrayList;

public class ProgramApplication extends Application {

    private static ArrayList<MenuNames> history = new ArrayList<>();

    private static Scene mainMenu, buyerMenu, managerMenu, registerAndLoginMenu, sellerMenu, allProductsMenu, saleMenu;
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScenes();
        mainStage = primaryStage;
        setMenu(MenuNames.MAINMENU);
    }

    private void initializeScenes() throws Exception {
        Parent root;
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/mainMenu/mainMenu.fxml").toURI().toURL());
        mainMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/allProductsMenu/allProducts.fxml").toURI().toURL());
        allProductsMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/registerAndLoginMenu/registerAndLoginMenu.fxml").toURI().toURL());
        registerAndLoginMenu = new Scene(root, 994, 666);
        root = FXMLLoader.load(new File("src/main/java/view/graphic/fxml/saleMenu/saleMenu.fxml").toURI().toURL());
        saleMenu = new Scene(root, 994, 666);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void back() {
        if (history.size() == 0)
            return;
        MenuNames currentScene = history.get(history.size() - 1);
        history.remove(currentScene);
        setMenu(currentScene);
    }

    public static void setMenu(MenuNames name) {
        history.add(name);
        switch (name) {
            case MAINMENU:
                mainStage.setTitle("main menu");
                FxmlMainMenu.setWindow(mainStage);
                mainStage.setScene(mainMenu);
                mainStage.show();
                break;
            case SALEMENU:
                mainStage.setTitle("sale menu");
                mainStage.setScene(saleMenu);
                mainStage.show();
                break;
            case REGISTERANDLOGINMENU:
                mainStage.setTitle("register and login menu");
                mainStage.setScene(registerAndLoginMenu);
                mainStage.show();
                break;
            case ALLPRODUCTSMENU:
                mainStage.setTitle("all products menu");
                mainStage.setScene(allProductsMenu);
                mainStage.show();
                break;
        }
    }

    public static void startApp(String[] args) {
        launch(args);
    }

}
