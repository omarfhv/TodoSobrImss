package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CursosIm extends AppCompatActivity implements View.OnClickListener {

    AdView mAdView;
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

        setContentView(R.layout.activity_menu_cursos_im);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void Tecnico(View view) {
        //se obtiene el titulo directamente del cduadro de texto del layout
        textview = findViewById(R.id.cursos1);
        cambioActivitypdf("becas", "cursos", (String) textview.getText());
    }


    public void Medicos(View view) {
        textview = findViewById(R.id.cursos3);
        cambioActivitypdf("mastecnicos", "cursos", (String) textview.getText());
    }

    public void Biblios(View view) {
        textview = findViewById(R.id.cursos4);
        cambioActivitypdf("nutris", "cursos", (String) textview.getText());
    }

    public void Tecnicos(View view) {
        textview = findViewById(R.id.cursos5);
        cambioActivitypdf("tecnic", "cursos", (String) textview.getText());

    }

    public void Centro(View view) {
        textview = findViewById(R.id.cursos6);
        cambioActivitypdf("cursoscdmxcentromedico", "cursos", (String) textview.getText());

    }

    public void cambioActivitypdf(String pdf, String clase, String titulo) {
        intent = new Intent(this, ShowPdf.class);
        intent.putExtra("pdf", pdf);
        intent.putExtra("clase", clase);
        intent.putExtra("titulo", titulo);
        startActivity(intent);
        finish();
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

    @Override
    public void onClick(View view) {

    }
}
