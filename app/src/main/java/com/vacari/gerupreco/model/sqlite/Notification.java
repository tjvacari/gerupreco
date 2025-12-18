package com.vacari.gerupreco.model.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
