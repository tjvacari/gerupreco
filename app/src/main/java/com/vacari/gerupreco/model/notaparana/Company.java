package com.vacari.gerupreco.model.notaparana;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNm_fan() {
        return nm_fan;
    }

    public void setNm_fan(String nm_fan) {
        this.nm_fan = nm_fan;
    }

    public String getNm_emp() {
        return nm_emp;
    }

    public void setNm_emp(String nm_emp) {
        this.nm_emp = nm_emp;
    }

    public String getTp_logr() {
        return tp_logr;
    }

    public void setTp_logr(String tp_logr) {
        this.tp_logr = tp_logr;
    }

    public String getNm_logr() {
        return nm_logr;
    }

    public void setNm_logr(String nm_logr) {
        this.nm_logr = nm_logr;
    }

    public String getNr_logr() {
        return nr_logr;
    }

    public void setNr_logr(String nr_logr) {
        this.nr_logr = nr_logr;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMun() {
        return mun;
    }

    public void setMun(String mun) {
        this.mun = mun;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getMesoreg() {
        return mesoreg;
    }

    public void setMesoreg(String mesoreg) {
        this.mesoreg = mesoreg;
    }

    public String getMicroreg() {
        return microreg;
    }

    public void setMicroreg(String microreg) {
        this.microreg = microreg;
    }

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
