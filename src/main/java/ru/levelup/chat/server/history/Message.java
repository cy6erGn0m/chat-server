package ru.levelup.chat.server.history;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sender_name", nullable = false, length = 50)
    private String sender;

    @Column(name = "message_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageTime;

    @Column(nullable = false, length = 1000)
    private String text;

    public Message() {
    }

    public Message(String sender, Date messageTime, String text) {
        this.sender = sender;
        this.messageTime = messageTime;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
