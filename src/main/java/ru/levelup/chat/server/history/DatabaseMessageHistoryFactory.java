package ru.levelup.chat.server.history;

import java.sql.SQLException;

public class DatabaseMessageHistoryFactory implements AbstractMessageHistoryFactory {
    @Override
    public MessageHistory createMessageHistory() {
        try {
            return new JdbcDatabaseMessageHistory("jdbc:postgre://..", "admin", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
