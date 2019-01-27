package com.desafio.rodrigo.smnchallenge.contato;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.auxiliares.Loading;
import com.desafio.rodrigo.smnchallenge.auxiliares.Permissao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdapterContatos extends RecyclerView.Adapter<AdapterContatos.ViewHolder>{
    private List<Contato> Lista;
    private Context context;
    private RecyclerView recyclerV;
    SharedPreferences sharedPreferences;
    Loading loading;

    public AdapterContatos(List<Contato> lista, Context c,RecyclerView recicler) {
        context = c;
        Lista = lista;
        recyclerV = recicler;
        sharedPreferences = c.getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        loading = new Loading((Activity)c);
    }

    @Override
    public AdapterContatos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.estilo_lista_contatos, viewGroup, false);
        return new AdapterContatos.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterContatos.ViewHolder holder, final int position) {
        final Contato item = Lista.get(position);

        holder.nomeContato.setText(item.getNome());
        holder.telefone.setText(item.getTelefone());
        Picasso.get().load(item.getImagem()).into(holder.imagem);
        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Permissao permissao = new Permissao((Activity)context);
                if (permissao.validaPermissoes((Activity)context)) {
                        Intent intent = new Intent(context, novoContato.class);
                        intent.putExtra("id", item.getId());
                        intent.putExtra("nome", item.getNome());
                        intent.putExtra("telefone", item.getTelefone());
                        intent.putExtra("imagem", item.getImagem());
                        context.startActivity(intent);
                }
            }
        });
        holder.cardContato.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir este contato?")
                        .setIcon(R.drawable.icone_apagar)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loading.abrir("Aguarde...");
                                String token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");
                                ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
                                final Call<ResponseBody> callAutenticacao = Api.apagarContato(String.valueOf(item.getId()),token);
                                callAutenticacao.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            loading.fechar();
                                            ResponseBody resposta = response.body();
                                            if (response.isSuccessful()) {
                                                Toast.makeText(context, "Excluido" , Toast.LENGTH_SHORT).show();
                                                Lista.remove(position);
                                                recyclerV.setAdapter(new AdapterContatos(Lista,context,recyclerV));

                                            } else {
                                                Toast.makeText(context, "Houve um erro", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            loading.fechar();
                                            Toast.makeText(context, "Houve um erro", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeContato;
        protected TextView telefone;
        protected CircleImageView imagem;
        protected ImageView btEditar;
        protected CardView cardContato;
        public ViewHolder(View itemView) {
            super(itemView);
            nomeContato = (TextView) itemView.findViewById(R.id.nome_contato);
            telefone = (TextView) itemView.findViewById(R.id.telefone_contato);
            imagem = (CircleImageView) itemView.findViewById(R.id.imagem_contato);
            btEditar = (ImageView) itemView.findViewById(R.id.bt_editar);
            cardContato = (CardView) itemView.findViewById(R.id.card_contato);

        }
    }


}
