package com.desafio.rodrigo.smnchallenge.contato;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.desafio.rodrigo.smnchallenge.auxiliares.Loading;
import com.desafio.rodrigo.smnchallenge.auxiliares.Mask;
import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class novoContato extends AppCompatActivity {

    TextInputLayout input_nome, input_telefone;
    Button bt_enviar;
    ImageView imagem,bt_camera2;
    FloatingActionButton bt_camera1;
    Boolean status = false;
    int idContato;
    String token;
    SharedPreferences sharedPreferences;
    AlertDialog alerta;
    static final int imagem_interna = 1;
    static final int imagem_camera = 0;
    Uri cameraImageUri;
    Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);
        sharedPreferences = getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);
        loading = new Loading(this);
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
        bt_camera1=(FloatingActionButton) findViewById(R.id.fab_camera);
        bt_camera2=(ImageView) findViewById(R.id.bt_camera);
        input_telefone.getEditText().addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, input_telefone.getEditText()));

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            idContato = bundle.getInt("id");
            String nomeContato = bundle.getString("nome");
            String telefoneContato = bundle.getString("telefone");
            String imagemContato = bundle.getString("imagem");
            Picasso.get().load(imagemContato).fit().centerCrop().into(imagem);
            input_nome.getEditText().setText(nomeContato);
            input_telefone.getEditText().setText(telefoneContato);
            imagem.setVisibility(View.VISIBLE);
            bt_camera1.setVisibility(View.VISIBLE);
            bt_enviar.setText("Salvar alterações");
            status = true;
        }
        token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");
        bt_enviar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(verificaCampos()) {
                    Contato contato = new Contato();
                    contato.setNome(input_nome.getEditText().getText().toString());
                    contato.setTelefone(input_telefone.getEditText().getText().toString());
                    ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
                    if (!status) {
                        loading.abrir("Aguarde...");
                        final Call<ResponseBody> callAutenticacao = Api.inserirContato(token, contato);
                        callAutenticacao.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                loading.fechar();
                                ResponseBody resposta = response.body();
                                if (response.isSuccessful()) {
                                    Toast.makeText(novoContato.this, "Salvo", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(novoContato.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.fechar();
                                Toast.makeText(novoContato.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        loading.abrir("Aguarde...");
                        final Call<ResponseBody> callAutenticacao = Api.atualizarContato(String.valueOf(idContato), token, contato);
                        callAutenticacao.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                loading.fechar();
                                if (response.isSuccessful()) {
                                    Toast.makeText(novoContato.this, "Atualizado", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(novoContato.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.fechar();
                                Toast.makeText(novoContato.this, "Houve um erro" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }
        });

        bt_camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaImagem();
            }
        });
        bt_camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaImagem();
            }
        });
    }

    public boolean verificaCampos() {
        Boolean status = false;
        String nome = input_nome.getEditText().getText().toString();
        String telefone = input_telefone.getEditText().getText().toString();
        if ( !nome.equals("") && !telefone.equals("")) {
            String telefoneNumero = telefone.replace("(", "")
                    .replace(")", "")
                    .replace("-", "")
                    .replace(" ", "");
            if(telefoneNumero.length()>10){
                status =true;
            }
            else
            {
                input_telefone.getEditText().requestFocus();
                input_telefone.getEditText().setError("Preencha este campo corretamente");
            }

        } else {
            if (nome.equals("")) {
                input_nome.getEditText().requestFocus();
                input_nome.getEditText().setError("Preencha este campo");
            }else
            if (telefone.equals("")) {
                input_telefone.getEditText().requestFocus();
                input_telefone.getEditText().setError("Preencha este campo");
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


    public void EscolhaImagem() {
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.activity_dialog_escolher_imagem, null);

        view.findViewById(R.id.bt_cancela_dialog).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
            }
        });

        view.findViewById(R.id.bt_get_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                cameraImageUri = getTempCameraUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
                startActivityForResult(intent, imagem_camera);

            }
        });

        view.findViewById(R.id.bt_get_galeria).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, imagem_interna);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(novoContato.this);
        builder.setView(view);
        alerta = builder.create();
        alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alerta.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        alerta.dismiss();
        if (resultCode != 0)
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case imagem_interna:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        Picasso.get().load(uri).fit().centerCrop().into(imagem);
                        imagem.setVisibility(View.VISIBLE);
                        bt_camera1.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case imagem_camera:
                if (resultCode == RESULT_OK) {
                    if (cameraImageUri != null) {
                        Picasso.get().load(cameraImageUri).fit().centerCrop().into(imagem);
                        imagem.setVisibility(View.VISIBLE);
                        bt_camera1.setVisibility(View.VISIBLE);
                    }
                }
                break;

        }//fim switch

    }

    private Uri getTempCameraUri() {
        try {
            File file = File.createTempFile("camera", ".jpg", this.getExternalCacheDir());
            return Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

