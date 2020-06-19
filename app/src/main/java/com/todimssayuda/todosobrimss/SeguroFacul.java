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
    ColorDrawable dialogColor;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_seguro_facul);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(SeguroFacul.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.directos_primera, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok = vi.findViewById(R.id.botoncont);
                botonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


                dialog.show();


                break;


            case R.id.botonfamidirectosrenova:

                final AlertDialog.Builder builders = new AlertDialog.Builder(SeguroFacul.this);
                final LayoutInflater inflater = getLayoutInflater();
                View vis = inflater.inflate(R.layout.directos_renovacion, null);
                builders.setView(vis);
                final AlertDialog dialogo = builders.create();
                dialogo.setCancelable(true);
                dialogo.getWindow().setBackgroundDrawable(dialogColor);
                Button botonoko = vis.findViewById(R.id.botoncont);
                botonoko.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogo.dismiss();

                    }
                });


                dialogo.show();


                break;


            case R.id.botonfamipoliticos:

                final AlertDialog.Builder builderss = new AlertDialog.Builder(SeguroFacul.this);
                final LayoutInflater inflaters = getLayoutInflater();
                View viss = inflaters.inflate(R.layout.politicos_renova, null);
                builderss.setView(viss);
                final AlertDialog dialogos = builderss.create();
                dialogos.setCancelable(true);
                dialogos.getWindow().setBackgroundDrawable(dialogColor);
                Button botonokos = viss.findViewById(R.id.botoncont);
                botonokos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogos.dismiss();

                    }
                });


                dialogos.show();


                break;

            case R.id.botongraficadirectos:

                final AlertDialog.Builder builderss1 = new AlertDialog.Builder(SeguroFacul.this);
                final LayoutInflater inflaters1 = getLayoutInflater();
                View viss1 = inflaters1.inflate(R.layout.grafica_directos, null);
                builderss1.setView(viss1);
                final AlertDialog dialogos1 = builderss1.create();
                dialogos1.setCancelable(true);
                dialogos1.getWindow().setBackgroundDrawable(dialogColor);
                Button botonokos1 = viss1.findViewById(R.id.botoncont);
                botonokos1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogos1.dismiss();

                    }
                });


                dialogos1.show();


                break;


            case R.id.botongraficapoliticos:

                final AlertDialog.Builder builderss12 = new AlertDialog.Builder(SeguroFacul.this);
                final LayoutInflater inflaters12 = getLayoutInflater();
                View viss12 = inflaters12.inflate(R.layout.grafica_politicos, null);
                builderss12.setView(viss12);
                final AlertDialog dialogos12 = builderss12.create();
                dialogos12.setCancelable(true);
                dialogos12.getWindow().setBackgroundDrawable(dialogColor);
                Button botonokos12 = viss12.findViewById(R.id.botoncont);
                botonokos12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogos12.dismiss();

                    }
                });


                dialogos12.show();


                break;



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



