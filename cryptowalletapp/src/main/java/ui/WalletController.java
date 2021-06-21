package ui;

import Exceptions.*;
import at.htlimst.sample.WalletApp;
import domain.CurrentPriceForCurrency;
import domain.Transaction;
import domain.Wallet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BackgroundImage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class WalletController extends BaseControllerState{

    @FXML
    private Button btnBackToMain;

    @FXML
    private Label lblId, lblName, lblCurrency, lblAmount, lblFee, lblValue;

    @FXML
    private TableView<Transaction> tblTransactions;

    private Wallet wallet;

    public void initialize(){
        this.wallet = (Wallet) GlobalContext.getGlobalContext().getStateFor(WalletApp.GLOBAL_SELECTED_WALLET);
        refreshAllGuiValues();
        btnBackToMain.setOnAction((ActionEvent e) ->{
            WalletApp.switchScene("main.fxml", "at.htlimst.sample.main");
        });
    }

    private CurrentPriceForCurrency getCurrentPriceStrategy(){
       return (CurrentPriceForCurrency) GlobalContext.getGlobalContext().getStateFor(WalletApp.GLOBAL_CURRENT_CURRENCY_PRICES);
    }

    private void refreshAllGuiValues(){
        this.lblId.textProperty().setValue(this.wallet.getId().toString());
        this.lblName.textProperty().setValue(this.wallet.getName());
        this.lblCurrency.textProperty().setValue(wallet.getCryptoCurrency().getCode());
        this.lblAmount.textProperty().setValue(wallet.getAmount().toString());
        this.lblFee.textProperty().setValue(wallet.getFeeInPercent().toString());

        try {
            BigDecimal currentPrice = this.getCurrentPriceStrategy().getCurrentPrice(wallet.getCryptoCurrency());
            BigDecimal currentValue = currentPrice.multiply(wallet.getAmount().setScale(6, RoundingMode.HALF_UP));
            System.out.println(currentPrice);
            this.lblValue.textProperty().setValue(currentValue.toString());
        } catch (GetCurrentPriceException e) {
            WalletApp.showErrorDialog(e.getMessage());
            this.lblValue.textProperty().setValue("CURRENT PRICES UNAVAILABLE! ");
            e.printStackTrace();
        }

        tblTransactions.getItems().setAll(wallet.getTransaction());
        populateTable();
    }

    private void populateTable(){
        TableColumn<Transaction, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Transaction, String> crypto = new TableColumn<>("CRYPTO");
        crypto.setCellValueFactory(new PropertyValueFactory<>("cryptoCurrency"));

        TableColumn<Transaction, String> amount = new TableColumn<>("AMOUNT");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, String> total = new TableColumn<>("TOTAL");
        total.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<Transaction, String> priceAtTransactionDate = new TableColumn<>("PRICE");
        priceAtTransactionDate.setCellValueFactory(new PropertyValueFactory<>("priceAtTransactionDate"));

        TableColumn<Transaction, String> date = new TableColumn<>("DATE");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblTransactions.getColumns().clear();
        tblTransactions.getColumns().add(id);
        tblTransactions.getColumns().add(crypto);
        tblTransactions.getColumns().add(amount);
        tblTransactions.getColumns().add(total);
        tblTransactions.getColumns().add(priceAtTransactionDate);
        tblTransactions.getColumns().add(date);
    }

    public void buy(){
        TextInputDialog dialog = new TextInputDialog("Amount of Crypto to buy...");
        dialog.setTitle("Buy Crypto");
        dialog.setHeaderText("How much Crypto do you want to buy?");
        dialog.setContentText("Amount: ");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            try {
                BigDecimal amount = new BigDecimal(result.get());
                this.wallet.buy(amount, this.getCurrentPriceStrategy().getCurrentPrice(wallet.getCryptoCurrency()), this.getBankAccount());
                this.refreshAllGuiValues();
            } catch (NumberFormatException e){
                WalletApp.showErrorDialog("Invalid amount. Insert a number!");
            } catch (GetCurrentPriceException | InvalidAmountException | InsufficientBalanceException e) {
                WalletApp.showErrorDialog(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void sell(){
        TextInputDialog dialog = new TextInputDialog("Amount of Crypto to sell...");
        dialog.setTitle("Sell Crypto");
        dialog.setHeaderText("How much Crypto do you want to sell?");
        dialog.setContentText("Amount: ");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            try {
                BigDecimal amount = new BigDecimal(result.get());
                this.wallet.sell(amount, this.getCurrentPriceStrategy().getCurrentPrice(wallet.getCryptoCurrency()), this.getBankAccount());
                this.refreshAllGuiValues();
            } catch (NumberFormatException e){
                WalletApp.showErrorDialog("Invalid amount. Insert a number!");
            } catch (InsufficientAmountException | GetCurrentPriceException | InvalidAmountException e) {
                WalletApp.showErrorDialog(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
