package ru.levelup.chat.server.history;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDatabaseMessageHistory implements MessageHistory {

    private final DataSource connectionSource;

    public JdbcDatabaseMessageHistory(String url, String login, String password) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(login);
        config.setPassword(password);
        config.setMaximumPoolSize(25);
        config.setMinimumIdle(5);

        connectionSource = new HikariDataSource(config);

        try (Connection connection = connect()) {
            try (Statement statement = connection.createStatement()) {
                createSchema(statement);
            }
        }
    }

    @Override
    public void addMessage(String login, String message) {
        try (Connection connection = connect()) {
            try (PreparedStatement pst = connection.prepareStatement("INSERT INTO messages (message) VALUES (?)")) {
                pst.setString(1, message);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getRecentMessages(int count) {
        try (Connection connection = connect()) {
            try (PreparedStatement pst = connection.prepareStatement("SELECT message FROM messages ORDER BY id DESC LIMIT ?")) {
                pst.setInt(1, count);
                try (ResultSet resultSet = pst.executeQuery()) {
                    ArrayList<String> results = new ArrayList<>(count);
                    while (resultSet.next()) {
                        results.add(resultSet.getString("message"));
                    }

                    return results;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect() throws SQLException {
        return connectionSource.getConnection();
    }

    private static void createSchema(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS messages (" +
                "id serial primary key," +
                "message varchar(1500)" +
        ")");
    }
}
