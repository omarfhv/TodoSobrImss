package com.todimssayuda.todosobrimss;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class Edit extends AppCompatActivity {
    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    private final String RUTA_IMAGEN = New.CARPETA_RAIZ + "PasesIMSS";
    Button upd_el, del_btn, whats;
    EditText horas, motivo;
    RadioButton entrada, salida, intermedio;
    TextView fecha;
    ImageView imagen;
    String idimagen, radiobtn, name, Fecha;
    long id;
    private AdView mAdView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        whats = findViewById(R.id.botonwhats);
        upd_el = findViewById(R.id.upd_element);
        del_btn = findViewById(R.id.del_btn);
        fecha = findViewById(R.id.txvEfecha);
        horas = findViewById(R.id.address);
        motivo = findViewById(R.id.motivo);
        imagen = findViewById(R.id.imagenId);
        entrada = findViewById(R.id.entrada);
        salida = findViewById(R.id.salida);
        intermedio = findViewById(R.id.intermedio);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adinter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }
            @Override
            public void onAdClosed() {
                Intent intentds = new Intent(Edit.this, PasesAgenda.class);
                startActivity(intentds);
                finish();
                // Code to be executed when the interstitial ad is closed.
            }

        });
        Intent i = getIntent();
        id = i.getLongExtra("paseid", 0);
        Fecha = i.getStringExtra("pasefecha");
        idimagen = i.getStringExtra("idimagen");
        //name.setText(Fecha);
        fecha.setText(Fecha.charAt(0) + "" + Fecha.charAt(1) + "/" + Fecha.charAt(2) + "" + Fecha.charAt(3) + "/" +
                Fecha.charAt(4) + "" + Fecha.charAt(5) + Fecha.charAt(6) + Fecha.charAt(7));

        switch (i.getStringExtra("paserbtn")) {
            case "ENTRADA":
                entrada.setChecked(true);
                break;
            case "SALIDA":
                salida.setChecked(true);
                break;
            case "INTERMEDIO":
                intermedio.setChecked(true);
                break;
        }
        horas.setText(i.getStringExtra("pasehora"));
        motivo.setText(i.getStringExtra("pasemotivo"));


        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fecha + idimagen + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagen.setImageBitmap(bitmap);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditSusti.onClickWhatsApp(Edit.this, imagen, "Te comparto este pase del día  " + fecha.getText().toString());

            }
        });
        upd_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fecha.getText().toString().length() > 0) {

                    if (entrada.isChecked()) {
                        radiobtn = "ENTRADA";
                    }
                    if (salida.isChecked()) {
                        radiobtn = "SALIDA";
                    }
                    if (intermedio.isChecked()) {
                        radiobtn = "INTERMEDIO";
                    }
                    Contact c = new Contact(getBaseContext());
                    c.open();
                    c.updatePase(id, Fecha, radiobtn, horas.getText().toString(), motivo.getText().toString(), idimagen);
                    horas.setText("");
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                    //se agrega metodo para pasar a nueva actividad
                    Intent intentds = new Intent(Edit.this, PasesAgenda.class);
                    startActivity(intentds);
                    //se cierra actividdad actual
                    finish();


                } else {
                    Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();
                }

            }
        });


        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);

                builder.setTitle("  Confirmar  ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Contact c = new Contact(getBaseContext());
                        c.open();
                        c.deleteContact(id);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }else {
                            Intent intentds = new Intent(Edit.this, PasesAgenda.class);
                            startActivity(intentds);
                            finish();
                        }
                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //recuso boton a atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), PasesAgenda.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), PasesAgenda.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}



