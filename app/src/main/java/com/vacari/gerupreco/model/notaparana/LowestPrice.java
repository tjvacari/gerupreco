package com.vacari.gerupreco.model.notaparana;

import java.util.List;

public class LowestPrice {

    private int tempo;
    private String local;
    private List<Product> produtos;
    private int total;
    private Price precos;

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<Product> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Product> produtos) {
        this.produtos = produtos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Price getPrecos() {
        return precos;
    }

    public void setPrecos(Price precos) {
        this.precos = precos;
    }
}
