package com.todimssayuda.todosobrimss;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MenuPrincipalAgenda extends AppCompatActivity implements View.OnClickListener {

    Button pases, sustis, licencia, otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_agenda);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        pases = findViewById(R.id.pases);
        pases.setOnClickListener(this);
        sustis = findViewById(R.id.sustis);
        sustis.setOnClickListener(this);
        licencia = findViewById(R.id.licencia);
        licencia.setOnClickListener(this);
        otros = findViewById(R.id.otros);
        otros.setOnClickListener(this);
        //INSTANCIA de los id's de los buttons


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("inicio", false)) {
            final androidx.appcompat.app.AlertDialog.Builder constructor = new androidx.appcompat.app.AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.alert_dialog_agenda, null);
            constructor.setView(vista);
            final androidx.appcompat.app.AlertDialog dialogo = constructor.create();
            Button botonok = vista.findViewById(R.id.botonok);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            TextView texto = vista.findViewById(R.id.txt);
            texto.setText("Recuerda que los datos que ingreses y los docuementos archivados solo son organizados en las carpetas" +
                    " del dispositivo, no almacenamos datos en servidores externos, ante todo nos preocupa la seguridad de tu informacion");
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               SharedPreferences sharedPref;
                                               sharedPref = getSharedPreferences(
                                                       "inicio", Context.MODE_PRIVATE);
                                               SharedPreferences.Editor editor = sharedPref.edit();
                                               editor.putBoolean("inicio", chbx.isChecked());
                                               editor.commit();
                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
        }
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
    public void onClick(View v) {
        switch (v.getId()) { //Obtenemos el ID del button que ha sido clickeado
            case R.id.pases:
                Intent intent1 = new Intent(MenuPrincipalAgenda.this, PasesAgenda.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.sustis:
                Intent intentae = new Intent(MenuPrincipalAgenda.this, SustisAgenda.class);
                startActivity(intentae);
                break;
            case R.id.licencia:
                Intent intentds = new Intent(MenuPrincipalAgenda.this, Licencia.class);
                startActivity(intentds);
                break;

            case R.id.otros:
                Intent intent11s = new Intent(MenuPrincipalAgenda.this, Otros.class);
                startActivity(intent11s);
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
