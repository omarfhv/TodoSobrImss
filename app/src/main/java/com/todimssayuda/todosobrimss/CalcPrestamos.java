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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;

public class CalcPrestamos extends AppCompatActivity {
    AdView mAdView;

    EditText et1, et2;
    Button btnSumar;
    TextView tvResultado1, tvResultado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_calc_prestamos);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        btnSumar = findViewById(R.id.btnSumar);
        tvResultado1 = findViewById(R.id.txvResultado1);
        tvResultado1.setVisibility(View.INVISIBLE);
        tvResultado2 = findViewById(R.id.txvResultado2);
        tvResultado2.setVisibility(View.INVISIBLE);


        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double aux1 = Double.parseDouble(et1.getText().toString());
                    double aux2 = Double.parseDouble(et2.getText().toString());
                    double resultado = ((aux1 + aux2) / 15) * 46;
                    double resultado2 = (aux1 + aux2) * 6;
                    DecimalFormat formato = new DecimalFormat("$00,000.00");
                    tvResultado1.setVisibility(View.VISIBLE);
                    tvResultado2.setVisibility(View.VISIBLE);
                    tvResultado1.setText("Su 2da quincena de Julio concepto 055 = " + formato.format(resultado));
                    tvResultado2.setText("Su Aguinaldo concepto 049 = " + formato.format(resultado2));

                } catch (Exception e) {

                    Toast.makeText(CalcPrestamos.this, "Ingresa todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), PrestamosPorCategoria.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Bono");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.che));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), PrestamosPorCategoria.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }

        switch (item.getItemId()) {
            case R.id.item2:
                Intent intent121 = new Intent(this, CalcBono.class);
                startActivity(intent121);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);

    }


}
