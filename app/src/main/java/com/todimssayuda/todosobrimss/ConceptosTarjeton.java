package com.todimssayuda.todosobrimss;

import static com.todimssayuda.todosobrimss.MainActivity.quitarbordesdialogo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ConceptosTarjeton extends AppCompatActivity {

    String[] titulos, conceptos;
    LinearLayout contenedor;
    TextView[] textViewArray;  // Almacenamos los TextViews creados para poder filtrarlos
    ScrollView scrollView;
    SharedPreferences sharedPref;
    AdView bannerad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_conceptos_tarjeton);
        scrollView = findViewById(R.id.scrollconceptos);
        sharedPref = getSharedPreferences("conceptos", Context.MODE_PRIVATE);
        this.setTitle("Conceptos del tarjeton");

        //poner el scroll en la posicion guardada
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setScrollY(sharedPref.getInt("pos", 0));
            }
        }, 100);


        //implemenatcion de banner
        bannerad = findViewById(R.id.bannerconceptos);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerad.loadAd(adRequest);


        contenedor = findViewById(R.id.layoutprincipal);
        SearchView searchView = findViewById(R.id.searchView);
        titulos = getResources().getStringArray(R.array.titulosconceptos);
        conceptos = getResources().getStringArray(R.array.conceptos);
        textViewArray = new TextView[titulos.length];

        // Crear los TextViews dinámicamente
        for (int z = 0; z < titulos.length; z++) {
             int index = z;

            TextView textView = new TextView(this);
            textView.setText(titulos[z]);
            textView.setTextSize(14);
            textView.setBackground(getResources().getDrawable(R.drawable.boton_principal));
            textView.setElevation(8);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 20, 20, 20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(75, 20, 75, 20);
            // Aplicar los parámetros al TextView
            textView.setLayoutParams(layoutParams);
            textViewArray[z] = textView;
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Todos");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.modifica));
        return true;
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
            savescrollpos();
            finish();
        }
        if (item.getItemId() == R.id.item2) {

            Intent intent = new Intent(this, ShowPdf.class);
            intent.putExtra("pdf", "conceptos");
            intent.putExtra("clase", "conceptostarjeton");
            intent.putExtra("titulo", "Conceptos del Tarjeton");
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            savescrollpos();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //Metodo que guarda la posicion del scroll
    private void savescrollpos() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("pos", scrollView.getScrollY());
        editor.apply();
    }

}