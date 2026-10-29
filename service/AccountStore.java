package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import model.Account;

public class AccountStore {
    private HashMap<String, Account> accounts = new HashMap<>();
    private ArrayList<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        accounts.put(account.getAccountNumber(), account);
        transactionHistory.add("Account created: " + account.getAccountNumber());
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void record(String transaction) {
        transactionHistory.add(transaction);
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        list.sort(new Comparator<Account>() {
            @Override
            public int compare(Account a1, Account a2) {
                return Long.compare(a1.getBalance(), a2.getBalance());
            }
        });
        return list;
    }
}
