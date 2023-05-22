package com.vacari.gerupreco.model.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "notification")
public class Notification {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String barCorde;

    @DatabaseField
    private Long dateType;

    @DatabaseField
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarCorde() {
        return barCorde;
    }

    public void setBarCorde(String barCorde) {
        this.barCorde = barCorde;
    }

    public Long getDateType() {
        return dateType;
    }

    public void setDateType(Long dateType) {
        this.dateType = dateType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
