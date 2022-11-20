package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SeguroFacul extends AppCompatActivity implements View.OnClickListener {


    LinearLayout botondirectprimera, botondirecrenovacio, botonpoliticos, botongraficadirecto, botongraficapoliticos;
    AlertDialog dialog;
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_seguro_facul);
        this.setTitle("Seguro facultativo");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        botondirectprimera = findViewById(R.id.botonfamidirectosprimera);
        botondirectprimera.setOnClickListener(this);

        botondirecrenovacio = findViewById(R.id.botonfamidirectosrenova);
        botondirecrenovacio.setOnClickListener(this);

        botonpoliticos = findViewById(R.id.botonfamipoliticos);
        botonpoliticos.setOnClickListener(this);

        botongraficadirecto = findViewById(R.id.botongraficadirectos);
        botongraficadirecto.setOnClickListener(this);

        botongraficapoliticos = findViewById(R.id.botongraficapoliticos);
        botongraficapoliticos.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.botonfamidirectosprimera:
                mostrardialogo(R.layout.directos_primera);
                break;

            case R.id.botonfamidirectosrenova:
                mostrardialogo(R.layout.directos_renovacion);
                break;

            case R.id.botonfamipoliticos:
                mostrardialogo(R.layout.politicos_renova);
                break;

            case R.id.botongraficadirectos:
                mostrardialogo(R.layout.grafica_directos);
                break;

            case R.id.botongraficapoliticos:
                mostrardialogo(R.layout.grafica_politicos);
                break;
        }
    }

    public void mostrardialogo(int recurso) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SeguroFacul.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(recurso, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(true);
        MainActivity.quitarbordesdialogo(dialog);
        Button botonok = view.findViewById(R.id.botoncont);
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}



