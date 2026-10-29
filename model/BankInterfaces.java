package model;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class BankInterfaces {

    public interface Transactable {
        void deposit(long amount) throws InvalidAmountException;
        boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
    }

    public interface InterestBearing {
        double interestRate();
        long getBalance();

        default double yearlyInterest() {
            return getBalance() * interestRate() / 100;
        }
    }

    @FunctionalInterface
    public interface WithdrawRule {
        boolean allow(Account account, long amount);
    }

    public interface Premium {
    }
}
