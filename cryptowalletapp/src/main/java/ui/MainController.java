package ui;

import java.text.MessageFormat;
import java.util.Random;
import java.util.ResourceBundle;

import domain.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

public class MainController extends BaseControllerState {

    @FXML
    private Button btnClose;

    @FXML
    private ComboBox cmbWalletCurrency;

    @FXML
    private Label lblBankaccountBalance;

    @FXML
    private TableView<Wallet> tableView;

    public void initialize() {

    }

    public void deposit(){
        System.out.println("DEPOSIT");
    }

    public void withdraw(){
        System.out.println("WITHDRAW");
    }

    public void openWallet(){
        System.out.println("OPEN WALLET");
    }

    public  void newWallet(){
        System.out.println("NEW WALLET");
    }

}
