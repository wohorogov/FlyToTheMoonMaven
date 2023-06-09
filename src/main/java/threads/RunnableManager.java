package threads;

import message.MessageService;
import port.Port;
import port.SpacePort;
import ship.Rocket;


public class RunnableManager implements Runnable{
    private Rocket rocket;
    private MessageService messageService;
    private int lastId;
    public RunnableManager(Rocket rocket, MessageService messageService) {
        this.rocket = rocket;
        this.messageService = messageService;
    }

    @Override
    public void run() {
        System.out.println("Мы в потоке управления");
        Port port = new SpacePort();
        port.mount(rocket);

        if (port.test()) {
            port.launch(messageService);
            System.out.println("Запустили поток ");
//            try {
//                messageService.set("Запуск");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            while (rocket.isFly()) {
//
//            }
        }
    }
}
