package ru.levelup.chat.server;

import org.junit.Assert;
import org.junit.Test;
import ru.levelup.chat.server.history.DatabaseMessageHistoryFactory;
import ru.levelup.chat.server.history.HibernateMessageHistoryFactory;
import ru.levelup.chat.server.history.MessageHistory;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HibernateMessageHistoryTest {
    @Test
    public void smokeTest() {
        MessageHistory history = new HibernateMessageHistoryFactory().createMessageHistory();

        history.addMessage("login1", "message 123");
        List<String> messages = history.getRecentMessages(10);

        assertEquals(List.of("message 123"), messages);
    }
}
