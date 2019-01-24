package com.desafio.rodrigo.smnchallenge.contato;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contato {

    @SerializedName("id")
    @Expose()
    private Integer id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("telefone")
    private String telefone;

    @SerializedName("user_id")
    @Expose()
    private Integer userId;
    @SerializedName("created_at")
    @Expose()
    private String createdAt;
    @SerializedName("updated_at")
    @Expose()
    private String updatedAt;
    @SerializedName("imagem")
    @Expose()
    private String imagem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}
