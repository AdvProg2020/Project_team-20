package controller.account.user;

import model.account.Account;

public interface AccountController {
    public Account getAccountInfo();
    public void editField(String field, String context) throws Exception;
    public void logout();
}
