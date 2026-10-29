package test;

import java.lang.reflect.Method;
import model.Account;
import model.SavingsAccount;
import exception.InsufficientFundsException;
import service.AccountWorker;

public class MiniBankTest {

    @Test
    public void testDepositIncreasesBalance() throws Exception {
        Account account = new SavingsAccount("User1", 1000, 500);
        account.deposit(500);
        if (account.getBalance() != 1500) {
            throw new AssertionError("Expected balance 1500, but got " + account.getBalance());
        }
    }

    @Test
    public void testWithdrawalOverLimitThrowsException() throws Exception {
        Account account = new SavingsAccount("User2", 1000, 500);
        boolean thrown = false;
        try {
            account.withdraw(800);
        } catch (InsufficientFundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected InsufficientFundsException");
        }
    }

    @Test
    public void testConcurrentDeposits() throws Exception {
        Account account = new SavingsAccount("User3", 0, 0);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new AccountWorker(account, 50, 10));
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        if (account.getBalance() != 5000) {
            throw new AssertionError("Expected 5000, got " + account.getBalance());
        }
    }

    public static void runAllTests() {
        int passed = 0;
        int failed = 0;
        MiniBankTest testObj = new MiniBankTest();
        for (Method m : MiniBankTest.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                try {
                    m.invoke(testObj);
                    System.out.println("Test passed: " + m.getName());
                    passed++;
                } catch (Exception e) {
                    System.out.println("Test failed: " + m.getName());
                    failed++;
                }
            }
        }
        System.out.println("Tests summary: " + passed + " passed, " + failed + " failed");
    }

    public static void main(String[] args) {
        runAllTests();
    }
}
