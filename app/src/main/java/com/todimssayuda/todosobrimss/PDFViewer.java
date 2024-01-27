package com.todimssayuda.todosobrimss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;

public class PDFViewer extends AppCompatActivity {
    ListView lv_pdf;
    public static ArrayList<File> fileList;
    ;
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    File dir;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setContentView(R.layout.activity_pdfviewer);
        init();

        this.setTitle("Consulta tarjeton");

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void init() {
        lv_pdf = findViewById(R.id.lv_pdf);
        dir = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            boolean_permission = true;
            getfile(dir);
            obj_adapter = new PDFAdapter(getApplicationContext(), fileList);
            lv_pdf.setAdapter(obj_adapter);
            // versiones con android 13.0 o superior
        } else {
            // para versiones anteriores a android 13.0
            fn_permission();
        }


        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PDFActivitybis.class);
                intent.putExtra("position", i);
                startActivity(intent);
                Log.e("Position", i + "");
            }
        });
    }

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();


        fileList = new ArrayList<File>();

        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                System.out.println(listFile[i].getName());
                if (listFile[i].isDirectory()) {
                    //getfile(listFile[i]);
                    // Toast.makeText(this, "es una subcarpeta" + listFile[i].getName(), Toast.LENGTH_SHORT).show();

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith("Tarjeton.aspx") || listFile[i].getName().endsWith("Tarjeton.pdf")) {

                        for (int j = 0; j < fileList.size(); j++) {

                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;

                            }
                        }

                        if (!booleanpdf) {
                            System.out.println("" + i);
                            fileList.add(listFile[i]);

                        }
                    }
                }

            }
        }
        System.out.println(fileList.size());
        System.out.println(listFile.length);
        //Toast.makeText(this, " " + listFile.length, Toast.LENGTH_SHORT).show();
        return fileList;
    }


    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((!ActivityCompat.shouldShowRequestPermissionRationale(PDFViewer.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
                ActivityCompat.requestPermissions(PDFViewer.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
            obj_adapter = new PDFAdapter(getApplicationContext(), getfile(dir));
            System.out.println(getfile(dir).size());
            lv_pdf.setAdapter(obj_adapter);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //boolean_permission = true;
                // getfile(dir);
                // obj_adapter = new PDFAdapter(getApplicationContext(), fileList);
                //lv_pdf.setAdapter(obj_adapter);

            } else {


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