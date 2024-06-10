package com.todimssayuda.todosobrimss;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

public class WbViewTarjeton extends AppCompatActivity {
    ProgressBar progresbar;
    WebView webview;
    String quincena, mes, year;
    ImageView imv;
    boolean quincenab, mesb, yearb;
   // AdView mAdView;
   // InterstitialAd mInterstitialAd;
    String urlactivos = "http://rh.imss.gob.mx/TarjetonDigital/", urljubilados = "http://rh.imss.gob.mx/tarjetonjubilados/", urldescarga = "http://rh.imss.gob.mx/tarjetondigital/Reportes/Web/wfrReporteTarjeton.aspx";
    boolean jubilados = true;

    int valor = 0;
    int[] imgsInstrucciones = new int[]{R.drawable.ins1, R.drawable.ins2, R.drawable.ins3, R.drawable.ins4, R.drawable.ins5, R.drawable.ins6, R.drawable.ins7};

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        //  nos aseguramos que no exista un archivo temporal de tarjeton
        File tarjeton = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/wfrReporteTarjeton.aspx");
        if (tarjeton.exists()) {

            tarjeton.delete();
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        imv = findViewById(R.id.imagevi);
       // mAdView = findViewById(R.id.adView1);
       // AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);


        progresbar = findViewById(R.id.pgbr);
        webview = findViewById(R.id.WebView);
/*
        InterstitialAd.load(this, getResources().getString(R.string.adinter), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i(TAG, "onAdLoaded");
            }


        });

        */
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
                    WbViewTarjeton.this.setProgress(progress * 1000);
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
                    setTitle("Descargar tarjeton");
                }


            });
            Intent intent = getIntent();
            jubilados = intent.getBooleanExtra("jubilados", false);
            if (jubilados) {
                imgsInstrucciones[5] = R.drawable.ins6j;
                imgsInstrucciones[6] = R.drawable.ins7j;
                webview.loadUrl(urljubilados);
            } else {
                webview.loadUrl(urlactivos);
            }
            instruccionesdialogo(false);
            webview.getSettings().setBuiltInZoomControls(true);

            webview.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                    Toast.makeText(WbViewTarjeton.this, "descargando", Toast.LENGTH_LONG).show();
                    final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setMimeType(mimetype);
                    //------------------------COOKIE------------------------
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    //------------------------COOKIE------------------------
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file...");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                    final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dialogo(WbViewTarjeton.this);
                    new Thread("Browser download") {
                        public void run() {
                            dm.enqueue(request);


                        }
                    }.start();

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
            MainActivity.quitarbordesdialogo(dialog);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setText("Reintentar");
            botonsi.setTextSize(10);
            botonsi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(WbViewTarjeton.this, WbViewTarjeton.this.getClass());
                    //llamamos a la actividad
                    WbViewTarjeton.this.startActivity(intent);
                    //finalizamos la actividad actual
                    WbViewTarjeton.this.finish();
                }
            });
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setTextSize(10);
            botonno.setText("Volver");
            botonno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
            });
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
        }

    }

    private void validaPermisos() {
        if ((checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        if ((shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(WbViewTarjeton.this);
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
        menu.findItem(R.id.item1).setTitle("");
        menu.findItem(R.id.item1).setIcon(getResources().getDrawable(R.drawable.reload));
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.info));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
            case R.id.item2:
                instruccionesdialogo(true);
                break;
            case R.id.item1:
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
                            WbViewTarjeton.this.setProgress(progress * 1000);
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
                            setTitle("Descargar tarjeton");

                        }


                    });
                    webview.loadUrl(urlactivos);

                    webview.getSettings().setBuiltInZoomControls(true);
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void siguiente(boolean chequed, AlertDialog dialogoorigen, AlertDialog dialogo, String mensaje){

        Toast.makeText(WbViewTarjeton.this, mensaje, Toast.LENGTH_SHORT).show();
        if (chequed) {
            //cambio de activity
            Intent intent = new Intent(WbViewTarjeton.this, PDFViewer.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(WbViewTarjeton.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
/*
    private void mostrarad(boolean chequed, AlertDialog dialogoorigen, AlertDialog dialogo, String mensaje) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(WbViewTarjeton.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    mInterstitialAd = null;
                    Toast.makeText(WbViewTarjeton.this, mensaje, Toast.LENGTH_SHORT).show();
                    if (chequed) {
                        //cambio de activity
                        Intent intent = new Intent(WbViewTarjeton.this, PDFViewer.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(WbViewTarjeton.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        } else {
            Toast.makeText(WbViewTarjeton.this, mensaje, Toast.LENGTH_SHORT).show();
            if (chequed) {
                //cambio de activity
                Intent intent = new Intent(WbViewTarjeton.this, PDFViewer.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(WbViewTarjeton.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
*/
    private void creararchivo(boolean chequed, String nombre, AlertDialog dialogoorigen) {
        File currentFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/wfrReporteTarjeton.aspx");
        File newFile = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + nombre + "wfrReporteTarjeton.pdf");
        if (currentFile.exists()) {
            if (newFile.exists()) {
                final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View vista = inflater.inflate(R.layout.dialogoconfirm, null);
                constructor.setView(vista);
                final AlertDialog dialogo = constructor.create();
                dialogo.setCancelable(false);
                MainActivity.quitarbordesdialogo(dialogo);
                TextView titulo = vista.findViewById(R.id.txtconfirm);
                Button btnno = vista.findViewById(R.id.botonno);
                Button btnsi = vista.findViewById(R.id.botonsi);
                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.cancel();
                    }
                });
                btnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newFile.delete();
                        rename(currentFile, newFile);
                        siguiente(chequed, dialogoorigen, dialogo, "Se ha sobreescrito el tarjeton");
                       // mostrarad(chequed, dialogoorigen, dialogo, "Se ha sobreescrito el tarjeton");
                    }
                });
                titulo.setText("Ya existe un archivo con ese nombre, ¿Deseas reemplazarlo?");
                dialogo.show();

            } else {
                rename(currentFile, newFile);
                siguiente(chequed, dialogoorigen, dialogoorigen, "Se ha sobreescrito el tarjeton");
               // mostrarad(chequed, dialogoorigen, dialogoorigen, "Se ha guardado el tarjeton");

            }
        } else {
            dialogoorigen.cancel();
            Toast.makeText(this, "Ha ocurrido un error en la descarga intentalo de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean rename(File from, File to) {
        return from.getParentFile().exists() && from.exists() && from.renameTo(to);
    }

    private void dialogo(final Context cont) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogoname, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        MainActivity.quitarbordesdialogo(dialog);
        Button botonok = vi.findViewById(R.id.botonokspiner);
        final CheckBox chbx = vi.findViewById(R.id.chbx);
        if (!jubilados) {
            final Spinner spinnerq = vi.findViewById(R.id.spinnerq);

            ArrayAdapter<CharSequence> adaptadorq = ArrayAdapter.createFromResource(cont, R.array.quincena, R.layout.custom_text_spinner);
            spinnerq.setAdapter(adaptadorq);


            spinnerq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        quincenab = false;
                    } else {
                        quincenab = true;
                    }
                    quincena = "" + parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            final Spinner spinnerq = vi.findViewById(R.id.spinnerq);
            spinnerq.setVisibility(View.GONE);
        }
        Spinner spinnerm = vi.findViewById(R.id.spinnerm);
        ArrayAdapter<CharSequence> adaptadorm = ArrayAdapter.createFromResource(cont, R.array.mes, R.layout.custom_text_spinner);
        spinnerm.setAdapter(adaptadorm);
        spinnerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mesb = false;
                } else {
                    mesb = true;
                }
                mes = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinnera = vi.findViewById(R.id.spinnera);
        ArrayAdapter<CharSequence> adaptadora = ArrayAdapter.createFromResource(cont, R.array.year, R.layout.custom_text_spinner);
        spinnera.setAdapter(adaptadora);
        spinnera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    yearb = false;
                } else {
                    yearb = true;
                }
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        botonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jubilados) {
                    if (mesb) {
                        if (yearb) {
                            creararchivo(chbx.isChecked(), "jub" + mes + year, dialog);
                            //dialog.cancel();

                        } else Toast.makeText(cont, "Selecciona el año", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(cont, "Selecciona el mes", Toast.LENGTH_LONG).show();
                } else if (quincenab) {
                    if (mesb) {
                        if (yearb) {
                            creararchivo(chbx.isChecked(), quincena + mes + year, dialog);
                            //dialog.cancel();

                        } else Toast.makeText(cont, "Selecciona el año", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(cont, "Selecciona el mes", Toast.LENGTH_LONG).show();
                } else Toast.makeText(cont, "Selecciona la quincena", Toast.LENGTH_LONG).show();

            }
        });

        dialog.show();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void instruccionesdialogo(boolean def) {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("instrucciones", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("instrucciones", false) || def) {
            valor = 0;
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.instrucciones_tarjeton, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            MainActivity.quitarbordesdialogo(dialogo);
            final Button botonext = vista.findViewById(R.id.botonnext);
            final Button botonback = vista.findViewById(R.id.botonback);
            botonback.setVisibility(View.INVISIBLE);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            chbx.setChecked(sharedPref.getBoolean("instrucciones", false));
            chbx.setVisibility(View.INVISIBLE);
            final ImageView imageV = vista.findViewById(R.id.imgvw);
            imageV.setBackground(WbViewTarjeton.this.getResources().getDrawable(imgsInstrucciones[valor]));
            //TextView texto = vista.findViewById(R.id.txt);
            // texto.setText(getString(R.string.mensajeinicio));
            botonback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (valor == 6) {
                        botonext.setBackground(getResources().getDrawable(R.drawable.btnflechar));
                    }
                    if (valor == 1) {
                        botonback.setVisibility(View.INVISIBLE);
                    }
                    valor--;
                    imageV.setBackground(WbViewTarjeton.this.getResources().getDrawable(imgsInstrucciones[valor]));

                }
            });
            botonext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (valor == 0) {
                        botonback.setVisibility(View.VISIBLE);
                    }
                    if (valor == 5) {
                        chbx.setVisibility(View.VISIBLE);
                        botonext.setBackground(getResources().getDrawable(R.drawable.btnchck));
                    }
                    if (valor == 6) {
                        SharedPreferences sharedPref;
                        sharedPref = getSharedPreferences("instrucciones", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("instrucciones", chbx.isChecked());
                        editor.commit();
                        dialogo.cancel();
                    } else {
                        valor++;
                        imageV.setBackground(WbViewTarjeton.this.getResources().getDrawable(imgsInstrucciones[valor]));

                    }

                }
            });
            dialogo.show();
        }
    }


}