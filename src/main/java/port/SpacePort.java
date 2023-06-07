package port;

import lombok.NoArgsConstructor;
import message.MessageService;
import ship.Rocket;
import threads.RunnableShip;
@NoArgsConstructor
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
    public void launch(MessageService messageService) {
        rocket.printFullInfo();
        Thread threadShip = new Thread(new RunnableShip(rocket, messageService));
        threadShip.start();
    }
}
