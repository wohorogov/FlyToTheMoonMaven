package port;

import fly.Flying;
import ship.Rocket;
import threads.RunnableManager;
import threads.RunnableShip;

import java.util.concurrent.Exchanger;

public class SpacePort implements Port {
    private Rocket rocket;
    @Override
    public void mount(Rocket rocket) {
        this.rocket = rocket;
    }
    @Override
    public boolean test() {
        return rocket.test();
    }
    @Override
    public void launch() {
        Thread threadShip = new Thread(new RunnableShip(rocket));
        Thread threadManager = new Thread(new RunnableManager(rocket));
        threadShip.start();
        threadManager.start();
    }
}
