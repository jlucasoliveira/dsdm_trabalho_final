package br.ufc.quixada.dsdm.meempresta.Models;

import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestStatus;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentId;
import br.ufc.quixada.dsdm.meempresta.Models.enums.RequestType;

public class Request {

    @DocumentId
    private String id;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private Timestamp date;
    private String owner;
    private String resolver;
    private Double latitude;
    private Double longitude;

    public Request() {}

    public Request(String id, String title, String description, Timestamp date, RequestType type,
                   RequestStatus status, Double longitude, Double latitude, String owner, String resolver) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type.getCode();
        this.status = status.getCode();
        this.owner = owner;
        this.longitude = longitude;
        this.latitude = latitude;
        this.resolver = resolver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        return getId().equals(request.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


}
