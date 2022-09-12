package ru.levelup.chat.server.history;

public interface AbstractMessageHistoryFactory {
    MessageHistory createMessageHistory();
}
