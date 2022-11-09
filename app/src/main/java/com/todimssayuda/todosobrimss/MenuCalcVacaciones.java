package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MenuCalcVacaciones extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botonanouno, botonanodos, botonanotres, botonanocuatro, botonanocinco,
            botonanomascinco, botoncalcuvacas;

    AdView mAdView;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setContentView(R.layout.activity_menu_marcas_de_inclusion);
        this.setTitle("Vacaciones");

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
        switch (view.getId()) {

            case R.id.botonanouno:
                cambioact(0, "1 año");
                break;
            case R.id.botonanodos:
                cambioact(1, "2 años");
                break;
            case R.id.botonanotres:
                cambioact(2, "3 años");
                break;
            case R.id.botonanocuatro:
                cambioact(3, "4 años");
                break;
            case R.id.botonanocinco:
                cambioact(4, "5 años");
                break;
            case R.id.botonanomas:
                cambioact(5, "Mas de 5 años");
                break;
            case R.id.botoncalcuva:
                intent = new Intent(this, CalcVac.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    public void cambioact(int i, String titulo) {
        intent = new Intent(this, AnoUno.class);
        intent.putExtra("id", i);
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


}
