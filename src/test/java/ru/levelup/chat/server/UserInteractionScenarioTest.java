package ru.levelup.chat.server;

import org.junit.Test;
import org.mockito.Mockito;
import ru.levelup.chat.server.history.InMemoryMessageHistory;
import ru.levelup.chat.server.history.MessageHistory;

import java.io.PrintWriter;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserInteractionScenarioTest {

    @Test
    public void sendHistoryTest() {
        UserSession session = new UserSession();
        MessageSubscription subscription = new MessageSubscription();
        MessageHistory history = new InMemoryMessageHistory();

        PrintWriter writer = mock(PrintWriter.class);

        history.addMessage("user1", "test message");

        UserInteractionScenario scenario = new UserInteractionScenario(session, subscription, history);
        scenario.sendHistory(writer);

        verify(writer).println("test message");
    }

    @Test
    public void sendHistoryMultipleMessages() {
        MessageHistory history = new InMemoryMessageHistory();
        PrintWriter writer = mock(PrintWriter.class);

        for (int i = 0; i < 100; ++i) {
            history.addMessage("user1", "test message");
        }

        UserInteractionScenario scenario = new UserInteractionScenario(
                mock(UserSession.class),
                mock(MessageSubscription.class),
                history
        );
        scenario.sendHistory(writer);

        verify(writer, times(10)).println("test message");
    }

    @Test
    public void sendHistoryNoMessage() {
        MessageHistory history = mock(MessageHistory.class);
        PrintWriter writer = mock(PrintWriter.class);

        UserInteractionScenario scenario = new UserInteractionScenario(
                mock(UserSession.class),
                mock(MessageSubscription.class),
                history
        );
        scenario.sendHistory(writer);

        verify(writer, never()).println(anyString());
    }

    @Test
    public void sendHistorySomeMessages() {
        MessageHistory history = mock(MessageHistory.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(history.getRecentMessages(0)).thenReturn(List.of());
        when(history.getRecentMessages(1)).thenReturn(List.of("message2"));
        when(history.getRecentMessages(anyInt())).thenReturn(List.of("Some message", "message2"));

        UserInteractionScenario scenario = new UserInteractionScenario(
                mock(UserSession.class),
                mock(MessageSubscription.class),
                history
        );
        scenario.sendHistory(writer);

        verify(writer, times(1)).println("Some message");
        verify(writer, atLeast(1)).println("Some message");
        verify(writer, atMost(1)).println("message2");
        verify(writer, never()).println("message3");
        Mockito.verifyNoMoreInteractions(writer);
    }
}
