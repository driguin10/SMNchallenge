package com.desafio.rodrigo.smnchallenge.contato;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.autenticacao.Autenticacao;
import com.desafio.rodrigo.smnchallenge.autenticacao.RetornoAutenticacao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.inicio.login;
import com.desafio.rodrigo.smnchallenge.principal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_contatos extends Fragment {
    FloatingActionButton fabAddContato;
    LinearLayoutManager layout;
    RecyclerView lista;
    AdapterContatos adapter;
    List<Contato> ListaContatos = new ArrayList<>();
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_contatos, container, false);
        sharedPreferences = v.getContext().getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        fabAddContato = (FloatingActionButton) v.findViewById(R.id.fab_add_contato);
        fabAddContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),novoContato.class);
                startActivityForResult(intent,1);
            }
        });


        lista = (RecyclerView) v.findViewById(R.id.view_contatos);
        lista.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        lista.setLayoutManager(layout);

        listarContatos();



        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int ResultCode, Intent intent) {
        listarContatos();
    }

    public void listarContatos(){
        String token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");

        ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
        final Call<List<Contato>> callAutenticacao = Api.listarContatos(token);
        callAutenticacao.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                List<Contato> resposta = response.body();
                if (response.isSuccessful()) {
                    adapter = new AdapterContatos(resposta, getContext() ,lista);
                    lista.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Não listou",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                Toast.makeText(getContext(),"Houve um erro",Toast.LENGTH_SHORT).show();
            }
        });
    }


}

