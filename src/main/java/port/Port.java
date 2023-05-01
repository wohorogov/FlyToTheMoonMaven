package port;

import ship.Rocket;

public interface Port {
    public void mount(Rocket rocket);
    public String test();
    public void launch();
}
