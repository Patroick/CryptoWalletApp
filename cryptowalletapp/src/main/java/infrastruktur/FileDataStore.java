package infrastruktur;

import Exceptions.RetrieveDataException;
import Exceptions.SaveDataException;
import domain.BankAccount;
import domain.DataStore;
import domain.WalletList;

import java.io.*;

public class FileDataStore implements DataStore {

    @Override
    public void saveBankAccount(BankAccount bankAccount) throws SaveDataException {
        if(bankAccount != null) {
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream("account.bin"));
                objectOutputStream.writeObject(bankAccount);
                objectOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new SaveDataException("Error saving BankAccount to File: " + ioException.getMessage());
            }
        }
    }

    @Override
    public void saveWalletList(WalletList walletList) throws SaveDataException {

        if(walletList != null) {
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream("walletlist.bin"));
                objectOutputStream.writeObject(walletList);
                objectOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new SaveDataException("Error saving Walletlist to File: " + ioException.getMessage());
            }

        }
    }

    @Override
    public BankAccount retriveBankAccount() throws RetrieveDataException {

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("account.bin"));
            BankAccount bankAccount = (BankAccount) objectInputStream.readObject();
            objectInputStream.close();
            return bankAccount;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RetrieveDataException("Error on retrieving BankAccount Data from file!" + e.getMessage());
        }
    }

    @Override
    public WalletList retrieveWalletList() throws RetrieveDataException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("walletlist.bin"));
            WalletList walletList = (WalletList)objectInputStream.readObject();
            objectInputStream.close();
            return walletList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RetrieveDataException("Error on retrieving WalletList Data from file!" + e.getMessage());
        }

    }
}
