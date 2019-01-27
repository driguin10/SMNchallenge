package com.desafio.rodrigo.smnchallenge.loja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.auxiliares.Permissao;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.desafio.rodrigo.smnchallenge.resumo.resumoAtividades;
import java.io.Serializable;
import java.util.List;

public class AdapterLojas extends RecyclerView.Adapter<AdapterLojas.ViewHolder>{
    private List<Loja> Lista;
    private Context context;
    private  boolean flagFavorito;
    Resources resources;



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
        resources = holder.resources;

        holder.btDetelhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Permissao permissao = new Permissao((Activity)context);
                if(permissao.validaPermissoes((Activity)context)) {
                    Intent intent = new Intent(context, informacoes.class);
                    intent.putExtra("nome", item.getNome());
                    intent.putExtra("tipo", item.getTipo());
                    intent.putExtra("descricao", item.getDescricao());
                    intent.putExtra("telefone", item.getTelefone());
                    intent.putExtra("site", item.getSite());
                    intent.putExtra("latitude", item.getLatitude());
                    intent.putExtra("longitude", item.getLongitude());
                    context.startActivity(intent);
                }
            }
        });
        holder.btResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, resumoAtividades.class);
                intent.putExtra("atividades",(Serializable)item.getAtividades());
                intent.putExtra("classe", (Serializable)item);
                context.startActivity(intent);
            }
        });
        holder.btFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( flagFavorito) {
                    holder.btFavorito.setImageResource(R.drawable.icone_favoritar);

                }else
                {
                    holder.btFavorito.setImageResource(R.drawable.icone_favorito);
                }
                flagFavorito = !flagFavorito;
            }
        });
        holder.btCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String texto = "Conheça a SMN " + item.getSite();
                sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
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
        Resources resources;
        public ViewHolder(View itemView) {
            super(itemView);
            resources = itemView.getContext().getResources();
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
