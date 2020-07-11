package server.controller.account.user;

import javafx.scene.image.Image;
import server.network.AuthToken;
import server.network.Message;

public interface AccountController {
    Message getAccountInfo(AuthToken authToken);

    Message editField(String field, String context, AuthToken authToken) throws Exception;

    Message changeMainImage(Image image, AuthToken authToken);

    Message logout(AuthToken authToken);
}
