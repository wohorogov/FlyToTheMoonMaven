package port;

import ship.Rocket;
import threads.RunnableShip;

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
        rocket.print_full_info();
        Thread threadShip = new Thread(new RunnableShip(rocket));
        threadShip.start();
    }
}
