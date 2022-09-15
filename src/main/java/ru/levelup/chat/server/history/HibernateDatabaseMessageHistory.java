package ru.levelup.chat.server.history;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateDatabaseMessageHistory implements MessageHistory {
    private final EntityManagerFactory factory;

    public HibernateDatabaseMessageHistory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void addMessage(String login, String message) {
        Message messageRow = new Message(login, new Date(), message);
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(messageRow);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<String> getRecentMessages(int count) {
        if (count < 1) throw new IllegalArgumentException("count should be at least 1");

        EntityManager entityManager = factory.createEntityManager();
        try {
            List<Message> messages = entityManager.createQuery("select m from Message m order by m.messageTime desc", Message.class)
                    .setMaxResults(count)
                    .getResultList();

            List<String> result = new ArrayList<>(messages.size());
            for (Message message : messages) {
                result.add(message.getText());
            }
            return result;
        } finally {
            entityManager.close();
        }
    }

    private List<String> getRecentMessages1(int count) {
        if (count < 1) throw new IllegalArgumentException("count should be at least 1");

        return factory.createEntityManager().createQuery("select m.text from Message m", String.class)
                .setMaxResults(count)
                .getResultList();
    }

    public List<String> getRecentMessages2(int count) {
        if (count < 1) throw new IllegalArgumentException("count should be at least 1");

        return factory.createEntityManager().createQuery("select m from Message m", Message.class)
                .setMaxResults(count)
                .getResultStream()
                .map(Message::getText)
                .collect(Collectors.toList());
    }

}
