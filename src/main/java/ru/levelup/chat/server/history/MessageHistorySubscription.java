package ru.levelup.chat.server.history;

import ru.levelup.chat.server.MessageSubscription;

public class MessageHistorySubscription implements MessageSubscription.EventReceiver {
    private final MessageHistory history;

    public MessageHistorySubscription(MessageHistory history) {
        this.history = history;
    }

    @Override
    public void onNewMessage(String login, String message) {
        history.addMessage(login, message);
    }
}
