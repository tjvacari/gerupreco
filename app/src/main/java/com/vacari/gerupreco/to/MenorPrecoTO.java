package com.vacari.gerupreco.to;

import java.util.List;

public class MenorPrecoTO {

    private int tempo;
    private String local;
    private List<Produto> produtos;
    private int total;
    private Precos precos;

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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Precos getPrecos() {
        return precos;
    }

    public void setPrecos(Precos precos) {
        this.precos = precos;
    }
}
