package ru.levelup.chat.server.history;

import java.util.Collections;
import java.util.List;

public class WithoutMessageHistory implements MessageHistory {
    @Override
    public void addMessage(String login, String message) {
    }

    @Override
    public List<String> getRecentMessages(int count) {
        return Collections.emptyList();
    }
}
