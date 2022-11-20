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

public class InfoPases extends AppCompatActivity implements View.OnClickListener {

    LinearLayout boton29, boton30, boton30bis;
    ColorDrawable dialogColor;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setContentView(R.layout.activity_info_pases);
        this.setTitle("Pases");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        boton29 = findViewById(R.id.boton29);
        boton29.setOnClickListener(this);

        boton30 = findViewById(R.id.boton30);
        boton30.setOnClickListener(this);

        boton30bis = findViewById(R.id.boton30bis);
        boton30bis.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.boton29:
                final AlertDialog.Builder builder = new AlertDialog.Builder(InfoPases.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.arti29, null);
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

            case R.id.boton30:

                final AlertDialog.Builder builders = new AlertDialog.Builder(InfoPases.this);
                final LayoutInflater inflater = getLayoutInflater();
                View vis = inflater.inflate(R.layout.arti30, null);
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


            case R.id.boton30bis:

                final AlertDialog.Builder builderss = new AlertDialog.Builder(InfoPases.this);
                final LayoutInflater inflaters = getLayoutInflater();
                View viss = inflaters.inflate(R.layout.arti30bis, null);
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


        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), Pases.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), Pases.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
