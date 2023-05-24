package com.vacari.gerupreco.model.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "notification")
public class Notification {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String barCorde;

    @DatabaseField
    private String description;

    @DatabaseField
    private Long dateType;

    @DatabaseField
    private BigDecimal lastLowestPrice;

    @DatabaseField
    private BigDecimal targetPrice;

    @DatabaseField
    private Long date;

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

    public BigDecimal getLastLowestPrice() {
        return lastLowestPrice;
    }

    public void setLastLowestPrice(BigDecimal lastLowestPrice) {
        this.lastLowestPrice = lastLowestPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }
}
