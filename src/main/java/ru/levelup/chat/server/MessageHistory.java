package ru.levelup.chat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;
import static java.util.List.copyOf;

public class MessageHistory implements MessageSubscription.EventReceiver {
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getRecentMessage(int count) {
        synchronized (messages) {
            return copyOf(messages.subList(max(0, messages.size() - count), messages.size()));
        }
    }

    @Override
    public void onNewMessage(String message) {
        addMessage(message);
    }
}
