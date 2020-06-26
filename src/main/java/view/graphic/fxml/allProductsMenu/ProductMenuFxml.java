package view.graphic.fxml.allProductsMenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.Main;
import controller.MainController;
import controller.MediaController;
import controller.account.user.SellerController;
import controller.product.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.account.Seller;
import model.product.Field.Field;
import model.product.Field.NumericalField;
import model.product.Field.OptionalField;
import model.product.Product;
import model.product.comment.Comment;
import model.product.comment.Reply;
import view.graphic.MenuNames;
import view.graphic.ProgramApplication;
import view.graphic.alert.AlertController;
import view.graphic.alert.AlertType;
import view.graphic.fxml.mainMenu.FxmlMainMenu;
import view.graphic.score.Score;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductMenuFxml implements Initializable {
    public ImageView productImg;
    public Text productName;
    private static Product currentProduct;
    private static ProductController productController;
    public ImageView score;
    public Text description;
    public JFXTextArea fields;
    public ListView<String> sellers;
    public ObservableList<String> sellerUserNames =
            FXCollections.observableArrayList();
    public Text price;
    public VBox allCommentsBox;
    public JFXTextField newComment;
    public JFXTextField newReply;
    public VBox allRepliesBox;
    public JFXButton sendReply;
    public Button rate1;
    public Button rate2;
    public Button rate3;
    public Button rate4;
    public Button rate5;
    public Text realPrice;
    public Line Offline;
    public ImageView saleImg;
    public TextField newPrice;
    public TextField newCount;
    public JFXButton addSellerBtn;
    private Seller seller;
    private Comment comment;

    MediaController mediaController = ProgramApplication.getMediaController();

    {
        mediaController.stop();
        mediaController.productMenu();
    }

    public void handleLogin(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.REGISTERANDLOGINMENU);
    }

    public void mainTabBtnEnter(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 3;");
    }

    public void mainTabBtnExit(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: transparent;");
    }

    public void handleBack(ActionEvent actionEvent) {
        ProgramApplication.back();
    }

    public void handleExit(ActionEvent actionEvent) {
        FxmlAllProductsMenu.key = false;
        Main.storeData();
        FxmlMainMenu.window.close();
    }

    public void handleMainMenu(ActionEvent actionEvent) {
        ProgramApplication.setMenu(MenuNames.MAINMENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image(new File("src/main/resources/Images/" + currentProduct.getImagePath()).toURI().toString());
        productImg.setImage(img);
        productName.setText(currentProduct.getName());
        description.setText(currentProduct.getDescription());
        score.setImage(new Score(currentProduct.getAverage()).getScoreImg());

        for (Field field:currentProduct.getGeneralFields()) {
            if (field instanceof NumericalField) {
                fields.appendText(field.getName() + ": " + ((NumericalField)field).getNumericalField() + "\n");
            } else {
                StringBuilder optionalFields = new StringBuilder();
                for (String str: ((OptionalField)field).getOptionalFiled())
                    optionalFields.append(str).append(", ");
                fields.appendText(field.getName() + ": " + optionalFields + "\n");
            }
        }
        fields.setEditable(false);
        if (MainController.getInstance().getAccount() instanceof Seller) {
            newPrice.setOpacity(1);
            newCount.setOpacity(1);
            addSellerBtn.setOpacity(1);
        }

        if (currentProduct.isInSale())
            saleImg.setOpacity(1);

        for (Seller seller:currentProduct.getSellers())
            sellerUserNames.add(seller.getUsername());
        sellers.setItems(sellerUserNames);
        showComments();
    }

    public void createComment(String username, String comment, String commentId, VBox mainBox, int type) {
        HBox commentBox = new HBox();
        commentBox.setStyle("-fx-background-color: #f4f4f4;");

        HBox usernameBox = new HBox();

        usernameBox.setStyle("-fx-background-color: transparent;");
        TextField text = new TextField();
        text.setText(username + ":");
        text.setStyle("-fx-background-color: transparent; -fx-text-inner-color: #5fccd0;");
        TextField textId = new TextField();
        textId.setStyle("-fx-background-color: transparent; -fx-text-inner-color: #f4f4f4;");
        textId.setText(commentId);
        textId.setOpacity(0);
        usernameBox.getChildren().add(text);
        usernameBox.getChildren().add(textId);
        mainBox.getChildren().add(usernameBox);

        HBox commentReplyBox = new HBox();
        commentReplyBox.setStyle("-fx-background-color: #e6e7e9; -fx-background-radius:20;");
        commentReplyBox.setLayoutX(20);
        TextField text2 = new TextField();
        text2.setStyle("-fx-background-color: transparent; -fx-text-inner-color: #575957;");
        text2.setText(comment);
        commentReplyBox.getChildren().add(text2);
        if (type==0) {
            JFXButton button = new JFXButton();
            button.setText("...");
            button.setStyle("-fx-background-color:  #edf3be#edf3be; -fx-border-color: #252e3b; -fx-background-radius:60; -fx-border-radius:60;");
            button.setOnAction(this::showReplies);
            commentReplyBox.getChildren().add(button);
        }
        mainBox.getChildren().add(commentReplyBox);
    }

    public static void setCurrentProduct(Product currentProduct) {
        ProductMenuFxml.currentProduct = currentProduct;
        productController = new ProductController(currentProduct);
    }


    public void selectSeller(MouseEvent event) {
        Offline.setOpacity(0);
        realPrice.setOpacity(0);
        String selected = sellers.getSelectionModel().getSelectedItem();
        seller = currentProduct.getSellerByUsername(selected);
        price.setText(Double.toString(currentProduct.getPrice(seller)));
        if (currentProduct.isInSale(seller)) {
            Offline.setOpacity(1);
            realPrice.setOpacity(1);
            realPrice.setText(Double.toString(currentProduct.getPrice(seller)));
            price.setText(Double.toString(currentProduct.getPriceWithOff(seller)));
        }
    }

    public void handleAddToCart(ActionEvent actionEvent) {
        if (seller==null) {
            new AlertController().create(AlertType.ERROR, "please select a seller");
            return;
        }
        try {
            productController.addProductToCart(seller.getUsername());
            handleBack(null);
            new AlertController().create(AlertType.CONFIRMATION, "addition was successful");
        } catch (Exception e) {
            e.printStackTrace();
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void showReplies(ActionEvent actionEvent) {
        allRepliesBox.getChildren().clear();
        JFXButton jfxButton = (JFXButton) actionEvent.getSource();
        TextField idText = null;
        for (Node node:(jfxButton.getParent()).getParent().getChildrenUnmodifiable()) {
            if (node instanceof HBox) {
                for (Node node1:((HBox) node).getChildrenUnmodifiable()) {
                    if (node1 instanceof TextField)
                        idText = (TextField)node1;
                }
                break;
            }
        }
        if (idText==null) {
            return;
        }
        String id = idText.getText();
        try {
            comment = currentProduct.getCommentWithId(id);
            ArrayList<Reply> replies = comment.getReplies();
            for (Reply reply:replies) {
                createComment(reply.getBuyer().getUsername(), reply.getContent(), null, allRepliesBox, 1);
            }
            allRepliesBox.setOpacity(1);
            sendReply.setOpacity(1);
            newReply.setOpacity(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRepliesComment(Comment commentToShow) {
        allRepliesBox.getChildren().clear();
        ArrayList<Reply> replies = comment.getReplies();
        for (Reply reply:replies) {
            try {
                createComment(reply.getBuyer().getUsername(), reply.getContent(), null, allRepliesBox, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showComments() {
        allCommentsBox.getChildren().clear();
        for (Comment comment:currentProduct.getComments()) {
            try {
                VBox commentVBox = new VBox();
                createComment(comment.getBuyer().getUsername(), comment.getContent(), comment.getCommentId(), commentVBox, 0);
                allCommentsBox.getChildren().add(commentVBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addComment(ActionEvent actionEvent) {
        String commentStr = newComment.getText();
        if (commentStr.equals("")) {
            new AlertController().create(AlertType.ERROR, "please fill the comment!");
            return;
        }
        try {
            productController.addComment(currentProduct, null, commentStr);
            new AlertController().create(AlertType.CONFIRMATION, "comment added successfully");
            newComment.setText("");
            showComments();
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void addReply(ActionEvent actionEvent) {
        String replyStr = newReply.getText();
        if (replyStr.equals("")) {
            new AlertController().create(AlertType.ERROR, "please fill the reply!");
            return;
        }
        try {
            productController.addReplyToComment(comment, null, replyStr);
            new AlertController().create(AlertType.CONFIRMATION, "comment added successfully");
            newReply.setText("");
            showRepliesComment(comment);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleRateProduct(ActionEvent actionEvent) {
        double score = Double.parseDouble(((Button)actionEvent.getSource()).getId().substring(((Button)actionEvent.getSource()).getId().length()-1));
        try {
            productController.addScore(score, currentProduct);
            new AlertController().create(AlertType.CONFIRMATION, "Thanks for rating");
            Image img = new Image(new File("src/main/resources/Images/" + currentProduct.getImagePath()).toURI().toString());
            productImg.setImage(null);
            productImg.setImage(img);
        } catch (Exception e) {
            new AlertController().create(AlertType.ERROR, e.getMessage());
        }
    }

    public void handleRateEnter(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-radius:30; -fx-background-color:#5fccd0; -fx-border-color:#5fccd0; -fx-border-radius:30; -fx-border-width:1");
        ((Button)event.getSource()).setTextFill(Color.WHITE);
    }

    public void handleRateExit(MouseEvent event) {
        ((Button)event.getSource()).setStyle("-fx-background-radius:30; -fx-background-color:transparent; -fx-border-color:#5fccd0; -fx-border-radius:30; -fx-border-width:1");
        ((Button)event.getSource()).setTextFill(Color.valueOf("#5fccd0"));
    }

    public void handleAnotherSeller(ActionEvent actionEvent) {
        if(SellerController.getSeller()==null){
            new AlertController().create(AlertType.ERROR, "you are not a seller!");
        }
        else{
            int count = Integer.parseInt(newCount.getText());
            double price = Double.parseDouble(newPrice.getText());
            SellerController sellerController = SellerController.getInstance();
            try {
                sellerController.addToProduct(currentProduct.getId(),count,price);
            }
           catch (Exception e){
               new AlertController().create(AlertType.ERROR, e.getMessage());
           }
        }
        newCount.clear();
        newPrice.clear();
    }
}
