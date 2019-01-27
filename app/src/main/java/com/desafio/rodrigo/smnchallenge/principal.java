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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.desafio.rodrigo.smnchallenge.configuracoes.configuracoes;
import com.desafio.rodrigo.smnchallenge.contato.fragment_contatos;
import com.desafio.rodrigo.smnchallenge.inicio.login;
import com.desafio.rodrigo.smnchallenge.loja.fragment_lojas;

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

        LinearLayout btSair = (LinearLayout) findViewById(R.id.bt_sair);
        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new AlertDialog.Builder(principal.this)
                        .setTitle("Sair")
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


        //dados era para vim da api --
        EmailDrawerr.setText("Rodrigo Pereira Gonçalves");
        NomeDrawer.setText("driguinpg10@gmail.com");
        carregaViewContatos("");
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
        getMenuInflater().inflate(R.menu.principal, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // carregaViewContatos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                carregaViewContatos(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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


                carregaViewContatos("");



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

    public void carregaViewContatos(String texto){
        Fragment fragment = null;
        fragment = new fragment_contatos();
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("texto", texto);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame_principal, fragment);
            ft.commitAllowingStateLoss();
            getSupportActionBar().setTitle("Contatos");
        }
    }

}
