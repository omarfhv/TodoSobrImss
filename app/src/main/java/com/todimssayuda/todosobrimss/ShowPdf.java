package com.todimssayuda.todosobrimss;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ShowPdf extends AppCompatActivity {


    PDFView pdfView;
    AdView mAdView;
    Bundle parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //ligar archivo xml
        setContentView(R.layout.activity_showpdf);

        //iniciar y cargar anuncios
        mAdView = findViewById(R.id.bannerviewer);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //buscar pdf en layout
        pdfView = findViewById(R.id.pdfviewer);
        // obtener nombre de pdf de la actividad anterior
        parametros = this.getIntent().getExtras();
        pdfView.fromAsset(parametros.getString("pdf") + ".pdf").load();
        this.setTitle(parametros.getString("titulo"));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            regresar();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            regresar();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void regresar() {
        Intent intent;
        parametros = this.getIntent().getExtras();
        switch (parametros.getString("clase")) {
            case "cursos":
                intent = new Intent(this, CursosIm.class);
                break;
            case "calendario":
                intent = new Intent(this, Calendario.class);
                break;

            case "modificacioncct":
                intent = new Intent(this, ContratoColectivoTrabajadores.class);
                break;
            case "incapacidades":
                intent = new Intent(this, Incapacidades.class);
                break;

            case "declara":
                intent = new Intent(this, DeclaracionAnual.class);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
