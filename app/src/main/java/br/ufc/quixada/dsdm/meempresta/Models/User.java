package br.ufc.quixada.dsdm.meempresta.Models;

import com.google.firebase.firestore.DocumentId;

public class User {

    @DocumentId
    private String id;

    private String nome;
    private String email;

    private double avaliacao;

    public User() {}

    public User(String id, String nome, String email, double avaliacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.avaliacao = avaliacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return nome;
    }
}
