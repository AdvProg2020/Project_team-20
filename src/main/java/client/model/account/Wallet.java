package client.model.account;

public class Wallet {
    double money;
    double minMoney;

    public Wallet(double money) {
        this.money = money;
    }

    public void setMinMoney(double money){
        this.minMoney = money;
    }

    public double getMoney() {
        return money;
    }

    public void addMoney(double wage , double money){
        this.money = (1-wage/100)*money;
    }

    public void buyWithWallet(double money1) throws Exception{
        if((money - money1)>minMoney){
            money = money - money1;
        }
        else {
            throw new notEnoughMoney();
        }
    }

    public static class notEnoughMoney extends Exception {
        public notEnoughMoney() {
            super("you don't have enough money...");
        }
    }
}
