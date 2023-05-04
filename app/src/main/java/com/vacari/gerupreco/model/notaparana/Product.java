package com.vacari.gerupreco.model.notaparana;

import java.util.Date;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCdanp() {
        return cdanp;
    }

    public void setCdanp(String cdanp) {
        this.cdanp = cdanp;
    }

    public String getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(String valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public String getValor_tabela() {
        return valor_tabela;
    }

    public void setValor_tabela(String valor_tabela) {
        this.valor_tabela = valor_tabela;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDistkm() {
        return distkm;
    }

    public void setDistkm(String distkm) {
        this.distkm = distkm;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getNrdoc() {
        return nrdoc;
    }

    public void setNrdoc(String nrdoc) {
        this.nrdoc = nrdoc;
    }

    public Company getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Company estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
