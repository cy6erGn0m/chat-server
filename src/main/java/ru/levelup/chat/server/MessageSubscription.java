package ru.levelup.chat.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageSubscription {
    private final List<EventReceiver> subscribers = new CopyOnWriteArrayList<>();

    public void subscribe(EventReceiver receiver) {
        subscribers.add(receiver);
    }

    public void unsubscribe(EventReceiver receiver) {
        subscribers.remove(receiver);
    }

    public void notifyAllSubscribers(String login, String message) {
        for (EventReceiver receiver : subscribers) {
            receiver.onNewMessage(login, message);
        }
    }

    public interface EventReceiver {
        void onNewMessage(String login, String message);
    }
}
