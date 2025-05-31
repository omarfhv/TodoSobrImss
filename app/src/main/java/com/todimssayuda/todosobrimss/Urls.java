package com.todimssayuda.todosobrimss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;

public class Urls extends AppCompatActivity {
   // AdView mAdView;

    ProgressBar progresbar;
    WebView webview;
    ImageView imv;
    ColorDrawable dialogColor;
    AdView mAdView;
    String url, titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_urls);
        imv = findViewById(R.id.imagevi);
        //cargar datos de la actividad anterior
        Bundle parametros = this.getIntent().getExtras();
        url = parametros.getString("url");
        titulo = parametros.getString("titulo");


        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progresbar = findViewById(R.id.pgbr);
        webview = findViewById(R.id.WebView);
        validaPermisos();


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento

            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    Urls.this.setProgress(progress * 1000);
                }
            });
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progresbar.setVisibility(View.VISIBLE);
                    imv.setVisibility(View.VISIBLE);
                    setTitle(" Cargando ");
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progresbar.setVisibility(View.GONE);
                    imv.setVisibility(View.GONE);
                    setTitle(titulo);
                }


            });

            webview.loadUrl(url);
           // webview.loadUrl("https://www.sntss.org.mx/promociones");
            webview.getSettings().setBuiltInZoomControls(true);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
                    return;
                }
            }

            webview.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                    Toast.makeText(Urls.this, "Descargando...", Toast.LENGTH_LONG).show();

                    String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                    Uri uri = Uri.parse(url);

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setMimeType(mimetype);

                    // Cookies y user-agent
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    request.addRequestHeader("User-Agent", userAgent);

                    request.setDescription("Descargando archivo...");
                    request.setTitle(fileName);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                    final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    final long downloadId = dm.enqueue(request); // Guardamos el ID de la descarga

                    // Registramos un BroadcastReceiver para saber cuándo se completa la descarga
                    BroadcastReceiver onComplete = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                            if (id == downloadId) {
                                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

                                if (file.exists()) {
                                    Uri fileUri = FileProvider.getUriForFile(Urls.this, getPackageName() + ".fileprovider", file);
                                    Intent openPdfIntent = new Intent(Intent.ACTION_VIEW);
                                    openPdfIntent.setDataAndType(fileUri, "application/pdf");
                                    openPdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    openPdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                    try {
                                        startActivity(openPdfIntent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                        Toast.makeText(Urls.this, "No se encontró una app para abrir el PDF", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(Urls.this, "Archivo no encontrado", Toast.LENGTH_SHORT).show();
                                }

                                // Unregister receiver para no dejarlo colgado
                                unregisterReceiver(this);
                            }
                        }
                    };

                    // Registrar el BroadcastReceiver
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
                    }

                }
            });




        } else {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            TextView txtv = vi.findViewById(R.id.txtconfirm);
            txtv.setText("No cuentas con conexion a internet ");
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(dialogColor);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setText("Reintentar");
            botonsi.setTextSize(10);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(Urls.this, Urls.this.getClass());
                            //llamamos a la actividad
                            Urls.this.startActivity(intent);
                            //finalizamos la actividad actual
                            Urls.this.finish();
                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setTextSize(10);
            botonno.setText("Volver");
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getBaseContext(), MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            finish();
                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
        }

    }

    private void validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if ((checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        if ((shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(Urls.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
                }
            });
            dialogo.show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Actualizar");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.reload));

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
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    // Si hay conexión a Internet en este momento

                    WebSettings webSettings = webview.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webview.setWebChromeClient(new WebChromeClient() {
                        public void onProgressChanged(WebView view, int progress) {
                            // Activities and WebViews measure progress with different scales.
                            // The progress meter will automatically disappear when we reach 100%
                            Urls.this.setProgress(progress * 1000);
                        }
                    });
                    webview.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
                            progresbar.setVisibility(View.VISIBLE);
                            imv.setVisibility(View.VISIBLE);
                            setTitle(" Cargando ");
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progresbar.setVisibility(View.GONE);
                            imv.setVisibility(View.GONE);
                            setTitle(titulo);
                        }


                    });
                    webview.loadUrl(url);
                    webview.getSettings().setBuiltInZoomControls(true);
                }
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


}