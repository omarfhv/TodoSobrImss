package com.todimssayuda.todosobrimss;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

public class EditLicencia extends AppCompatActivity implements View.OnClickListener {

    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    private final String RUTA_IMAGEN = New.CARPETA_RAIZ + "LicenciaIMSS";
    Button upd_ellicencia, del_btnlicencia, btnfechafinal, whats;
    EditText motivolicencia;
    TextView fechainicio, txtfechafinal;
    ImageView imagenlicencia;
    String idimagenlicencia, radiobtnlicencia, Fecha, diafinal, mesfinal, fechafinal;
    long idlicencia;
    CheckBox sueldo;
    private AdView mAdView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_licencia);

        upd_ellicencia = findViewById(R.id.upd_element);
        upd_ellicencia.setOnClickListener(this);
        del_btnlicencia = findViewById(R.id.del_btn);
        del_btnlicencia.setOnClickListener(this);
        fechainicio = findViewById(R.id.textfechalicencia);
        txtfechafinal = findViewById(R.id.textfechafinal);
        btnfechafinal = findViewById(R.id.fechabtnfinal);
        btnfechafinal.setOnClickListener(this);
        motivolicencia = findViewById(R.id.motivolicencia);
        imagenlicencia = findViewById(R.id.imagenIdlicencia);
        sueldo = findViewById(R.id.chbxsueldo);
        whats = findViewById(R.id.botonwhats);
        whats.setOnClickListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        Intent i = getIntent();
        idlicencia = i.getLongExtra("licenciaid", 0);
        Fecha = i.getStringExtra("licenciafecha");
        String Fechafinal = i.getStringExtra("licenciafechafinal");
        idimagenlicencia = i.getStringExtra("idimagen");
        //name.setText(Fecha);
        fechainicio.setText(Fecha.charAt(0) + "" + Fecha.charAt(1) + "/" + Fecha.charAt(2) + "" + Fecha.charAt(3) + "/" +
                Fecha.charAt(4) + "" + Fecha.charAt(5) + Fecha.charAt(6) + Fecha.charAt(7));

        txtfechafinal.setText(Fechafinal.charAt(0) + "" + Fechafinal.charAt(1) + "/" + Fechafinal.charAt(2) + "" + Fechafinal.charAt(3) + "/" +
                Fechafinal.charAt(4) + "" + Fechafinal.charAt(5) + Fechafinal.charAt(6) + Fechafinal.charAt(7));

        switch (i.getStringExtra("licenciarbtn")) {
            case "CON SUELDO":
                sueldo.setChecked(true);
                break;

        }
        motivolicencia.setText(i.getStringExtra("licenciamotivo"));
        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fecha + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagenlicencia.setImageBitmap(bitmap);


        //recuso boton a atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), Licencia.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), Licencia.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonwhats:
                EditSusti.onClickWhatsApp( this, imagenlicencia, "Te comparto esta licencia de " + fechainicio.getText().toString() + " al " + fechafinal);
                break;
            case R.id.upd_element:
                if (txtfechafinal.getText().toString().length() > 0) {

                    if (sueldo.isChecked()) {
                        radiobtnlicencia = "CON SUELDO";
                    } else
                        radiobtnlicencia = "SIN SUELDO";


                    ContactLicencia c = new ContactLicencia(getBaseContext());
                    c.open();
                    c.updateLicencia(idlicencia, Fecha, fechafinal, radiobtnlicencia, motivolicencia.getText().toString());
                    motivolicencia.setText("");
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                    //se agrega metodo para pasar a nueva actividad
                    Intent intentds = new Intent(EditLicencia.this, Licencia.class);
                    startActivity(intentds);
                    //se cierra actividdad actual
                    finish();

                }

                break;

            case R.id.del_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditLicencia.this);

                builder.setTitle("  Confirmar  ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ContactLicencia c = new ContactLicencia(getBaseContext());
                        c.open();
                        c.deleteContactLicencia(idlicencia);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();
                        Intent intentds = new Intent(EditLicencia.this, Licencia.class);
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
                break;
            case R.id.fechabtnfinal:
                final String anoinicio = ("" + Fecha.charAt(4) + Fecha.charAt(5) + Fecha.charAt(6) + Fecha.charAt(7));
                final String mesinicio = ("" + Fecha.charAt(2) + Fecha.charAt(3));
                final String diainicio = ("" + Fecha.charAt(0) + Fecha.charAt(1));
                // Toast.makeText(EditLicencia.this, " " + anoinicio + " " + mesinicio, Toast.LENGTH_LONG).show();
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Toast.makeText(NewLicencia.this, "" + anoinicio + "" + year, Toast.LENGTH_SHORT).show();

                        if (Integer.parseInt(anoinicio) <= year) {
                                if (Integer.parseInt(mesinicio) <= month + 1) {
                                if (Integer.parseInt(diainicio) <= dayOfMonth || Integer.parseInt(mesinicio) == month) {

                                    // Toast.makeText(EditLicencia.this, " " + anoinicio + " se puede " + mesinicio, Toast.LENGTH_LONG).show();
                                    mesfinal = "" + (month + 1);
                                    //Toast.makeText(New.this, "" + mes.length(), Toast.LENGTH_LONG).show();
                                    if (mesfinal.length() == 1) {
                                        mesfinal = 0 + mesfinal;
                                    }
                                    diafinal = "" + dayOfMonth;
                                    if (diafinal.length() == 1)
                                        diafinal = 0 + diafinal;

                                    fechafinal = diafinal + mesfinal + year;

                                    txtfechafinal.setText(diafinal + "/" + mesfinal + "/" + year);

                                } else
                                    Toast.makeText(EditLicencia.this, "la fecha final no puede ser antes que la inicial (dia)", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(EditLicencia.this, "la fecha final no puede ser antes que la inicial (mes)", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(EditLicencia.this, "la fecha final no puede ser antes que la inicial (año)", Toast.LENGTH_LONG).show();
                    }
                }, Integer.parseInt(anoinicio), Integer.parseInt(mesinicio) - 1, Integer.parseInt(diainicio));
                datePickerDialog1.show();
                break;
        }
    }
}



