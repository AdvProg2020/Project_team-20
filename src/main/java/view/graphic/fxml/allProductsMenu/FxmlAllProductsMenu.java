package view.graphic.fxml.allProductsMenu;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FxmlAllProductsMenu {
    public ImageView get;
    public ImageView show;
    String name;

    public void show(ActionEvent actionEvent) throws Exception{
        Image img = new Image(new File("src/main/resources/Images/" + name).toURI().toString());
        show.setImage(img);
    }

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) throws Exception{
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream((files.get(0))));
        get.setImage(img);
        File outputFile = new File("src/main/resources/Images/hi.png");
        name = "hi.png";
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
