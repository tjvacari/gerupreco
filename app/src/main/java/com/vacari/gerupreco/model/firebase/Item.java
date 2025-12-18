package com.vacari.gerupreco.model.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item {

    private String id;
    private String barCode;
    private String description;
    private String size;
    private String unitMeasure;

}
