package com.desafio.rodrigo.smnchallenge.usuario;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.auxiliares.Loading;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class novaConta extends AppCompatActivity {
    TextInputLayout usuario_nome, usuario_email,usuario_senha;
    Button bt_cadastrar;
    SharedPreferences sharedPreferences;
    Loading loading;
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
        loading = new Loading(this);
        usuario_nome = (TextInputLayout) findViewById(R.id.usuario_nome);
        usuario_email = (TextInputLayout) findViewById(R.id.usuario_email);
        usuario_senha = (TextInputLayout) findViewById(R.id.usuario_senha);
        bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (verificaCampos()) {
                    Usuario usuario = new Usuario();
                    usuario.setEmail(usuario_email.getEditText().getText().toString());
                    usuario.setUsername(usuario_nome.getEditText().getText().toString());
                    usuario.setPassword(usuario_senha.getEditText().getText().toString());
                    loading.abrir("Aguarde...");
                    ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
                    final Call<ResponseBody> callAutenticacao = Api.inserirUsuario(usuario);
                    callAutenticacao.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            loading.fechar();
                            ResponseBody resposta = response.body();

                            if (response.isSuccessful()) {
                                Toast.makeText(novaConta.this, "Salvo", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(novaConta.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                                try {
                                    Toast.makeText(novaConta.this, "erro" + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) { }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            loading.fechar();
                            Toast.makeText(novaConta.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public boolean verificaCampos() {
        Boolean status = false;
        String Email = usuario_email.getEditText().getText().toString();
        String Senha = usuario_senha.getEditText().getText().toString();
        String Nome = usuario_nome.getEditText().getText().toString();
        if (!Email.equals("") && !Senha.equals("") && !Nome.equals("")) {
            if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                usuario_email.getEditText().setError("Insira um email valido!!");
                usuario_email.getEditText().requestFocus();
            } else if (Senha.length() >= 8) {
                status = true;
            } else {
                usuario_senha.getEditText().requestFocus();
                usuario_senha.getEditText().setError("A senha deve ter o minimo de 8 caracteres !!");
                status = false;
            }
        } else {
             if (Nome.equals("")) {
                 usuario_nome.getEditText().requestFocus();
                 usuario_nome.getEditText().setError("Preencha este campo");
            }else
            if (Email.equals("")) {
                usuario_email.getEditText().requestFocus();
                usuario_email.getEditText().setError("Preencha este campo");
            } else if (Senha.equals("")) {
                usuario_senha.getEditText().requestFocus();
                usuario_senha.getEditText().setError("Preencha este campo");
            }

            status = false;
        }
        return status;
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
