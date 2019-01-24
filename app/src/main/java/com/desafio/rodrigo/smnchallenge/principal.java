package com.desafio.rodrigo.smnchallenge;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.desafio.rodrigo.smnchallenge.api.ApiRotas;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.contato.AdapterContatos;
import com.desafio.rodrigo.smnchallenge.contato.Contato;
import com.desafio.rodrigo.smnchallenge.contato.fragment_contatos;
import com.desafio.rodrigo.smnchallenge.inicio.login;
import com.desafio.rodrigo.smnchallenge.loja.classes.Loja;
import com.desafio.rodrigo.smnchallenge.loja.fragment_lojas;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView NomeDrawer;
    TextView EmailDrawerr;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(configuracoes.shared_preference,MODE_PRIVATE);

        //Toast.makeText(this,"OK: "+sharedPreferences.getString(configuracoes.shared_token,""),Toast.LENGTH_SHORT ).show();


        LinearLayout btSair = (LinearLayout) findViewById(R.id.bt_sair);
        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(principal.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja realmente sair?")
                        .setIcon(R.drawable.icone_exit)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor e = sharedPreferences.edit();
                                e.remove(configuracoes.shared_token);
                                e.remove(configuracoes.shared_login);
                                if(e.commit()) {
                                    Intent intent = new Intent(principal.this, login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();







            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        EmailDrawerr = (TextView) headerView.findViewById(R.id.email_usuario);
        NomeDrawer = (TextView) headerView.findViewById(R.id.nome_usuario);


        EmailDrawerr.setText("Rodrigo Pereira Gonçalves");
        NomeDrawer.setText("driguinpg10@gmail.com");
        abrirViewContatos();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_contatos) {
            abrirViewContatos();

        } else if (id == R.id.nav_lojas) {
            Fragment fragment = null;
            fragment = new fragment_lojas();
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame_principal, fragment);
                ft.commitAllowingStateLoss();
                getSupportActionBar().setTitle("Nossas Lojas");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirViewContatos(){
        Fragment fragment = null;
        fragment = new fragment_contatos();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_principal, fragment);
            ft.commitAllowingStateLoss();
            getSupportActionBar().setTitle("Contatos");
        }
    }

   /* public void buscarContatos(){
        String token = "Bearer "+sharedPreferences.getString(configuracoes.shared_token,"");

        ApiRotas Api = ApiRotas.retrofit.create(ApiRotas.class);
        final Call<Contato> callAutenticacao = Api.BuscarContato("81",token);
        callAutenticacao.enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {
                Contato resposta = response.body();
                if (response.isSuccessful()) {
                    EmailDrawerr.setText();
                    NomeDrawer.setText();
                    Toast.makeText(principal.this,"ok"+resposta.getNome(),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(principal.this,"Não listou",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {
                Toast.makeText(principal.this,"Houve um erro",Toast.LENGTH_SHORT).show();
            }
        });
    }*/


}
