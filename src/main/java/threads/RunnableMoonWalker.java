package threads;

import lombok.AllArgsConstructor;
import message.Message;
import message.MessageService;
import ship.Rocket;
import ship.spacecraft.MoonWalker;

import java.util.Map;

@AllArgsConstructor
public class RunnableMoonWalker implements Runnable {
    private static final String SENDER = "MOON_WALKER";
    private static final String MESSAGE_TO = "MANAGER";
    private static final String COMMAND_FORWARD = "ВПЕРЕД";
    private static final String COMMAND_RIGHT = "ВПРАВО";
    private static final String COMMAND_LEFT = "ВЛЕВО";
    private static final String COMMAND_BACK = "НАЗАД";
    private static final String COMMAND_SHOT = "СНИМОК";

    private Rocket rocket;
    private MessageService messageService;

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        try {
            t.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MoonWalker moonWalker = rocket.getSpaceCraft().getMoonWalker();
        try {
            readCommands(MESSAGE_TO, moonWalker);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCommands(String from, MoonWalker moonWalker) throws InterruptedException {
        Map<Integer, Message> messages = null;
        while (true) {
            try {
                messages = messageService.getUnreadMessages(from, SENDER);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (messages.size() != 0) {
                for (Message m :
                        messages.values()) {
                    switch (m.getText()) {
                        case COMMAND_FORWARD -> {
                            writeAnswer(moonWalker.moveForward(), MESSAGE_TO);
                            break;
                        }
                        case COMMAND_BACK -> {
                            writeAnswer(moonWalker.moveBack(), MESSAGE_TO);
                            break;
                        }
                        case COMMAND_LEFT -> {
                            writeAnswer(moonWalker.turnLeft(), MESSAGE_TO);
                            break;
                        }
                        case COMMAND_RIGHT -> {
                            writeAnswer(moonWalker.turnRight(), MESSAGE_TO);
                            break;
                        }
                        case COMMAND_SHOT -> {
                            writeAnswer(moonWalker.takeShot(), MESSAGE_TO);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void writeAnswer(String answer, String to) throws InterruptedException {
        messageService.putMessage(answer, SENDER, to);
    }


}
