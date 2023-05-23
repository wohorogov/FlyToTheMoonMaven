package threads;

import fly.Flying;
import ship.Rocket;

import java.util.concurrent.Exchanger;

public class RunnableShip implements Runnable{
    Rocket rocket;

    public RunnableShip(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public void run() {
        Exchanger<String> exchanger = new Exchanger<String>();
        System.out.println("Мы в потоке Ракеты");
        try {
            String mess = exchanger.exchange("Ракета готова к полету");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean rocketOperable = true;
        Flying flying = new Flying();
        while (rocketOperable) {
            flying.startFly(rocket);
        }
    }
}
