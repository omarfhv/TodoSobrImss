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

public class PliegoTesta extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botontramite, botonrequisitos, botona, botonb, botonc;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_pliego_testa);
        this.setTitle("Pliego testamentario");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        botonrequisitos = findViewById(R.id.botonrequi);
        botonrequisitos.setOnClickListener(this);

        botontramite = findViewById(R.id.botontramite);
        botontramite.setOnClickListener(this);

        botona = findViewById(R.id.botona);
        botona.setOnClickListener(this);

        botonb = findViewById(R.id.botonb);
        botonb.setOnClickListener(this);

        botonc = findViewById(R.id.botonc);
        botonc.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.botona:
                mostrardialogo(R.layout.dialogo_testamento);
                break;
            case R.id.botonb:
                mostrardialogo(R.layout.pliego_b);
                break;
            case R.id.botonc:
                mostrardialogo(R.layout.pliegob);
                break;
            case R.id.botonrequi:
                mostrardialogo(R.layout.requisitos);
                break;
            case R.id.botontramite:
                mostrardialogo(R.layout.tramite);
                break;


        }
    }


    public void mostrardialogo(int recurso) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PliegoTesta.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(recurso, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        MainActivity.quitarbordesdialogo(dialog);
        Button botonokos12 = view.findViewById(R.id.botoncont);
        botonokos12.setOnClickListener(new View.OnClickListener() {
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
