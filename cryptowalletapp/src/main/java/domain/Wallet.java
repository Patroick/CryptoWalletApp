package domain;

import Exceptions.InvalidFeeException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet implements Serializable {

    private final UUID id;
    private final String name;
    private final CryptoCurrency cryptoCurrency;
    private BigDecimal amount;
    private final List<Transaction> transaction;
    private BigDecimal feeInPercent;

    public Wallet(String name, CryptoCurrency cryptoCurrency, BigDecimal feeInPercent) throws InvalidFeeException {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cryptoCurrency = cryptoCurrency;
        this.amount = new BigDecimal("0");
        this.transaction = new ArrayList<>();
        this.setNewFee(feeInPercent);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Transaction> getTransaction() {
        return List.copyOf(transaction);
    }

    public BigDecimal getFeeInPercent() {
        return feeInPercent;
    }

    public String getCurrencyName(){
        return this.cryptoCurrency.getCurrencyName();
    }

    public void setNewFee (BigDecimal fee) throws InvalidFeeException {
        if(fee.compareTo(new BigDecimal("0"))>=0){
            this.feeInPercent = fee;
        } else {
            throw new InvalidFeeException();
        }
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cryptoCurrency=" + cryptoCurrency +
                ", amount=" + amount +
                ", transaction=" + transaction +
                ", feeInPercent=" + feeInPercent +
                '}';
    }
}
