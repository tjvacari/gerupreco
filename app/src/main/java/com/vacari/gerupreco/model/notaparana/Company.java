package com.vacari.gerupreco.model.notaparana;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {

    private String codigo;
    private String nm_fan;
    private String nm_emp;
    private String tp_logr;
    private String nm_logr;
    private String nr_logr;
    private String complemento;
    private String bairro;
    private String mun;
    private String uf;
    private String mesoreg;
    private String microreg;

    public String getFullAddress() {
        StringBuilder address = new StringBuilder();
        address.append(getNm_logr());
        address.append(", ");
        address.append(getNr_logr());
        address.append(" - ");
        address.append(getBairro());

        return address.toString();
    }
}
