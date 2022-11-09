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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AnoUno extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botonincluuno, botonincludos, botonanocuatro, botonanocinco, botonanomas;
    int[][] imagenes = new int[][]
            {{R.drawable.ano111, R.drawable.ano112, R.drawable.ano113, R.drawable.ano114, R.drawable.ano115},
                    {R.drawable.ano221, R.drawable.ano222, R.drawable.ano223, R.drawable.ano224, R.drawable.ano225},
                    {R.drawable.ano331, R.drawable.ano332, R.drawable.ano333, R.drawable.ano334, R.drawable.ano335},
                    {R.drawable.ano441, R.drawable.ano442, R.drawable.ano443, R.drawable.ano444, R.drawable.ano445},
                    {R.drawable.ano551, R.drawable.ano552, R.drawable.ano553, R.drawable.ano554, R.drawable.ano555},
                    {R.drawable.ano661, R.drawable.ano662, R.drawable.ano663, R.drawable.ano664, R.drawable.ano665}};


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

        setContentView(R.layout.activity_ano_uno);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        parametros = new Bundle();
        parametros = this.getIntent().getExtras();
        this.setTitle(parametros.getString("titulo"));

        botonincluuno = findViewById(R.id.botonincludos);
        botonincluuno.setOnClickListener(this);

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
                mostrarMensaje(imagenes[parametros.getInt("id")][0]);
                break;
            case R.id.botoninclucuatro:
                mostrarMensaje(imagenes[parametros.getInt("id")][1]);
                break;
            case R.id.botoninclunueve:
                mostrarMensaje(imagenes[parametros.getInt("id")][2]);
                break;
            case R.id.botonincluuno:
                mostrarMensaje(imagenes[parametros.getInt("id")][3]);
                break;
            case R.id.botoninclucero:
                mostrarMensaje(imagenes[parametros.getInt("id")][4]);
                break;


        }
    }

    public void mostrarMensaje(int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AnoUno.this);
        final LayoutInflater inflater1 = getLayoutInflater();
        View vi = inflater1.inflate(R.layout.mensajeanovac, null);
        ImageView imageviewvac = vi.findViewById(R.id.imageViewvac);
        imageviewvac.setBackground(getResources().getDrawable(id));
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        MainActivity.quitarbordesdialogo(dialog);
        Button botonok = vi.findViewById(R.id.botoncont);
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
            startActivity(new Intent(getBaseContext(), MenuCalcVacaciones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MenuCalcVacaciones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}