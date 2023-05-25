package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ContratoColectivoTrabajadores extends AppCompatActivity implements View.OnClickListener {

    AdView mAdView;
    LinearLayout btncompleto, btncct, btntabu, btnprofesio, btncata, btnreglame, btnconve, btnindice;

    Intent intent;
    TextView textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.contrato_dividido);

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btncompleto = findViewById(R.id.btncomple);
        btncompleto.setOnClickListener(this);

        btncct = findViewById(R.id.btncontr);
        btncct.setOnClickListener(this);

        btntabu = findViewById(R.id.btntabu);
        btntabu.setOnClickListener(this);

        btnprofesio = findViewById(R.id.btnprofesio);
        btnprofesio.setOnClickListener(this);

        btncata = findViewById(R.id.btncatalogos);
        btncata.setOnClickListener(this);

        btnreglame = findViewById(R.id.btnreglamen);
        btnreglame.setOnClickListener(this);

        btnconve = findViewById(R.id.btnconveni);
        btnconve.setOnClickListener(this);

        btnindice = findViewById(R.id.btnindice);
        btnindice.setOnClickListener(this);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Modificaciones");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.modifica));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        if (item.getItemId() == R.id.item2) {

            Intent intent = new Intent(this, ShowPdf.class);
            intent.putExtra("pdf", "modificacion");
            intent.putExtra("clase", "modificacioncct");
            intent.putExtra("titulo", "Modificaciones CCT");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btncomple:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato", "modificacioncct", "CCT 2021-2023");
                break;

            case R.id.btncontr:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato1", "modificacioncct", "CCT 2021-2023");
                break;

            case R.id.btntabu:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato2", "modificacioncct", "Tabulador de sueldos");
                break;

            case R.id.btnprofesio:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato3", "modificacioncct", "Profesiogramas");
                break;

            case R.id.btncatalogos:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato4", "modificacioncct", "Catálogos");
                break;

            case R.id.btnreglamen:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato5", "modificacioncct", "Reglamentos");
                break;

            case R.id.btnconveni:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato6", "modificacioncct", "Convenio adicional para las jubilaciones");
                break;

            case R.id.btnindice:
                //se obtiene el titulo directamente del cduadro de texto del layout
                cambioActivitypdf("contrato7", "modificacioncct", "Índice");
                break;

        }
    }

    private void cambioActivitypdf(String pdf, String clase, String titulo) {
        intent = new Intent(this, ShowPdf.class);
        intent.putExtra("pdf", pdf);
        intent.putExtra("clase", clase);
        intent.putExtra("titulo", titulo);
        startActivity(intent);
        finish();


    }
}
