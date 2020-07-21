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

public class ProporcionalJulio extends AppCompatActivity {
    AdView mAdView;

    EditText et1, et2, et3;
    Button btnproporciona;
    TextView tvResultado, tvResultado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_proporcional_julio);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        btnproporciona =findViewById(R.id.btnpropocional);
        tvResultado = findViewById(R.id.textViewResultado);


        btnproporciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    double aux1 = Double.valueOf(et1.getText().toString());
                    double aux2 = Double.valueOf(et2.getText().toString());
                    Integer aux3 = Integer.valueOf(et3.getText().toString());
                    double resultado = ((aux1 + aux2) / 15) * 46;
                    double resultado2 = (resultado * aux3) / 360;
                    DecimalFormat formato = new DecimalFormat("$0,000.00");
                    tvResultado.setText("Su 2da quincena de Julio concepto 055 = " + formato.format(resultado2));
                }catch (NumberFormatException e){

                    Toast toast1 =  Toast.makeText(getApplicationContext(),
                            "Favor de Ingresar todos los conceptos correctamente", Toast.LENGTH_LONG);

                    toast1.show();
                    return;
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), SegundaDeJulio.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), SegundaDeJulio.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}