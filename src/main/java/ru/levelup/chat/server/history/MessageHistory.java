package ru.levelup.chat.server.history;

import java.util.List;

public interface MessageHistory {

    void addMessage(String login, String message);

    List<String> getRecentMessages(int count);
}
