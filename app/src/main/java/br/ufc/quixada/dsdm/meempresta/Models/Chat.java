package br.ufc.quixada.dsdm.meempresta.Models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class Chat {

    @DocumentId
    private String id;
    private String message;
    private String sender;
    private String receiver;
    private String request;
    private Timestamp instant;

    public Chat() {}

    public Chat(String id, String sender, String receiver, String message, String request, Timestamp instant) {
        this.id = id;
        this.message = message;
        this.instant = instant;
        this.sender = sender;
        this.request = request;
        this.receiver = receiver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Timestamp getInstant() {
        return instant;
    }

    public void setInstant(Timestamp instant) {
        this.instant = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        return getId().equals(chat.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
