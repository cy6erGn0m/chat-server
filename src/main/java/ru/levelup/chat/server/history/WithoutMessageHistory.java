package ru.levelup.chat.server.history;

import java.util.Collections;
import java.util.List;

public class WithoutMessageHistory implements MessageHistory {
    @Override
    public void addMessage(String message) {
    }

    @Override
    public List<String> getRecentMessage(int count) {
        return Collections.emptyList();
    }

    @Override
    public void onNewMessage(String message) {
    }
}
