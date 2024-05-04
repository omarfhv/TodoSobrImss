package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class DeclaracionAnual extends AppCompatActivity implements View.OnClickListener {

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

        setContentView(R.layout.activity_declaracion_anual);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public static void quitarbordesdialogo(AlertDialog dialog) {
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
    }

    public void Descarga(View view) {
        //se obtiene el titulo directamente del cduadro de texto del layout
        textview = findViewById(R.id.cursos1);

        final AlertDialog.Builder builder = new AlertDialog.Builder(DeclaracionAnual.this);
        final LayoutInflater inflater1 = getLayoutInflater();
        View vi = inflater1.inflate(R.layout.dialog_declara, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        quitarbordesdialogo(dialog);
        Button botonok = vi.findViewById(R.id.botoncont);
        final RadioButton rbtna = vi.findViewById(R.id.rbtna);
        final RadioButton rbtnj = vi.findViewById(R.id.rbtnj);
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbtna.isChecked()) {
                    Intent intentae = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sat.gob.mx/declaracion/97720/consulta-el-visor-de-comprobantes-de-nomina-para-el-trabajador"));
                    startActivity(intentae);
                }
                if (rbtnj.isChecked()) {

                    Intent intentae = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rh.imss.gob.mx/Personal/IngresoAnual/#/login"));
                    startActivity(intentae);

                }


            }
        });

        dialog.show();

    }


    public void Tutorial(View view) {
        textview = findViewById(R.id.cursos3);
        cambioActivitypdf("declara", "declara", (String) textview.getText());
    }

    public void Declaran(View view) {
        Intent intentae = new Intent(Intent.ACTION_VIEW, Uri.parse("https://declaranet.gob.mx/"));
        startActivity(intentae);
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
