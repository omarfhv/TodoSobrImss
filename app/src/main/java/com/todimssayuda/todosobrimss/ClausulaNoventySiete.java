package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class ClausulaNoventySiete extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;

    EditText et1, et2;
    Button btnSumar;
    TextView tvResultado, tvResultado2,tvResultado3;
    ColorDrawable dialogColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_clausula_noventy_siete);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);

        btnSumar =findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(this);



        tvResultado = findViewById(R.id.textViewResultado);
        tvResultado2 = findViewById(R.id.textViewResultado1);
        tvResultado3 = findViewById(R.id.textViewResultado2);




    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSumar:
                try {


                    double aux1 = Double.parseDouble(et1.getText().toString());
                    double aux2 = Double.parseDouble(et2.getText().toString());
                    double resultado2 = (aux1 + aux2)*2;
                    double resultado3 = (aux1 + aux2)*4;
                    double resultado4 = (aux1 + aux2)*6;

                    DecimalFormat formato = new DecimalFormat("$00,000.00");
                    tvResultado.setText("Su prestamo por 1 meses es = " + formato.format(resultado2));
                    tvResultado2.setText("Su prestamo por 2 meses es = " + formato.format(resultado3));
                    tvResultado3.setText("Su prestamo por 3 meses es = " + formato.format(resultado4));


                }catch (NumberFormatException e){

                    Toast toast1 =  Toast.makeText(getApplicationContext(),
                            "Favor de Ingresar todos los conceptos correctamente", Toast.LENGTH_LONG);

                    toast1.show();
                    return;
                }

                break;


        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clausula, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
            case R.id.infohipo:
                instruccionesdialogo(true);

                break;
            case R.id.documenclausu:
                documents(true);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void documents(boolean def) {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("instruccionesa", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("instruccionesa", false) || def) {
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.documentoclausula, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            dialogo.getWindow().setBackgroundDrawable(dialogColor);
            final Button botonok = vista.findViewById(R.id.botonok);
            //TextView texto = vista.findViewById(R.id.txt);
            // texto.setText(getString(R.string.mensajeinicio));

            botonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogo.cancel();

                }
            });
            dialogo.show();
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


    private void instruccionesdialogo(boolean def) {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("instruccionesa", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("instruccionesa", false) || def) {
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.infoclausulanoventaysiete, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            dialogo.getWindow().setBackgroundDrawable(dialogColor);
            final Button botonok = vista.findViewById(R.id.botonok);
            //TextView texto = vista.findViewById(R.id.txt);
            // texto.setText(getString(R.string.mensajeinicio));

            botonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogo.cancel();

                }
            });
            dialogo.show();
        }

    }


}