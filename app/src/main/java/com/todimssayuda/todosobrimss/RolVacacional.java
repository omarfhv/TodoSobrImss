package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class RolVacacional extends AppCompatActivity implements View.OnClickListener {

    PDFView pdfView;
    private AdView mAdView;

    LinearLayout botonroles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_rol_vacacional);
        this.setTitle("Rol vacacional 2022");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        botonroles = findViewById(R.id.botonroles);
        botonroles.setOnClickListener(this);


        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("rol22.pdf").load();

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


    public void onClick(View v) {

        Intent intent = new Intent(this, ShowPdf.class);
        intent.putExtra("pdf", "rol23");
        intent.putExtra("clase", "rol");
        intent.putExtra("titulo", "Rol vacacional 2023");
        startActivity(intent);
        finish();

    }

}
