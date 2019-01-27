
package com.desafio.rodrigo.smnchallenge.loja.classes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Loja implements Serializable {
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("nome")
  @Expose
  private String nome;
  @SerializedName("tipo")
  @Expose
  private String tipo;
  @SerializedName("descricao")
  @Expose
  private String descricao;
  @SerializedName("telefone")
  @Expose
  private String telefone;
  @SerializedName("site")
  @Expose
  private String site;
  @SerializedName("latitude")
  @Expose
  private String latitude;
  @SerializedName("longitude")
  @Expose
  private String longitude;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  @SerializedName("atividades")
  @Expose
  private List<Atividade> atividades = null;

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

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
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

  public List<Atividade> getAtividades() {
    return atividades;
  }

  public void setAtividades(List<Atividade> atividades) {
    this.atividades = atividades;
  }
}
