package model.account;

public class Manager extends Account {
    private boolean firstManager;

    public Manager(String name, String lastName, String email, String phoneNumber, String username, double credit,
                   boolean firstManager) {
        super(name, lastName, email, phoneNumber, username, credit);
        this.firstManager = firstManager;
    }

    public boolean isFirstManager() {
        return firstManager;
    }

    public void setFirstManager(boolean firstManager) {
        this.firstManager = firstManager;
    }

    public void deleteAccount(String username) {
        Account account = getAccountWithUsername(username);
        try {
            Manager manager = (Manager) account;
            if (manager != null) {
                if (manager.isFirstManager())
                    return;
                getAllAccounts().remove(manager);
            }
        } catch (Exception e) {
            getAllAccounts().remove(account);
        }
    }
}
