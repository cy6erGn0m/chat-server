package ru.levelup.chat.server;

import ru.levelup.chat.server.history.DatabaseMessageHistory;
import ru.levelup.chat.server.history.InMemoryMessageHistory;
import ru.levelup.chat.server.history.MessageHistory;
import ru.levelup.chat.server.history.WithoutMessageHistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class ChatServer {
//    private final MessageHistory messages = new InMemoryMessageHistory();
    private final MessageHistory messages;

    {
        try {
            messages = new DatabaseMessageHistory("jdbc:postgre://..", "admin", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final MessageSubscription subscription = new MessageSubscription();

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        server.subscription.subscribe(server.messages);

        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            //noinspection InfiniteLoopStatement
            do {
                Socket client = serverSocket.accept();
                server.handleClient(client);
            } while (true);
        }
    }

    public void handleClient(Socket client) {
        new Thread(() -> {
            UserSession session = new UserSession();

            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
                writer = new PrintWriter(client.getOutputStream(), true, StandardCharsets.UTF_8);
                session.setWriter(writer);

                UserInteractionScenario scenario = new UserInteractionScenario(session, subscription, messages);
                scenario.start(reader);
            } catch (IOException cause) {
                cause.printStackTrace();
            } finally {
                subscription.unsubscribe(session);
                closeSocket(client, reader, writer);
            }
        }).start();
    }

    private static void closeSocket(Socket client, BufferedReader reader, PrintWriter writer) {
        try {
            client.close();
        } catch (IOException ignore) {
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignore) {
            }
        }
        if (writer != null) {
            writer.close();
        }
    }
}
