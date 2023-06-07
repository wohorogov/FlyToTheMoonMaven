package message;

import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class MessageService {
    private Map<Integer, Message> messageMapHistory;
    private static int id = 1;
    private boolean getMessage = true;
//
//    public synchronized String get() throws InterruptedException {
//        while (getMessage) {
//            wait();
//        }
//        getMessage = true;
//
//        notify();
//
//        return message;
//    }
//    public synchronized void set(String message) throws InterruptedException {
//        while (!getMessage) {
//            wait();
//        }
//        this.message = message;
//
//        getMessage = false;
//
//        notify();
//    }
    @Synchronized("messageMapHistory")
    public void putMessage(String text, String from, String to) {
        Message message = new Message(from, to, text, false);
        messageMapHistory.put(id, message);
        id++;
    }
    @Synchronized("messageMapHistory")
    public Map<Integer, Message> getUnreadMessages(String from, String to) {
        return messageMapHistory.entrySet()
                .stream()
                .filter(m -> !m.getValue().isRead() && m.getValue().getTo() == to && m.getValue().getFrom() == from)
                .collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue));
    }
}
//сообщения Map
//библиотека для логирования logforj к ней конфиг, чтобы уйти от печатанья в консоль
