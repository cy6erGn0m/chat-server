package ru.levelup.chat.server.history;

import java.util.List;

public interface MessageHistory {

    void addMessage(String message);

    List<String> getRecentMessages(int count);
}
