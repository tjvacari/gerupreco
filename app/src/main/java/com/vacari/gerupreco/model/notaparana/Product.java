package com.vacari.gerupreco.model.notaparana;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private String id;
    private String local;
    private String desc;
    private String ncm;
    private String cdanp;
    private String valor_desconto;
    private String valor_tabela;
    private String valor;
    private Date datahora;
    private String tempo;
    private String distkm;
    private String gtin;
    private String nrdoc;
    private Company estabelecimento;

}
