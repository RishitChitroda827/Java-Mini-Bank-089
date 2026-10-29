package util;

public class TransactionSession implements AutoCloseable {
    private String sessionId;

    public TransactionSession(String sessionId) {
        this.sessionId = sessionId;
        System.out.println("Session opened: " + sessionId);
    }

    @Override
    public void close() {
        System.out.println("Session closed: " + sessionId);
    }
}
