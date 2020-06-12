package controller.account.user;

import javafx.scene.image.Image;
import model.account.Account;

public interface AccountController {
    public Account getAccountInfo();

    public void editField(String field, String context) throws Exception;

    public void changeMainImage(Image image);

    public void logout();
}
