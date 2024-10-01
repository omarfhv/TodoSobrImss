package com.todimssayuda.todosobrimss;

import static com.todimssayuda.todosobrimss.MainActivity.quitarbordesdialogo;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;



public class ConceptosTarjeton extends AppCompatActivity {

    String[] titulos, conceptos;
    LinearLayout contenedor;
    TextView[] textViewArray;  // Almacenamos los TextViews creados para poder filtrarlos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_conceptos_tarjeton);
        this.setTitle("Conceptos del tarjeton");

        contenedor = findViewById(R.id.layoutprincipal);
        SearchView searchView = findViewById(R.id.searchView);
        titulos = getResources().getStringArray(R.array.titulosconceptos);
        conceptos = getResources().getStringArray(R.array.conceptos);
        textViewArray = new TextView[titulos.length];

        // Crear los TextViews dinámicamente
        for (int i = 0; i < titulos.length; i++) {
            final int index = i;
            TextView textView = new TextView(this);
            textView.setText(titulos[i]);
            textView.setTextSize(15);
            textView.setBackground(getResources().getDrawable(R.drawable.boton_principal));
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 20, 20, 20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(20, 10, 100, 10);

            // Aplicar los parámetros al TextView
            textView.setLayoutParams(layoutParams);
            textViewArray[i] = textView;

            // Agregar el TextView al LinearLayout
            contenedor.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarDialogoConcepto(index);
                }
            });
        }

        // Implementar el filtro del SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;  // No necesitamos manejar el evento submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarConceptos(newText);
                return true;
            }
        });
    }

    // Método para mostrar el diálogo con el concepto correspondiente
    private void mostrarDialogoConcepto(int index) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ConceptosTarjeton.this);
        final LayoutInflater inflater1 = getLayoutInflater();
        View vi = inflater1.inflate(R.layout.dialog_conceptos, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        quitarbordesdialogo(dialog);
        TextView titulo = vi.findViewById(R.id.titulo);
        TextView descripcion = vi.findViewById(R.id.descripcion);
        titulo.setText(titulos[index]);
        descripcion.setText(conceptos[index]);
        Button botonok = vi.findViewById(R.id.botonok);
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    // Método para filtrar los TextViews según el texto del SearchView
    private void filtrarConceptos(String query) {
        query = query.toLowerCase();
        for (int i = 0; i < titulos.length; i++) {
            String titulo = titulos[i].toLowerCase();
            if (titulo.contains(query)) {
                textViewArray[i].setVisibility(View.VISIBLE);
            } else {
                textViewArray[i].setVisibility(View.GONE);
            }
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