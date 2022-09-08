package ru.levelup.chat.server;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class UserInteractionScenarioTest {

    @Test
    public void sendHistoryTest() {
        UserSession session = new UserSession();
        MessageSubscription subscription = new MessageSubscription();
        MessageHistory history = new MessageHistory();

        PrintWriter writer = Mockito.mock(PrintWriter.class);

        history.addMessage("test message");

        UserInteractionScenario scenario = new UserInteractionScenario(session, subscription, history);
        scenario.sendHistory(writer);

        Mockito.verify(writer).println("test message");
    }

    @Test
    public void sendHistoryMultipleMessages() {
        MessageHistory history = new MessageHistory();
        PrintWriter writer = Mockito.mock(PrintWriter.class);

        for (int i = 0; i < 100; ++i) {
            history.addMessage("test message");
        }

        UserInteractionScenario scenario = new UserInteractionScenario(
                mock(UserSession.class),
                mock(MessageSubscription.class),
                history
        );
        scenario.sendHistory(writer);

        Mockito.verify(writer, times(10)).println("test message");
    }
}
