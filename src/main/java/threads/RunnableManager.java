package threads;

import lombok.Getter;
import message.Message;
import message.MessageService;
import org.example.StartProject;
import port.Port;
import port.SpacePort;
import ship.Rocket;

import java.util.Map;
import java.util.logging.Logger;

@Getter
public class RunnableManager implements Runnable{
    private static final String SENDER = "MANAGER";
    private static final String MESSAGE_TO_ROCKET = "ROCKET";
    private static final String MESSAGE_TO_MOON_WALKER = "MOON_WALKER";
    private static final String COMMAND_FORWARD = "ВПЕРЕД";
    private static final String COMMAND_RIGHT = "ВПРАВО";
    private static final String COMMAND_LEFT = "ВЛЕВО";
    private static final String COMMAND_BACK = "НАЗАД";
    private static final String COMMAND_SHOT = "СНИМОК";
    public final static Logger log = Logger.getLogger(String.valueOf(StartProject.class));
    private boolean getShot = false;
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
        Thread t = Thread.currentThread();

        if (port.test()) {
            port.launch(messageService);

//            try {
//                t.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            while (true) {
                try {
                    t.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                readMessages(MESSAGE_TO_ROCKET);
                getFlyInfo();
                if (!rocket.isFly()) {
                    readMessages(MESSAGE_TO_ROCKET);
                    break;
                }
            }

            Thread threadMoonWalker = new Thread(new RunnableMoonWalker(rocket, messageService));
            threadMoonWalker.start();

            while (!getShot) {
                try {
                    messageService.putMessage(COMMAND_FORWARD, SENDER, MESSAGE_TO_MOON_WALKER);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    messageService.putMessage(COMMAND_BACK, SENDER, MESSAGE_TO_MOON_WALKER);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    messageService.putMessage(COMMAND_RIGHT, SENDER, MESSAGE_TO_MOON_WALKER);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }try {
                    messageService.putMessage(COMMAND_SHOT, SENDER, MESSAGE_TO_MOON_WALKER);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    messageService.getUnreadMessages(MESSAGE_TO_MOON_WALKER, SENDER);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    t.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                readMessages(MESSAGE_TO_MOON_WALKER);
            }
        }
    }

    private void readMessages(String from) {
        Map<Integer, Message> messages = null;
        try {
            messages = messageService.getUnreadMessages(from, SENDER);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (messages.size() != 0) {
            for (Message m :
                    messages.values()) {
                log.info(m.getText());
//                System.out.println(m.getText());
                m.setRead(true);
                if (m.getText().equals("Снимок сделан")) {
                    getShot = true;
                }
            }
        }
    }
    private void getFlyInfo() {
        String flyInfo = "My Log Пройденное расстояние = " + rocket.getDistance() + "м, масса = " +
                rocket.getAllMass() + "кг, скорость " + rocket.getSpeed() + "м/с";

        log.info(flyInfo);
    }
}
