package com.desafio.rodrigo.smnchallenge.contato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.autenticacao.Autenticacao;
import com.desafio.rodrigo.smnchallenge.autenticacao.RetornoAutenticacao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.inicio.login;
import com.desafio.rodrigo.smnchallenge.principal;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class novoContato extends AppCompatActivity {
    TextInputLayout input_nome, input_telefone;
    Button bt_enviar;
    ImageView imagem;
    FloatingActionButton bt_camera;
    Boolean status = false;
    int idContato;
    String token;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);
        sharedPreferences = getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contato);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Novo Contato");
        Drawable upArrow = ContextCompat.getDrawable(novoContato.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(novoContato.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        input_nome = (TextInputLayout) findViewById(R.id.contato_nome);
        input_telefone = (TextInputLayout) findViewById(R.id.contato_telefone);
        bt_enviar = (Button) findViewById(R.id.bt_enviar);
        imagem=(ImageView) findViewById(R.id.img_contato);
        bt_camera=(FloatingActionButton) findViewById(R.id.fab_camera);




        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            idContato = bundle.getInt("id");
            String nomeContato = bundle.getString("nome");
            String telefoneContato = bundle.getString("telefone");
            String imagemContato = bundle.getString("imagem");
            Picasso.get().load(imagemContato).into(imagem);
            input_nome.getEditText().setText(nomeContato);
            input_telefone.getEditText().setText(telefoneContato);
            imagem.setVisibility(View.VISIBLE);
            bt_camera.setVisibility(View.VISIBLE);
            bt_enviar.setText("Salvar alterações");
            status = true;
        }
         token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Contato contato = new Contato();
                contato.setNome(input_nome.getEditText().getText().toString());
                contato.setTelefone(input_telefone.getEditText().getText().toString());
                ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
                if(!status) {
                    final Call<ResponseBody> callAutenticacao = Api.InserirContato(token,contato);
                    callAutenticacao.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody resposta = response.body();
                            if (response.isSuccessful()) {
                                Toast.makeText(novoContato.this, "Salvo - " , Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(novoContato.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Log.d("xex","err:"+ t.getMessage().toString());
                        }
                    });
                }
                else
                {
                    final Call<ResponseBody> callAutenticacao = Api.AtualizarContato(String.valueOf(idContato),token,contato);
                    callAutenticacao.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(novoContato.this, "Atualizado", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(novoContato.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(novoContato.this, "Houve um erro"+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

/*
*
* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                 //some operation
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //some operation
                }
            });
            EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            findViewById(R.id.default_title).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
* */
