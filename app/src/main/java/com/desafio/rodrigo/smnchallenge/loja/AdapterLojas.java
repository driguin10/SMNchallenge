package com.desafio.rodrigo.smnchallenge.loja;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.contato.Contato;
import com.desafio.rodrigo.smnchallenge.informacoes;
import com.desafio.rodrigo.smnchallenge.loja.classes.Atividade;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.desafio.rodrigo.smnchallenge.resumo.resumoAtividades;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterLojas extends RecyclerView.Adapter<AdapterLojas.ViewHolder>{
    private List<Loja> Lista;
    private Context context;

    public AdapterLojas(List<Loja> lista, Context c) {
        context = c;
        Lista = lista;
    }

    @Override
    public AdapterLojas.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.estilo_lista_lojas, viewGroup, false);
        return new AdapterLojas.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterLojas.ViewHolder holder, int position) {
        final Loja item = Lista.get(position);
        holder.nomeLoja.setText(item.getNome());
        holder.tipoLoja.setText(item.getTipo());
        holder.descricaoLoja.setText(item.getDescricao());

        holder.btDetelhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, informacoes.class);

                intent.putExtra("nome",item.getNome());
                intent.putExtra("tipo",item.getTipo());
                intent.putExtra("descricao",item.getDescricao());
                intent.putExtra("telefone",item.getTelefone());
                intent.putExtra("site",item.getSite());
                intent.putExtra("latitude",item.getLatitude());
                intent.putExtra("longitude",item.getLongitude());
                context.startActivity(intent);
            }
        });
        holder.btResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, resumoAtividades.class);
                intent.putExtra("atividades",(Serializable)item.getAtividades());
                context.startActivity(intent);

            }
        });
        holder.btFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeLoja;
        protected TextView tipoLoja;
        protected TextView descricaoLoja;
        protected Button btDetelhes;
        protected Button btResumo;
        protected ImageView btFavorito;
        protected ImageView btCompartilhar;
        public ViewHolder(View itemView) {
            super(itemView);
            nomeLoja = (TextView) itemView.findViewById(R.id.nome_Loja);
            tipoLoja = (TextView) itemView.findViewById(R.id.tipo_loja);
            descricaoLoja = (TextView) itemView.findViewById(R.id.descricao_loja);

            btDetelhes = (Button) itemView.findViewById(R.id.bt_detelhes);
            btResumo = (Button) itemView.findViewById(R.id.bt_resumo);
            btFavorito = (ImageView) itemView.findViewById(R.id.bt_favorito);
            btCompartilhar = (ImageView) itemView.findViewById(R.id.bt_compartilhar);
        }
    }


}
