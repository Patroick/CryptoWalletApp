package Exceptions;

public class InsufficientAmountException extends Throwable {

    public InsufficientAmountException(){
        super("Insufficient Amount of Crypto in Wallet!");
    }
}
