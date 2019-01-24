package com.desafio.rodrigo.smnchallenge.resumo;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.desafio.rodrigo.smnchallenge.R;
import com.desafio.rodrigo.smnchallenge.contato.fragment_contatos;
import com.desafio.rodrigo.smnchallenge.loja.fragment_lojas;

public class resumoAtividades extends AppCompatActivity {
    private com.desafio.rodrigo.smnchallenge.resumo.adapterTabResumo adapterTabResumo;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_atividades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_resumo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Resumo de atividades");
        Drawable upArrow = ContextCompat.getDrawable(resumoAtividades.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(resumoAtividades.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        adapterTabResumo = new adapterTabResumo(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_resumo);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapterTabResumo adapter = new adapterTabResumo(getSupportFragmentManager());
        adapter.addFragment(new fragment_lojas(), "Relatório 2018");
        adapter.addFragment(new fragment_contatos(), "Dashboard");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        finish();
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



