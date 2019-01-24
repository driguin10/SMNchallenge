package com.desafio.rodrigo.smnchallenge.autenticacao;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RetornoAutenticacao {

    @SerializedName("token")
    @Expose(serialize = true, deserialize = true)
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
