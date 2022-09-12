package ru.levelup.chat.server.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryMessageHistory implements MessageHistory {
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void onNewMessage(String message) {
        addMessage(message);
    }

    @Override
    public void addMessage(String message) {
        messages.add(message);
    }

    @Override
    public List<String> getRecentMessages(int count) {
        synchronized (messages) {
            int firstIndex = messages.size() - count;
            if (firstIndex < 0) {
                firstIndex = 0;
            }
            return List.copyOf(messages.subList(firstIndex, messages.size()));
        }
    }
}
