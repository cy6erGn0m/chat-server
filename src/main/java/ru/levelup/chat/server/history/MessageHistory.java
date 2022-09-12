package ru.levelup.chat.server.history;

import ru.levelup.chat.server.MessageSubscription;

import java.util.List;

public interface MessageHistory extends MessageSubscription.EventReceiver {

    void addMessage(String message);

    List<String> getRecentMessage(int count);
}
