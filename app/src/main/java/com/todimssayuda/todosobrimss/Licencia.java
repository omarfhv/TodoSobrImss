package com.todimssayuda.todosobrimss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Licencia extends AppCompatActivity implements View.OnClickListener {

    Button n;
    ContactLicencia data;
    TextView txtvw;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licencia);
        n = findViewById(R.id.new_elementlicencia);
        txtvw = findViewById(R.id.textviewaddlicencia);
        data = new ContactLicencia(this);
        data.open();

        n.setOnClickListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final List<ContactLicencia> values = data.getAll();


        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
        //ListView listView = getListView();
        //Drawable res = getResources().getDrawable(R.drawable.btnagregar);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int ancho = metrics.widthPixels / (20) * 8;
        int anchobtn = metrics.widthPixels / (20) * 8;
        if (values.size() == 0) {
            txtvw.setText("Agrega elementos desde aqui");
        } else {
            txtvw.setText("Selecciona para editar o eleminiar");
        }
        // Toast.makeText(this, ""+ values.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Category> category = new ArrayList<Category>();
        for (int i = 0; i < values.size(); i++) {

            String Fecha = values.get(i).fechainiciolicencia.charAt(0) + "" + values.get(i).fechainiciolicencia.charAt(1) + "/" + values.get(i).fechainiciolicencia.charAt(2) + "" + values.get(i).fechainiciolicencia.charAt(3) + "/" +
                    values.get(i).fechainiciolicencia.charAt(4) + "" + values.get(i).fechainiciolicencia.charAt(5) + values.get(i).fechainiciolicencia.charAt(6) + values.get(i).fechainiciolicencia.charAt(7);
            String Fecha2 = values.get(i).fechafinallicencia.charAt(0) + "" + values.get(i).fechafinallicencia.charAt(1) + "/" + values.get(i).fechafinallicencia.charAt(2) + "" + values.get(i).fechafinallicencia.charAt(3) + "/" +
                    values.get(i).fechafinallicencia.charAt(4) + "" + values.get(i).fechafinallicencia.charAt(5) + values.get(i).fechafinallicencia.charAt(6) + values.get(i).fechafinallicencia.charAt(7);

            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + NewLicencia.RUTA_IMAGEN + File.separator + 0 + values.get(i).fechainiciolicencia + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).idlicencia, "Servicio 1", Fecha + " a " + Fecha2 + '\n' + values.get(i).rbtnlicencia, PasesAgenda.getResizedBitmap(bitmap, this)));
        }
        ListView listView = findViewById(android.R.id.list);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int positionlicencia, long idlicencia) {

                Intent i = new Intent( Licencia.this, EditLicencia.class);
                i.putExtra("licenciaid", values.get(positionlicencia).idlicencia);
                i.putExtra("licenciafecha", values.get(positionlicencia).fechainiciolicencia);
                i.putExtra("licenciafechafinal", values.get(positionlicencia).fechafinallicencia);
                i.putExtra("licenciarbtn", values.get(positionlicencia).rbtnlicencia);
                i.putExtra("licenciamotivo", values.get(positionlicencia).motivolicencia);
                startActivity(i);
                fileList();

                startActivity(i);
                fileList();

            }

        });


    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();

        //Refresh your stuff here
        // final List<Contact> values = data.getAll();
        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MenuPrincipalAgenda.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentds = new Intent(this, MenuPrincipalAgenda.class);
            startActivity(intentds);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Licencia.this, NewLicencia.class);
        startActivity(i);
        finish();
    }
}