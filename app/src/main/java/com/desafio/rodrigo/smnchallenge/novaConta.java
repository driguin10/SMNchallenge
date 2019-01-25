package com.desafio.rodrigo.smnchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.autenticacao.Autenticacao;
import com.desafio.rodrigo.smnchallenge.autenticacao.RetornoAutenticacao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.contato.novoContato;
import com.desafio.rodrigo.smnchallenge.inicio.login;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class novaConta extends AppCompatActivity {
    TextInputLayout usuario_nome, usuario_email,usuario_senha;
    Button bt_cadastrar;
    SharedPreferences sharedPreferences;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_nova_conta);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Nova Conta");
        Drawable upArrow = ContextCompat.getDrawable(novaConta.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(novaConta.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        sharedPreferences = getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");
        usuario_nome = (TextInputLayout) findViewById(R.id.usuario_nome);
        usuario_email = (TextInputLayout) findViewById(R.id.usuario_email);
        usuario_senha = (TextInputLayout) findViewById(R.id.usuario_senha);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setEmail(usuario_email.getEditText().getText().toString());
                usuario.setUsername(usuario_nome.getEditText().getText().toString());
                usuario.setPassword(usuario_senha.getEditText().getText().toString());
                ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
                final Call<ResponseBody> callAutenticacao = Api.InserirUsuario(token,usuario);
                callAutenticacao.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody resposta = response.body();


                        if (response.isSuccessful()) {
                                Toast.makeText(novaConta.this,"Salvo",Toast.LENGTH_SHORT ).show();

                        }
                        else
                        {
                            Toast.makeText(novaConta.this,"Houve um erro",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(novaConta.this,"Houve um erro",Toast.LENGTH_SHORT).show();
                    }
                });
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
