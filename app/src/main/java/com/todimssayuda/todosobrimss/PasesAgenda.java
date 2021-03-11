package com.todimssayuda.todosobrimss;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PasesAgenda extends AppCompatActivity implements View.OnClickListener {

    Button n;
    Contact data;
    TextView txtvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pases_agenda);
        n = findViewById(R.id.new_element);
        txtvw = findViewById(R.id.textviewadd);
        data = new Contact(this);
        data.open();

        n.setOnClickListener(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final List<Contact> values = data.getAll();


        // ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1, values);
        //setListAdapter(adapter);
        //ListView listView = getListView();
        //Drawable res = getResources().getDrawable(R.drawable.btnagregar);

        if (values.size() == 0) {
            txtvw.setText("Agrega elementos desde aqui");
        } else {
            txtvw.setText("Selecciona para editar o eleminiar");
        }
        // Toast.makeText(this, ""+ values.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Category> category = new ArrayList<Category>();
        for (int i = 0; i < values.size(); i++) {

            String Fecha = values.get(i).fecha.charAt(0) + "" + values.get(i).fecha.charAt(1) + "/" + values.get(i).fecha.charAt(2) + "" + values.get(i).fecha.charAt(3) + "/" +
                    values.get(i).fecha.charAt(4) + "" + values.get(i).fecha.charAt(5) + values.get(i).fecha.charAt(6) + values.get(i).fecha.charAt(7);

           /*String paths = Environment.getExternalStorageDirectory() +
                    File.separator + New.RUTA_IMAGEN + File.separator + 0 + values.get(i).fecha + values.get(i).idimagen + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            */
          //  category.add(new Category("olo" + values.get(i).id, "Servicio 1", Fecha + '\n' + values.get(i).radibuttonnid, getResizedBitmap(bitmap, this)));
        }
        ListView listView = findViewById(R.id.lista);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
/*
                Intent i = new Intent(PasesAgenda.this, Edit.class);
                i.putExtra("paseid", values.get(position).id);
                i.putExtra("pasefecha", values.get(position).fecha);
                i.putExtra("paserbtn", values.get(position).radibuttonnid);
                i.putExtra("pasehora", values.get(position).horas);
                i.putExtra("pasemotivo", values.get(position).motivo);
                i.putExtra("idimagen", values.get(position).idimagen);
                startActivity(i);
                fileList();
*/
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


    public static Bitmap getResizedBitmap(Bitmap bm, Context context) {
        Bitmap resizedBitmap;
        if (bm != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) metrics.widthPixels / (100) * 28) / width;
            float scaleHeight = ((float) metrics.widthPixels / (100) * 28) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
        } else {
            resizedBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }
        return resizedBitmap;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentds = new Intent(PasesAgenda.this, MenuPrincipalAgenda.class);
            startActivity(intentds);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
       Intent i = new Intent(PasesAgenda.this, New.class);
       startActivity(i);
        finish();
    }
}