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

public class CalcuVacas extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;

    EditText et1, et2, et3;
    Button btnSumar;
    TextView tvResultado, tvprima;
    ColorDrawable dialogColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_calcu_vacas);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);


        btnSumar =findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(this);



        tvResultado = findViewById(R.id.textViewResultado);
        tvprima = findViewById(R.id.textViewResultado2);


    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSumar:
                try {


                    double aux1 = Double.valueOf(et1.getText().toString());
                    double aux2 = Double.valueOf(et2.getText().toString());
                    double aux3 = Double.valueOf(et3.getText().toString());

                    double resultado = ((aux1 / 30) *aux2);
                    double resultado2 = (((aux1 / 30) *aux3)*.25);
                    DecimalFormat formato = new DecimalFormat("$0,000.00");
                    tvResultado.setText("048 Su pago de vacaciones fraccionadas o vendidas es = " + formato.format(resultado));
                    tvprima.setText("029 Su Pago de Prima Vacacional es = "+ formato.format(resultado2));

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
        getMenuInflater().inflate(R.menu.segunda, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), MarcasDeInclusion.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
            case R.id.modificacionescontrato:
                instruccionesdialogo(true);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MarcasDeInclusion.class)
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
            View vista = getLayoutInflater().inflate(R.layout.instruccionessegunda, null);
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