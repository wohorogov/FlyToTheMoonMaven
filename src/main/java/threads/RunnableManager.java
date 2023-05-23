package threads;

import fly.Flying;
import ship.Rocket;

import java.util.concurrent.Exchanger;

public class RunnableManager implements Runnable{
    Rocket rocket;

    public RunnableManager(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public void run() {
        Exchanger<String> exchanger = new Exchanger<String>();
        System.out.println("Мы в потоке управления");
        try {
            String mess = exchanger.exchange("");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
