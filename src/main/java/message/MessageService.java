package message;

public class MessageService {
    private enum status
    private String message;
    private boolean getMessage = false;

    public synchronized String get() throws InterruptedException {
        while (getMessage) {
            wait();
        }
        getMessage = true;

        notify();

        return message;
    }
    public synchronized void set(String message) throws InterruptedException {
        while (!getMessage) {
            wait();
        }
        this.message = message;

        getMessage = false;

        notify();
    }
}

enum status {
    PRED_START,
    START,
    FLYING_WITH_ENGINE,
    FLYING_WITHOUT_ENGINE,
    STOPPING,
    STOP
}