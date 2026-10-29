package service;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.annotation.Audit;
import util.Repository;
import util.StatementFormatter;
import util.TransactionLog;
import exception.BankException;
import exception.AccountNotFoundException;
import exception.InvalidAmountException;

public class BankService {
    private Repository<Account> repository;
    private Path logPath = Path.of("logs", "transactions.log");

    public BankService(Repository<Account> repository) {
        this.repository = repository;
    }

    public Repository<Account> getRepository() {
        return repository;
    }

    private void audit(String methodName) {
        for (Method m : getClass().getDeclaredMethods()) {
            if (m.getName().equals(methodName) && m.isAnnotationPresent(Audit.class)) {
                System.out.println("Audit log: " + methodName + " invoked");
                break;
            }
        }
    }

    @Audit
    public Account openAccount(String type, String ownerName, long initialBalance, long extra) throws InvalidAmountException {
        audit("openAccount");
        if (initialBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative");
        }

        Account account;
        if (type.equalsIgnoreCase("SAVINGS")) {
            account = new SavingsAccount(ownerName, initialBalance, extra);
        } else if (type.equalsIgnoreCase("CURRENT")) {
            account = new CurrentAccount(ownerName, initialBalance, extra);
        } else {
            account = new FixedDepositAccount(ownerName, initialBalance);
        }

        repository.save(account);
        TransactionLog.append(logPath, "OPEN " + account.getAccountNumber() + " " + initialBalance);
        return account;
    }

    @Audit
    public void deposit(String accountNumber, long amount) throws BankException {
        audit("deposit");
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        account.deposit(amount);
        TransactionLog.append(logPath, "DEPOSIT " + accountNumber + " " + amount);
    }

    @Audit
    public void withdraw(String accountNumber, long amount) throws BankException {
        audit("withdraw");
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        account.withdraw(amount);
        TransactionLog.append(logPath, "WITHDRAW " + accountNumber + " " + amount);
    }

    @Audit
    public void transfer(String fromAcc, String toAcc, long amount) throws BankException {
        audit("transfer");
        Account from = repository.findById(fromAcc);
        Account to = repository.findById(toAcc);
        if (from == null) {
            throw new AccountNotFoundException("Source account not found: " + fromAcc);
        }
        if (to == null) {
            throw new AccountNotFoundException("Target account not found: " + toAcc);
        }
        Account.safeTransfer(from, to, amount);
        TransactionLog.append(logPath, "TRANSFER " + fromAcc + " " + toAcc + " " + amount);
    }

    @Audit
    public String statement(String accountNumber) throws BankException {
        audit("statement");
        Account account = repository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return StatementFormatter.buildStatement(account);
    }

    @Audit
    public ArrayList<Account> listAccounts() {
        audit("listAccounts");
        return repository.findAll();
    }
}
