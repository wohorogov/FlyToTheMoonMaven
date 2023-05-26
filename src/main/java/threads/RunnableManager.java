package threads;

import message.MessageService;
import port.Port;
import port.SpacePort;
import ship.Rocket;


public class RunnableManager implements Runnable{
    Rocket rocket;

    public RunnableManager(Rocket rocket) {
        this.rocket = rocket;
    }

    @Override
    public void run() {
        System.out.println("Мы в потоке управления");
        MessageService messageService = new MessageService();
        Port port = new SpacePort();
        port.mount(rocket);

        if (port.test()) {
            port.launch();
            System.out.println("Запустили поток ");
            try {
                messageService.set("Запуск");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (rocket.isFly()) {

            }
        }
    }
}
