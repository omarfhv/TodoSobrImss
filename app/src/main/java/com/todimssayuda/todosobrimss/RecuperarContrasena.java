package com.todimssayuda.todosobrimss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.Manifest.permission.CALL_PHONE;

public class RecuperarContrasena extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botonrecujubilados, botonrecuactivos, botonsoportetecnico;
    Intent intent;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_recuperar_contrasena);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


// SI NOS CONCEDE EL PERMISO Y LANZA LA LLAMADA


        botonrecujubilados = findViewById(R.id.botonrecujubilados);
        botonrecujubilados.setOnClickListener(this);

        botonrecuactivos = findViewById(R.id.botonrecuactivos);
        botonrecuactivos.setOnClickListener(this);

        botonsoportetecnico = findViewById(R.id.botonsoporte);
        botonsoportetecnico.setOnClickListener(this);

        validaPermisos();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.botonrecujubilados:
                intent = new Intent(this, RecuJubilados.class);
                break;

            case R.id.botonrecuactivos:
                intent = new Intent(this, RecuActivos.class);
                break;

            case R.id.botonsoporte:
                intent = new Intent(this, RecuperaSoporte.class);
                break;
        }
        startActivity(intent);
        finish();
    }


    private void validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if ((checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        if ((shouldShowRequestPermissionRationale(CALL_PHONE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(RecuperarContrasena.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{CALL_PHONE}, 100);
                }
            });
            dialogo.show();
        } else {
            requestPermissions(new String[]{CALL_PHONE}, 100);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}