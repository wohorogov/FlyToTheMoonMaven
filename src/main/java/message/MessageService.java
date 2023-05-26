package message;

public class MessageService {
//    private enum status
    private static String message;
    private static boolean getMessage = false;

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