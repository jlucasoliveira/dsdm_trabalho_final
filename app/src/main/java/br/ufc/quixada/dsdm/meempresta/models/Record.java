package br.ufc.quixada.dsdm.meempresta.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Record {

    private String id;
    private String objectName;
    private String objectOwner;
    private Date data;

    static List<Record> records = new ArrayList<>();

    public static List<Record> getRecords() {
        for (int i = 0; i <= 15; i++){
            String i_St = String.valueOf(i);
            records.add(new Record(i_St, i_St, i_St, new Date()));
        }
        return records;
    }

    public Record() {}

    public Record(String id, String objectName, String objectOwner, Date data) {
        this.id = id;
        this.objectName = objectName;
        this.objectOwner = objectOwner;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectOwner() {
        return objectOwner;
    }

    public void setObjectOwner(String objectOwner) {
        this.objectOwner = objectOwner;
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

        Record record = (Record) o;

        return getId() != null ? getId().equals(record.getId()) : record.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
