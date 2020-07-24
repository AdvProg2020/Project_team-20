package client.view.graphic.fxml.bank;

import client.controller.bank.BankController;
import client.model.bank.BankReceipt;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransitionsFxml implements Initializable {
    public Text balanceTxt;
    public ScrollPane depositsScroll;
    public Text depositsTxt;
    public ScrollPane withdrawScroll;
    public Text withdrawsTxt;
    public ScrollPane movesScroll;
    public Text movesTxt;

    private BankController bankController = BankController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        depositsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        depositsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        withdrawScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        withdrawScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        movesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        movesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        balanceTxt.setText(Double.toString(bankController.getBalance()));
        showTransactions();
    }

    private void showTransactions() {
        ArrayList<BankReceipt> deposits = bankController.getDeposits();
        System.out.println(deposits.size());
        ArrayList<BankReceipt> withdraws = bankController.getWithdraws();
        ArrayList<BankReceipt> moves = bankController.getMoves();
        StringBuilder depositsStr = new StringBuilder();
        for (BankReceipt bankReceipt:deposits)
            depositsStr.append(bankReceipt.toString());
        depositsTxt.setText(depositsStr.toString());

        StringBuilder movesStr = new StringBuilder();
        for (BankReceipt bankReceipt:moves)
            movesStr.append(bankReceipt.toString());
        movesTxt.setText(movesStr.toString());

        StringBuilder withdrawsStr = new StringBuilder();
        for (BankReceipt bankReceipt:withdraws)
            withdrawsStr.append(bankReceipt.toString());
        withdrawsTxt.setText(withdrawsStr.toString());
    }
}
