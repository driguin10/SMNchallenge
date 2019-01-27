package com.desafio.rodrigo.smnchallenge.loja;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.auxiliares.Loading;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_lojas extends Fragment {
    SharedPreferences sharedPreferences;
    LinearLayoutManager layout;
    RecyclerView recyclerV;
    AdapterLojas adapter;
    Loading loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_fragment_lojas, container, false);
        sharedPreferences =  v.getContext().getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        loading = new Loading(getActivity());
        recyclerV = (RecyclerView) v.findViewById(R.id.view_lojas);
        recyclerV.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(layout);
        String token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");
        loading.abrir("Aguarde...");
        ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
        final Call<List<Loja>> callAutenticacao = Api.listarLojas(token);
        callAutenticacao.enqueue(new Callback<List<Loja>>() {
            @Override
            public void onResponse(Call<List<Loja>> call, Response<List<Loja>> response) {
                loading.fechar();
                List<Loja> resposta = response.body();
                if (response.isSuccessful()) {
                   adapter = new AdapterLojas(resposta, getContext());
                    recyclerV.setAdapter(adapter);
                }
                else
                {
                    Toast.makeText(getContext(),"Erro ao listar",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Loja>> call, Throwable t) {
                loading.fechar();
                Toast.makeText(getContext(),"Houve um erro",Toast.LENGTH_SHORT).show();
            }
        });

        return  v;
    }
}
