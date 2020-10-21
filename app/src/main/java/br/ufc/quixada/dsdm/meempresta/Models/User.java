package br.ufc.quixada.dsdm.meempresta.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentId;

public class User {

    @DocumentId
    private String id;

    private String nome;
    private String email;

    private Double avaliacao;

    private Double latitude;
    private Double longitude;

    public User() {}

    public User(String id, String nome, String email, Double avaliacao, LatLng latLng) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.avaliacao = avaliacao;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
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

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
