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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class EditOtros extends AppCompatActivity {
    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    private final String RUTA_IMAGEN = New.CARPETA_RAIZ + "OtrosIMSS";
    Button upd_elotros, del_btnotros, whats;
    EditText motivootros, documentosotros;
    TextView fechaotros;
    ImageView imagenotros;
    String idimagenotros, radiobtnotros, name, Fechaotros;
    long idotros;
    private AdView mAdView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_otros);
        whats = findViewById(R.id.botonwhats);
        upd_elotros = findViewById(R.id.upd_element);
        del_btnotros = findViewById(R.id.del_btn);
        fechaotros = findViewById(R.id.textfechaotros);
        documentosotros = findViewById(R.id.documentosotros);
        motivootros = findViewById(R.id.motivootros);
        imagenotros = findViewById(R.id.imagenIdotros);

        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adinter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();

            }

        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Intent i = getIntent();
        idotros = i.getLongExtra("otrosid", 0);
        Fechaotros = i.getStringExtra("otrosfecha");
        idimagenotros = i.getStringExtra("idimagen");
        //name.setText(Fecha);
        fechaotros.setText(Fechaotros.charAt(0) + "" + Fechaotros.charAt(1) + "/" + Fechaotros.charAt(2) + "" + Fechaotros.charAt(3) + "/" +
                Fechaotros.charAt(4) + "" + Fechaotros.charAt(5) + Fechaotros.charAt(6) + Fechaotros.charAt(7));


        documentosotros.setText(i.getStringExtra("otrosdocumentos"));
        motivootros.setText(i.getStringExtra("otrosmotivo"));


        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fechaotros + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagenotros.setImageBitmap(bitmap);
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditSusti.onClickWhatsApp(EditOtros.this, imagenotros, "Te comparto este documento " + documentosotros.getText().toString());

            }
        });
        upd_elotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ContactOtros c = new ContactOtros(getBaseContext());
                c.open();
                c.updateOtros(idotros, Fechaotros, documentosotros.getText().toString(), motivootros.getText().toString());
                documentosotros.setText("");
                Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                //se agrega metodo para pasar a nueva actividad
                Intent intentds = new Intent(EditOtros.this, Otros.class);
                startActivity(intentds);
                //se cierra actividdad actual
                finish();


            }
        });

        del_btnotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditOtros.this);

                builder.setTitle("  Confirmar  ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ContactOtros c = new ContactOtros(getBaseContext());
                        c.open();
                        c.deleteContactOtros(idotros);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();
                        Intent intentds = new Intent(EditOtros.this, Otros.class);
                        startActivity(intentds);
                        finish();

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
            startActivity(new Intent(getBaseContext(), Otros.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), Otros.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}



