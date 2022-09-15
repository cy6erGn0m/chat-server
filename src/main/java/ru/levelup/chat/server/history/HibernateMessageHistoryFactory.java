package ru.levelup.chat.server.history;

import jakarta.persistence.Persistence;

public class HibernateMessageHistoryFactory implements AbstractMessageHistoryFactory {
    @Override
    public MessageHistory createMessageHistory() {
        return new HibernateDatabaseMessageHistory(
                Persistence.createEntityManagerFactory("TestPersistenceUnit")
        );
    }
}
