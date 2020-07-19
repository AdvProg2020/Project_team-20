package server.model.account;

public class Wallet {
    double money;

    public void increaseMoney(double moneyToAdd) {
        money+=moneyToAdd;
    }

    public void decreaseMoney(double moneyToSub) throws Exception{
        if (money<moneyToSub)
            throw new NotEnoughMoney();
        money-=moneyToSub;
    }

    public Wallet(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public static class NotEnoughMoney extends Exception {
        public NotEnoughMoney() {
            super("Not Enough money");
        }
    }
}
