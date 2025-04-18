package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class CalcPrestamoCarro extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;
    EditText et1, et2;
    Button btnSumar;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.setTitle("Prestamo carro");
        setContentView(R.layout.activity_calc_prestamo_carro);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);

        btnSumar = findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(this);

        tvResultado = findViewById(R.id.textViewResultado);
        tvResultado.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSumar:
                try {
                    double aux1 = Double.parseDouble(et1.getText().toString());
                    double aux2 = Double.parseDouble(et2.getText().toString());
                    double resultado2 = (aux1 + aux2) * .2;
                    double resultado = (aux1 + aux2 + resultado2);
                    double resultado3 = (resultado) * 48;
                    DecimalFormat formato = new DecimalFormat("$00,000.00");
                    tvResultado.setText("SU CREDITO DE CARRO ES = " + formato.format(resultado3));
                    tvResultado.setVisibility(View.VISIBLE);
                } catch (NumberFormatException e) {

                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Favor de Ingresar todos los conceptos correctamente", Toast.LENGTH_LONG);

                    toast1.show();
                    return;
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Requisitos");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.info));
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
            case R.id.item2:
                instruccionesdialogo();
                break;
        }

        return super.onOptionsItemSelected(item);
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


    private void instruccionesdialogo() {
        final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        View vista = getLayoutInflater().inflate(R.layout.dialogo_requisitos, null);
        constructor.setView(vista);
        final AlertDialog dialogo = constructor.create();
        MainActivity.quitarbordesdialogo(dialogo);
        final Button botonok = vista.findViewById(R.id.botonok);
        final TextView txv = vista.findViewById(R.id.texto);
        txv.setText(getResources().getString(R.string.instruccionescarro));
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.cancel();
            }
        });
        dialogo.show();

    }

}