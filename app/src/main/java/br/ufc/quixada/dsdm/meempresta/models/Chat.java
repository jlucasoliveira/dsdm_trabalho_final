package br.ufc.quixada.dsdm.meempresta.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {

    private String id;
    private String usuarioName;
    private String lastMessage;
    private Date data;
    private static List<Chat> chats = new ArrayList<>();

    static public List<Chat> getChats() {
        for (int i = 0; i <= 25; i++){
            String ist = String.valueOf(i);
            chats.add(new Chat(ist, ist, ist, new Date()));
        }
        return chats;
    }

    public Chat() {}

    public Chat(String id, String usuarioName, String lastMessage, Date data) {
        this.id = id;
        this.usuarioName = usuarioName;
        this.lastMessage = lastMessage;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioName() {
        return usuarioName;
    }

    public void setUsuarioName(String usuarioName) {
        this.usuarioName = usuarioName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
