package server.controller.account.user;

import javafx.scene.image.Image;
import server.model.account.Account;
import server.network.AuthToken;
import server.network.Message;

public interface AccountController {
    Message getAccountInfo(String username);

    Message editField(String field, String context, Account account) throws Exception;

    Message changeMainImage(Image image, Account account);

    Message logout(AuthToken authToken, Account account);
}
