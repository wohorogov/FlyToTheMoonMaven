package port;

import message.MessageService;
import ship.Rocket;

public interface Port {
    public void mount(Rocket rocket);
    public boolean test();
    public void launch(MessageService messageService);
}
