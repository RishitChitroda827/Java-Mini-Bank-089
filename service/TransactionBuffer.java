package service;

public class TransactionBuffer {
    private String[] buffer = new String[5];
    private int count = 0;

    public synchronized void produce(String tx) throws InterruptedException {
        while (count == buffer.length) {
            wait();
        }
        buffer[count] = tx;
        count++;
        System.out.println("Produced transaction: " + tx);
        notify();
    }

    public synchronized String consume() throws InterruptedException {
        while (count == 0) {
            wait();
        }
        count--;
        String tx = buffer[count];
        System.out.println("Consumed transaction: " + tx);
        notify();
        return tx;
    }
}
