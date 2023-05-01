package port;

import fly.Flying;
import ship.Rocket;

public class SpacePort implements Port {
    private Rocket rocket;

    @Override
    public void mount(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public String test() {
        return rocket.test();
    }

    @Override
    public void launch() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Мы в потоке");
                Flying flying = new Flying();
                flying.startFly(rocket);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
