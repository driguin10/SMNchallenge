package com.desafio.rodrigo.smnchallenge.api;

import com.desafio.rodrigo.smnchallenge.usuario.Usuario;
import com.desafio.rodrigo.smnchallenge.autenticacao.Autenticacao;
import com.desafio.rodrigo.smnchallenge.autenticacao.RetornoAutenticacao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.contato.Contato;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRotas {

    //------------------------AUTENTICACAO---------------------------------

    @POST("/autenticacao")
    Call<RetornoAutenticacao> autenticar(@Body Autenticacao dados);

    //------------------------LOJAS----------------------------------------

    @GET("/lojas")
    Call<List<Loja>> listarLojas(@Header("Authorization") String authKey);

    @GET("/lojas/{id}")
    Call<Loja> buscarLoja(@Path("id") String id,
            @Header("Authorization") String authKey);

    //--------------------------CONTATOS---------------------------------------


    @GET("/contatos")
    Call<List<Contato>> listarContatos(@Header("Authorization") String authKey);


    @GET("/contatos/{id}")
    Call<Contato> buscarContato(@Path("id") String id,
                                @Header("Authorization") String authKey);

    @POST("/contatos")
    Call<ResponseBody> inserirContato(@Header("Authorization") String authKey,
                                        @Body Contato dados);

    @PUT("/contatos/{id}")
    Call<ResponseBody> atualizarContato(@Path("id") String id,
                                        @Header("Authorization") String authKey,
                                        @Body Contato dados);


    @DELETE("/contatos/{id}")
    Call<ResponseBody> apagarContato(@Path("id") String id,
                                     @Header("Authorization") String authKey);

    //------------------------------USUARIOS--------------------------------------

    @POST("/usuarios")
    Call<ResponseBody> inserirUsuario(@Body Usuario dados);

    @GET("/usuarios/{id}")
    Call<List<Usuario>> buscaUsuario(@Path("id") String id,
                                    @Header("Authorization") String authKey);
    //-----------------------------------------------------------------------------



    //******************* SERVICE RETROFIT ******************************
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(configuracoes.webService)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    //*******************************************************************
}
