package com.desafio.rodrigo.smnchallenge.loja.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Atividade {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mes")
    @Expose
    private String mes;
    @SerializedName("entrada")
    @Expose
    private Double entrada;
    @SerializedName("saida")
    @Expose
    private Integer saida;
    @SerializedName("loja_id")
    @Expose
    private Integer lojaId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Integer getSaida() {
        return saida;
    }

    public void setSaida(Integer saida) {
        this.saida = saida;
    }

    public Integer getLojaId() {
        return lojaId;
    }

    public void setLojaId(Integer lojaId) {
        this.lojaId = lojaId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
