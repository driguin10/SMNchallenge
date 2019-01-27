package com.desafio.rodrigo.smnchallenge.auxiliares;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Permissao extends AppCompatActivity {
     static String[] permissoesNecessarias = new String[] {
             Manifest.permission.CAMERA,
             Manifest.permission.WRITE_EXTERNAL_STORAGE,
             Manifest.permission.ACCESS_COARSE_LOCATION,

    };
    private static final int REQUEST_PERMISSIONS_CODE = 128;
    Activity activity;

    public Permissao(Activity activity) {
        this.activity = activity;
    }

    public  boolean validaPermissoes(Activity activity) {

        if(Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();

            //Percorre as permissões passadas, verificando uma a uma se já tem a permissão liberada
            for (String permissao : permissoesNecessarias) {
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) listaPermissoes.add(permissao);
            }

            if(listaPermissoes.isEmpty())
                return true;
            else {

                ActivityCompat.requestPermissions(activity, listaPermissoes.toArray(new String[0]), REQUEST_PERMISSIONS_CODE);
                return false;
            }

        }

        return true;
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar este app, é necessário aceitar as permissões");

        builder.setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              validaPermissoes(activity);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        for(int resultado : grantResult) {
            if(resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }
}

