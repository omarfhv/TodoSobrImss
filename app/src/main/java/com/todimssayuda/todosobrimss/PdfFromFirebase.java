package com.todimssayuda.todosobrimss;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

public class PdfFromFirebase extends AppCompatActivity {

    private PDFView pdfView;
    private TextView label;
    private Button reloadButton;
    private Handler timeoutHandler = new Handler(Looper.getMainLooper());
    private boolean didTimeout = false;
    AdView mAdView;
    File localFile;
    String titulo, nombre;
    Class<?> clase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_imss);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titulo = getIntent().getStringExtra("titulo");
        this.setTitle(titulo);
        nombre = getIntent().getStringExtra("nombre");
        String claseorigen = getIntent().getStringExtra("claseorigen");
        try {
            clase = Class.forName(claseorigen);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

         localFile = new File(this.getFilesDir(), nombre);

        pdfView = findViewById(R.id.pdfView);
        label = findViewById(R.id.textLabel);
        reloadButton = findViewById(R.id.btnReload);

        reloadButton.setOnClickListener(v -> {
            label.setVisibility(View.VISIBLE);
            loadPDFfromFirebase();
        });

        loadPDFfromFirebase();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), clase).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), clase).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPDFfromFirebase() {
        isConnectedToInternet(isConnected -> {
            if (!isConnected) {
                showAlert("Sin conexión", "No tienes conexión a internet.");
                reloadButton.setVisibility(View.VISIBLE);
                label.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
                return;
            }

            FirebaseStorage storage = FirebaseStorage.getInstance("gs://tarjetondigitalios.firebasestorage.app/");
            StorageReference pdfRef = storage.getReference().child(nombre);

            int timeoutMillis = 10000;
            didTimeout = false;
            timeoutHandler.postDelayed(() -> {
                didTimeout = true;
                showAlert("Tiempo de espera agotado", "No se pudo descargar el archivo. Intenta nuevamente.");
                reloadButton.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                label.setVisibility(View.GONE);
            }, timeoutMillis);

            pdfRef.getMetadata().addOnSuccessListener(metadata -> {
                if (didTimeout) return;

                String remoteMd5 = metadata.getMd5Hash(); // ya viene codificado en base64
                if (localFile.exists()) {
                    String localMd5 = getMd5FromFile(localFile);
                    String localMd5Base64 = null;
                    try {
                        localMd5Base64 = Base64.encodeToString(
                                MessageDigest.getInstance("MD5").digest(localMd5.getBytes()),
                                Base64.NO_WRAP
                        );
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    if (remoteMd5 != null && remoteMd5.equals(localMd5Base64)) {
                        timeoutHandler.removeCallbacksAndMessages(null);
                        showLocalPDF();
                        return;
                    }
                }

                pdfRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    if (didTimeout) return;
                    timeoutHandler.removeCallbacksAndMessages(null);
                    showLocalPDF();
                }).addOnFailureListener(e -> {
                    if (didTimeout) return;
                    timeoutHandler.removeCallbacksAndMessages(null);
                    showAlert("Error", "No se pudo obtener el archivo: " + e.getMessage());
                    reloadButton.setVisibility(View.VISIBLE);
                    label.setVisibility(View.GONE);
                    pdfView.setVisibility(View.GONE);
                });

            }).addOnFailureListener(e -> {
                if (didTimeout) return;
                timeoutHandler.removeCallbacksAndMessages(null);
                showAlert("Error", "No se pudo obtener los metadatos del archivo: " + e.getMessage());
                reloadButton.setVisibility(View.VISIBLE);
                label.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
            });
        });
    }

    private void showLocalPDF() {
        pdfView.setVisibility(View.VISIBLE);
        pdfView.fromFile(localFile).load();
        label.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
    }

    private void isConnectedToInternet(Consumer<Boolean> callback) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        callback.accept(isConnected);
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private String getMd5FromFile(File file) {
        try (InputStream is = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = is.read(buffer)) != -1) {
                md.update(buffer, 0, numRead);
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e("PDF", "Error calculando MD5: " + e.getMessage());
            return "";
        }
    }

}
