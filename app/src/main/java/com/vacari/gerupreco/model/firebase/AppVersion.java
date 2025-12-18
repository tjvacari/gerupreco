package com.vacari.gerupreco.model.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class AppVersion {

    private int versionCode;
    private String url;

}
