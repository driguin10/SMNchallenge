package com.desafio.rodrigo.smnchallenge.inicio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.autenticacao.Autenticacao;
import com.desafio.rodrigo.smnchallenge.autenticacao.RetornoAutenticacao;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.novaConta;
import com.desafio.rodrigo.smnchallenge.principal;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {


    TextInputLayout input_email, input_senha;
    Button bt_login,bt_nova_conta;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        input_email = (TextInputLayout) findViewById(R.id.login_email);
        input_senha = (TextInputLayout) findViewById(R.id.login_senha);
        bt_login = (Button) findViewById(R.id.bt_acessar);
        bt_nova_conta = (Button) findViewById(R.id.bt_nova_conta);
        bt_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logar(input_email.getEditText().getText().toString(),input_senha.getEditText().getText().toString());
            }
        });
        bt_nova_conta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(login.this, novaConta.class);
                startActivity(intent);
            }
        });

        if(sharedPreferences.getBoolean(configuracoes.shared_login,false)){
            Intent intent = new Intent(login.this, principal.class);
            startActivity(intent);
            finish();
        }

    }

    public void logar(String email,String senha){
        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setEmail(email);
        autenticacao.setSenha(senha);
        ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
        final Call<RetornoAutenticacao> callAutenticacao = Api.autenticar(autenticacao);
        callAutenticacao.enqueue(new Callback<RetornoAutenticacao>() {
            @Override
            public void onResponse(Call<RetornoAutenticacao> call, Response<RetornoAutenticacao> response) {
                RetornoAutenticacao resposta = response.body();


                if (response.isSuccessful()) {
                    String token = resposta.getToken();
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(configuracoes.shared_token, token);
                    e.putBoolean(configuracoes.shared_login, true);
                    if(e.commit()) {
                        Intent intent = new Intent(login.this, principal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(login.this,"Houve um erro",Toast.LENGTH_SHORT ).show();
                    }
                }
                else
                {
                    try{
                        String retorno = response.errorBody().string().replace("[", "").replace("]", "");
                        Log.d("xex","aq"+response.errorBody().string());
                        JSONObject obj = new JSONObject(retorno);
                        if(obj.has("field"))
                        {
                            if(obj.get("field").toString().equals("email"))
                            {
                                input_email.getEditText().requestFocus();
                                input_email.getEditText().setError("Email não encontrado");
                            }else
                            if(obj.get("field").toString().equals("password"))
                            {
                                input_senha.getEditText().requestFocus();
                                input_senha.getEditText().setError("senha incorreta");
                            }
                        }
                        else{
                            Toast.makeText(login.this,"Houve um erro",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (NullPointerException e){
                        Toast.makeText(login.this,"Houve um erro",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(login.this,"Houve um erro",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RetornoAutenticacao> call, Throwable t) {
                Toast.makeText(login.this,"Houve um erro",Toast.LENGTH_SHORT).show();
            }
        });
    }




}
