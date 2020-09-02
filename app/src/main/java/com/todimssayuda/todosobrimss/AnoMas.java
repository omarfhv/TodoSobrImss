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

public class AnoMas extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botonincluuno, botonincludos, botonanocuatro, botonanocinco, botonanomas;
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

        setContentView(R.layout.activity_ano_uno);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        botonincluuno = findViewById(R.id.botonincludos);
        botonincluuno.setOnClickListener(this);

        botonincludos = findViewById(R.id.botoninclucuatro);
        botonincludos.setOnClickListener(this);

        botonanocuatro = findViewById(R.id.botoninclunueve);
        botonanocuatro.setOnClickListener(this);

        botonanocinco = findViewById(R.id.botonincluuno);
        botonanocinco.setOnClickListener(this);

        botonanomas = findViewById(R.id.botoninclucero);
        botonanomas.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.botonincludos:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AnoMas.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.ano_seis_marcados, null);
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


            case R.id.botoninclucuatro:
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(AnoMas.this);
                final LayoutInflater inflater11 = getLayoutInflater();
                View vi1 = inflater11.inflate(R.layout.ano_seis_marcacuatro, null);
                builder1.setView(vi1);
                final AlertDialog dialog1 = builder1.create();
                dialog1.setCancelable(true);
                dialog1.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok1 = vi1.findViewById(R.id.botoncont);
                botonok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();

                    }
                });


                dialog1.show();


                break;
            case R.id.botoninclunueve:
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(AnoMas.this);
                final LayoutInflater inflater12 = getLayoutInflater();
                View vi2 = inflater12.inflate(R.layout.ano_seis_marcanueve, null);
                builder2.setView(vi2);
                final AlertDialog dialog2 = builder2.create();
                dialog2.setCancelable(true);
                dialog2.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok2 = vi2.findViewById(R.id.botoncont);
                botonok2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();

                    }
                });


                dialog2.show();


                break;
            case R.id.botonincluuno:
                final AlertDialog.Builder builder3 = new AlertDialog.Builder(AnoMas.this);
                final LayoutInflater inflater13 = getLayoutInflater();
                View vi3 = inflater13.inflate(R.layout.ano_seis_marcauno, null);
                builder3.setView(vi3);
                final AlertDialog dialog3 = builder3.create();
                dialog3.setCancelable(true);
                dialog3.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok3 = vi3.findViewById(R.id.botoncont);
                botonok3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog3.dismiss();

                    }
                });


                dialog3.show();


                break;
            case R.id.botoninclucero:
                final AlertDialog.Builder builder4 = new AlertDialog.Builder(AnoMas.this);
                final LayoutInflater inflater14 = getLayoutInflater();
                View vi4 = inflater14.inflate(R.layout.ano_seis_marcacero, null);
                builder4.setView(vi4);
                final AlertDialog dialog4 = builder4.create();
                dialog4.setCancelable(true);
                dialog4.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok4 = vi4.findViewById(R.id.botoncont);
                botonok4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog4.dismiss();

                    }
                });


                dialog4.show();


                break;




        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MarcasDeInclusion.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MarcasDeInclusion.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
