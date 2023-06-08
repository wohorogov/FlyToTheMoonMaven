package message;

import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class MessageService {
    private Map<Integer, Message> messageMapHistory = new HashMap<Integer, Message>();
    private static int id = 1;
    private boolean busy = false;
    @Synchronized("messageMapHistory")
    public void putMessage(String text, String from, String to) throws InterruptedException {
        if (busy) {
            try {
                messageMapHistory.wait();
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        busy = true;
        Message message = new Message(from, to, text, false);
        messageMapHistory.put(id, message);
        id++;

        busy = false;
        messageMapHistory.notifyAll();
    }
    @Synchronized("messageMapHistory")
    public Map<Integer, Message> getUnreadMessages(String from, String to) throws InterruptedException {
        if (busy) {
            try {
                messageMapHistory.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        busy = true;
        Map<Integer, Message> messHistory = messageMapHistory;
        Map<Integer, Message> unreadMessages =  messHistory.entrySet()
                .stream()
                .filter(m -> !m.getValue().isRead() && m.getValue().getTo() == to && m.getValue().getFrom() == from)
                .collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue));

        busy = false;
        messageMapHistory.notifyAll();
        return unreadMessages;
    }
}
//сообщения Map
//библиотека для логирования logforj к ней конфиг, чтобы уйти от печатанья в консоль
