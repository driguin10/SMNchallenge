package com.desafio.rodrigo.smnchallenge.loja.relatorio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.loja.classes.Atividade;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class fragment_relatorio extends Fragment {
    SharedPreferences sharedPreferences;
    LinearLayoutManager layout;
    RecyclerView recyclerV;
    AdapterRelatorio adapter;
    List<Atividade> ListaAtividade = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_fragment_relatorio, container, false);
        sharedPreferences =  v.getContext().getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        recyclerV = (RecyclerView) v.findViewById(R.id.view_atividades);
        recyclerV.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(layout);
        Bundle bundle = getActivity().getIntent().getExtras();
        ListaAtividade = (List<Atividade>) bundle.getSerializable("atividades");
        Loja classAtividade = (Loja) bundle.getSerializable("classe");

          if(classAtividade.getAtividades()!=null) {
               adapter = new AdapterRelatorio(classAtividade.getAtividades(), getContext());
              recyclerV.setAdapter(adapter);
           }
        return v;
    }
}
