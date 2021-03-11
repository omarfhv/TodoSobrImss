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

public class SustisAgenda extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    Button newitem;
    ContactSustis data;
    TextView txtvw;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustis_agenda);
        newitem = findViewById(R.id.new_element);
        newitem.setOnClickListener(this);
        txtvw = findViewById(R.id.textviewadd);
        data = new ContactSustis(this);
        data.open();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final List<ContactSustis> values = data.getAll();
        if (values.size() == 0) {
            txtvw.setText("Agrega elementos desde aqui");
        } else {
            txtvw.setText("Selecciona para editar o eleminiar");
        }
        ArrayList<Category> category = new ArrayList<Category>();
        for (int i = 0; i < values.size(); i++) {

            String paths = Environment.getExternalStorageDirectory() +
                    File.separator + NewSusti.RUTA_IMAGEN + File.separator + 0 + values.get(i).fechasustis + "" + values.get(i).chbx + ".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(paths);
            category.add(new Category("olo" + values.get(i).idsustis, "Servicio 1", values.get(i).fechasustis.charAt(0) + "" + values.get(i).fechasustis.charAt(1) + "/" + values.get(i).fechasustis.charAt(2) + "" + values.get(i).fechasustis.charAt(3) + "/" +
                    values.get(i).fechasustis.charAt(4) + "" + values.get(i).fechasustis.charAt(5) + values.get(i).fechasustis.charAt(6) + values.get(i).fechasustis.charAt(7) + '\n' + values.get(i).rbtnsustis, PasesAgenda.getResizedBitmap(bitmap, this)));
        }
        ListView listView = findViewById(android.R.id.list);
        AdapterCategory adapter = new AdapterCategory(this, category);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int positionsustis, long idsustis) {
        final List<ContactSustis> values = data.getAll();
        Intent i = new Intent(this, EditSusti.class);
        i.putExtra("idsusti", values.get(positionsustis).idsustis);
        i.putExtra("fechasusti", values.get(positionsustis).fechasustis);
        i.putExtra("namesusti", values.get(positionsustis).nombresustis);
        i.putExtra("phonesusti", values.get(positionsustis).phonesustis);
        i.putExtra("rbtnsusti", values.get(positionsustis).rbtnsustis);
        i.putExtra("chbx", values.get(positionsustis).chbx);
        fileList();
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, NewSusti.class);
        startActivity(i);
        finish();
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

    public Bitmap getResizedBitma(Bitmap bm) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) metrics.widthPixels / (100) * 28) / width;
        float scaleHeight = ((float) metrics.widthPixels / (100) * 28) / height;
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
}

