package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MarcasDeInclusion extends AppCompatActivity implements View.OnClickListener{

    LinearLayout botonanouno,botonanodos,botonanotres, botonanocuatro, botonanocinco,
            botonanomascinco, botoncalcuvacas;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setContentView(R.layout.activity_marcas_de_inclusion);

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        botonanouno = findViewById(R.id.botonanouno);
        botonanouno.setOnClickListener(this);


        botonanodos = findViewById(R.id.botonanodos);
        botonanodos.setOnClickListener(this);


        botonanotres = findViewById(R.id.botonanotres);
        botonanotres.setOnClickListener(this);


        botonanocuatro = findViewById(R.id.botonanocuatro);
        botonanocuatro.setOnClickListener(this);


        botonanocinco = findViewById(R.id.botonanocinco);
        botonanocinco.setOnClickListener(this);


        botonanomascinco = findViewById(R.id.botonanomas);
        botonanomascinco.setOnClickListener(this);

        botoncalcuvacas = findViewById(R.id.botoncalcuva);
        botoncalcuvacas.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.botonanouno:

                Intent intent11 = new Intent(this, AnoUno.class);
                startActivity(intent11);
                finish();
                break;

            case R.id.botonanodos:

                Intent intent111 = new Intent(this, AnoDos.class);
                startActivity(intent111);
                finish();
                break;

            case R.id.botonanotres:

                Intent intent112 = new Intent(this, AnoTres.class);
                startActivity(intent112);
                finish();
                break;

            case R.id.botonanocuatro:

                Intent intent113 = new Intent(this, AnoCuatro.class);
                startActivity(intent113);
                finish();
                break;

            case R.id.botonanocinco:

                Intent intent115 = new Intent(this, AnoCinco.class);
                startActivity(intent115);
                finish();
                break;

            case R.id.botonanomas:

                Intent intent116 = new Intent(this, AnoMas.class);
                startActivity(intent116);
                finish();
                break;

            case R.id.botoncalcuva:

                Intent intent1163 = new Intent(this, CalcuVacas.class);
                startActivity(intent1163);
                finish();
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



}
