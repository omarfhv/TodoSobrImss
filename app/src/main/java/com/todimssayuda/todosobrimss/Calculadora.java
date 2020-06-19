package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Calculadora extends AppCompatActivity {

    EditText et1, et2;
    Button btnSumar;
    TextView tvResultado, tvResultado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);


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
}