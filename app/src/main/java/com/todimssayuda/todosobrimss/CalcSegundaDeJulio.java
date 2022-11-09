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

public class CalcSegundaDeJulio extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;

    EditText et1, et2;
    Button btnSumar, btnproporcional;
    TextView tvResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_calc_segunda_de_julio);
        this.setTitle("Calc 2a de julio");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);

        btnSumar = findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(this);

        btnproporcional = findViewById(R.id.btnpropocional);
        btnproporcional.setOnClickListener(this);

        tvResultado = findViewById(R.id.textViewResultado);
        tvResultado.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSumar:
                try {


                    double aux1 = Double.valueOf(et1.getText().toString());
                    double aux2 = Double.valueOf(et2.getText().toString());
                    double resultado = ((aux1 + aux2) / 15) * 46;
                    DecimalFormat formato = new DecimalFormat("$00,000.00");
                    tvResultado.setText("Su 2da quincena de Julio concepto 055 = " + formato.format(resultado));
                    tvResultado.setVisibility(View.VISIBLE);
                } catch (NumberFormatException e) {

                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Favor de Ingresar todos los conceptos correctamente", Toast.LENGTH_LONG);

                    toast1.show();
                    return;
                }
                break;

            case R.id.btnpropocional:
                Intent intentdajaa = new Intent(this, CalcPropSegundaJulio.class);
                startActivity(intentdajaa);
                finish();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Incidencias");
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
        TextView titulo = vista.findViewById(R.id.titulo);
        titulo.setText("Las incidencias que afectan el fondo de ahorro:");
        TextView texto = vista.findViewById(R.id.texto);
        texto.setText(getResources().getString(R.string.instruccionessegundadejulio));
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogo.cancel();

            }
        });
        dialogo.show();

    }


}