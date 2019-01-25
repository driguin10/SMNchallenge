package com.desafio.rodrigo.smnchallenge.relatorio;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.loja.AdapterLojas;
import com.desafio.rodrigo.smnchallenge.loja.classes.Atividade;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.desafio.rodrigo.smnchallenge.relatorio.AdapterRelatorio;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class fragment_relatorio extends Fragment {
    SharedPreferences sharedPreferences;
    LinearLayoutManager layout;
    RecyclerView lista;
    AdapterRelatorio adapter;
    List<Atividade> ListaAtividade = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment_relatorio, container, false);
        sharedPreferences =  v.getContext().getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        lista = (RecyclerView) v.findViewById(R.id.view_atividades);
        lista.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        lista.setLayoutManager(layout);
        Bundle bundle = getActivity().getIntent().getExtras();
        ListaAtividade = (List<Atividade>) bundle.getSerializable("atividades");
      if(ListaAtividade!=null) {
           adapter = new AdapterRelatorio(ListaAtividade, getContext());
           lista.setAdapter(adapter);
       }
        return v;
    }


}
