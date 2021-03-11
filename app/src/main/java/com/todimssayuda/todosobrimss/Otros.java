package com.todimssayuda.todosobrimss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

public class Otros extends AppCompatActivity implements View.OnClickListener {

    Button n;
    ContactOtros data;
    TextView txtvw;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros);
        n = findViewById(R.id.new_elementotros);
        txtvw = findViewById(R.id.textviewaddotros);
        data = new ContactOtros(this);
        data.open();

        n.setOnClickListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        final List<ContactOtros> values = data.getAll();


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

            String Fecha = values.get(i).fechaotros.charAt(0) + "" + values.get(i).fechaotros.charAt(1) + "/" + values.get(i).fechaotros.charAt(2) + "" + values.get(i).fechaotros.charAt(3) + "/" +
                    values.get(i).fechaotros.charAt(4) + "" + values.get(i).fechaotros.charAt(5) + values.get(i).fechaotros.charAt(6) + values.get(i).fechaotros.charAt(7);

            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + NewOtros.RUTA_IMAGEN + File.separator + 0 + values.get(i).fechaotros  + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).idotros, "Servicio 1", Fecha + '\n' + values.get(i).documentootros, getResizedBitmap(bitmap, ancho, anchobtn)));
        }
        ListView listView = findViewById(android.R.id.list);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int positionotros, long idotros) {

                Intent i = new Intent(Otros.this, EditOtros.class);
                i.putExtra("otrosid", values.get(positionotros).idotros);
                i.putExtra("otrosfecha", values.get(positionotros).fechaotros);
                i.putExtra("otrosdocumentos", values.get(positionotros).documentootros);
                i.putExtra("otrosmotivo", values.get(positionotros).motivootros);
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


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
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
        Intent i = new Intent(this, NewOtros.class);
        startActivity(i);
        finish();
    }
}