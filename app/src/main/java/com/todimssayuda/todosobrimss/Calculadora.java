package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class Calculadora extends AppCompatActivity {
    AdView mAdView;

    EditText et1, et2;
    Button btnSumar;
    TextView tvResultado, tvResultado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_calculadora);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        btnSumar =findViewById(R.id.btnSumar);
        tvResultado = findViewById(R.id.textViewResultado);
        tvResultado2 = findViewById(R.id.textViewResultado2);


        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double aux1 = Double.valueOf(et1.getText().toString());
                double aux2 = Double.valueOf(et2.getText().toString());
                double resultado = ((aux1 + aux2)/15)*46;
                double resultado2= (aux1 + aux2 )*6;
                DecimalFormat formato = new DecimalFormat("$00,000.00");
                tvResultado.setText("Su 2da quincena de Julio concepto 055 = "+ formato.format(resultado));
                tvResultado2.setText("Su Aguinaldo concepto 049 = "+ formato.format(resultado2));


            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), Calculadora.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menubono, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        Intent intentmenu = new Intent();
        switch (item.getItemId()) {
            case R.id.modificacionescontrato:
                Intent intent121 = new Intent(this, Bono.class);
                startActivity(intent121);
                finish();
                break;


        }



        return super.onOptionsItemSelected(item);



    }


}
