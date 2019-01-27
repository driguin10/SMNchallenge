package com.desafio.rodrigo.smnchallenge.loja;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.desafio.rodrigo.smnchallenge.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class informacoes extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener, LocationListener {
    TextView loja_nome, loja_descricao,loja_telefone,loja_site,loja_tipo;
    FloatingActionButton bt_mapa;
    String latitude,longitude,nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_informacoes);

        loja_nome = (TextView) findViewById(R.id.loja_nome);
        loja_descricao = (TextView) findViewById(R.id.loja_descricao);
        loja_telefone = (TextView) findViewById(R.id.loja_telefone);
        loja_site = (TextView) findViewById(R.id.loja_site);
        loja_tipo = (TextView) findViewById(R.id.loja_tipo);

        bt_mapa=(FloatingActionButton) findViewById(R.id.fab_mapa);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            loja_nome.setText( bundle.getString("nome"));
            nome = bundle.getString("nome");
            loja_telefone.setText( bundle.getString("telefone"));
            loja_descricao.setText( bundle.getString("descricao"));
            loja_tipo.setText( bundle.getString("tipo"));
            loja_site.setText( bundle.getString("site"));
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        bt_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geo = "geo:"+ latitude +","+longitude;
                Uri gmmIntentUri = Uri.parse(geo);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng userLocation = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        map.addMarker(new MarkerOptions().position(userLocation).title(nome));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16.0f));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}

