package model.account;

public class Supporter extends Account{
    public Supporter(String name, String lastName, String email,
                     String phoneNumber, String username, String password, AccountType accountType) {
        super(name, lastName, email, phoneNumber, username, password, 0, accountType);
    }
}
