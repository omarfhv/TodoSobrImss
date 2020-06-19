package com.todimssayuda.todosobrimss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecuJubilados extends AppCompatActivity {
    ProgressBar progresbar;
    WebView webview;
    String quincena, mes, year;
    ImageView imv;
    ColorDrawable dialogColor;
    boolean quincenab, mesb, yearb;
    Button btndescargar;
   // InterstitialAd mInterstitialAd;
    static int[] id = new int[]{R.drawable.q, R.drawable.qj, R.drawable.qj1, R.drawable.q4, R.drawable.q5, R.drawable.q6, R.drawable.q7, R.drawable.q8};
    private AdView mAdView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recu_jubilados);
        instruccionesdialogo(false);
        imv = findViewById(R.id.imagevi);


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
                    RecuJubilados.this.setProgress(progress * 1000);
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
                    setTitle("Contraseña Jubilados");
                }


            });


            webview.loadUrl("http://rh.imss.gob.mx/tarjetonjubilados/(S(bue2h4awkwxyebgnn3bld52a))/default.aspx");
            webview.getSettings().setBuiltInZoomControls(true);



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
                            intent.setClass(RecuJubilados.this, RecuJubilados.this.getClass());
                            //llamamos a la actividad
                            RecuJubilados.this.startActivity(intent);
                            //finalizamos la actividad actual
                            RecuJubilados.this.finish();
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
                            startActivity(new Intent(getBaseContext(), RecuperarContrasena.class)
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
            AlertDialog.Builder dialogo = new AlertDialog.Builder(RecuJubilados.this);
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
        getMenuInflater().inflate(R.menu.menuwebview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), RecuperarContrasena.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
            case R.id.info:
                instruccionesdialogo(true);
                break;
            case R.id.reload:
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
                            RecuJubilados.this.setProgress(progress * 1000);
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
                            setTitle("Contraseña Jubilados");
                        }


                    });
                    webview.loadUrl("http://rh.imss.gob.mx/tarjetonjubilados/(S(bue2h4awkwxyebgnn3bld52a))/default.aspx");
                    webview.getSettings().setBuiltInZoomControls(true);
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), RecuperarContrasena.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    static public int instruccionesbotones(ImageView imv, int num, boolean contador, Context context) {
        if (contador) {
            num++;
        } else {
            num--;
        }
        imv.setBackground(context.getResources().getDrawable(id[num]));


        return num;
    }

    private void instruccionesdialogo(boolean def) {
        SharedPreferences sharedPref;
        sharedPref = getSharedPreferences("instruccionesa", Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean("instruccionesa", false) || def) {
            final int[] cont = {0};
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.instrucciones, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            dialogo.getWindow().setBackgroundDrawable(dialogColor);
            final Button botonext = vista.findViewById(R.id.botonnext);
            final Button botonback = vista.findViewById(R.id.botonback);
            botonback.setVisibility(View.INVISIBLE);
            final CheckBox chbx = vista.findViewById(R.id.chbxdialog);
            chbx.setChecked(sharedPref.getBoolean("instruccionesa", false));
            final ImageView imageV = vista.findViewById(R.id.imgvw);
            //TextView texto = vista.findViewById(R.id.txt);
            // texto.setText(getString(R.string.mensajeinicio));
            botonback.setOnClickListener(new View.OnClickListener() {
                                             @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                             @Override
                                             public void onClick(View v) {
                                                 if (cont[0] == 6) {
                                                     botonext.setBackground(getResources().getDrawable(R.drawable.btnflechar));
                                                 }
                                                 if (cont[0] == 1) {
                                                     botonback.setVisibility(View.INVISIBLE);
                                                 }
                                                 cont[0] = instruccionesbotones(imageV, cont[0], false, RecuJubilados.this);
                                             }
                                         }
            );
            botonext.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    if (cont[0] == 0) {
                        botonback.setVisibility(View.VISIBLE);
                    }
                    if (cont[0] == 6) {
                        botonext.setBackground(getResources().getDrawable(R.drawable.btnchck));
                    }
                    if (cont[0] == 7) {
                        SharedPreferences sharedPref;
                        sharedPref = getSharedPreferences(
                                "instruccionesa", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("instruccionesa", chbx.isChecked());
                        editor.commit();
                        dialogo.cancel();
                    } else {
                        cont[0] = instruccionesbotones(imageV, cont[0], true, RecuJubilados.this);
                    }

                }
            });
            dialogo.show();
        }
    }


}