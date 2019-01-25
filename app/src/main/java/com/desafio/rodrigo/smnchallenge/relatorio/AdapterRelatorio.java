package com.desafio.rodrigo.smnchallenge.relatorio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.informacoes;
import com.desafio.rodrigo.smnchallenge.loja.classes.Atividade;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.desafio.rodrigo.smnchallenge.resumo.resumoAtividades;

import java.util.List;

public class AdapterRelatorio extends RecyclerView.Adapter<AdapterRelatorio.ViewHolder>{
    private List<Atividade> Lista;
    private Context context;

    public AdapterRelatorio(List<Atividade> lista, Context c) {
        context = c;
        Lista = lista;
    }

    @Override
    public AdapterRelatorio.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.estilo_relatorio, viewGroup, false);
        return new AdapterRelatorio.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterRelatorio.ViewHolder holder, int position) {
        final Atividade item = Lista.get(position);
        holder.entrada.setText("Entradas: R$"+String.valueOf(item.getEntrada()));
        holder.saida.setText("Saidas: R$"+String.valueOf(item.getSaida()));
        holder.mes.setText(String.valueOf(item.getMes()));
        int cor = item.getEntrada().compareTo(item.getSaida());
        switch (cor){
            case 0:
                //neutro
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.corNeutro));
                break;
            case 1:
                //verde maior
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.corVerde));
                break;

            case -1:
                holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.corVermelho));
                //menor
                break;
        }




        holder.card.setOnClickListener(new View.OnClickListener() {
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
        protected TextView entrada;
        protected TextView saida;
        protected TextView mes;
        protected CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            entrada = (TextView) itemView.findViewById(R.id.input_entrada);
            saida = (TextView) itemView.findViewById(R.id.input_saida);
            mes = (TextView) itemView.findViewById(R.id.input_mes);
            card = (CardView) itemView.findViewById(R.id.card_relatorio);
        }
    }


}
