package com.vacari.gerupreco.model.notaparana;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowestPrice {

    private int tempo;
    private String local;
    private List<Product> produtos;
    private int total;
    private Price precos;

}
