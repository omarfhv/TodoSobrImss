package com.todimssayuda.todosobrimss;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISO_NOTIFICACIONES = 1;
    LinearLayout btn1;
    LinearLayout botonconvenioescuela, botonconvocatoria, botontiempoextra, botonmedianoplazo, botonprestamocarro, botontarjeton, botoncalendario, botonpromociones, botonenterate, botonrol, botonconsulta, botoncct, botonfaltas, botontabulador, botoncursos, botonpermutas, botonpases, botonpliego, botonsustis, botondias, botonjubilacion, botontiposdecontrato, botonincapacidades, botonseguro, botonrecuperar, botonbono, botonpresta, botonsegunda, botoncalcuvacas, botoncajadeahorro, botonaguinaldo, botonhipotecario, botonconceptos, botonclausulanoventaysiete;
    SharedPreferences sharedPref;
    Intent intent;
    InterstitialAd mInterstitialAd;
    AdView mAdView;
    int califica;

    LinearLayout bloqueo;
    Button link;
    ScrollView scrollView;


    int contadorads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        contadorads = sharedPref.getInt("contadorads", 0);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);


        bloqueo = findViewById(R.id.bloqueo);
        link = findViewById(R.id.botonenlacefb);
        if (yy <= 2024) {
            if (mm < 7 || mm == 11) {
                bloqueo.setVisibility(View.INVISIBLE);
            }
        }

        PedirPermisonotificaciones();

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        InterstitialAd.load(this, getResources().getString(R.string.adinter), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });


        califica = sharedPref.getInt("califica", 0);
        boolean binfo = sharedPref.getBoolean("infoinicio", true);
        if (binfo)
            dialogoinfo();

        if (califica == 8) {
            dialogocalifica();
            califica = 0;
        } else
            califica++;

        // actualizar posicion de scroll
        scrollView = findViewById(R.id.scrollview);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setScrollY(sharedPref.getInt("pos", 0));
            }
        }, 100);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor = sharedPref.edit();
        editor.putInt("califica", califica);
        editor.apply();

        botontiempoextra = findViewById(R.id.botontiempoextra);
        botontiempoextra.setOnClickListener(this);

        botonmedianoplazo = findViewById(R.id.botonmedianoplazo);
        botonmedianoplazo.setOnClickListener(this);

        botonprestamocarro = findViewById(R.id.botoncarro);
        botonprestamocarro.setOnClickListener(this);

        botontarjeton = findViewById(R.id.botontarjeton);
        botontarjeton.setOnClickListener(this);

        botoncalendario = findViewById(R.id.botoncalendario);
        botoncalendario.setOnClickListener(this);

        botonpromociones = findViewById(R.id.botonpromociones);
        botonpromociones.setOnClickListener(this);

        botonconvenioescuela = findViewById(R.id.botonconvenioescuela);
        botonconvenioescuela.setOnClickListener(this);

        botonconvocatoria = findViewById(R.id.botonconvocatorias);
        botonconvocatoria.setOnClickListener(this);

        botonenterate = findViewById(R.id.botonenterate);
        botonenterate.setOnClickListener(this);

        botonrol = findViewById(R.id.botonrol);
        botonrol.setOnClickListener(this);

        botonconsulta = findViewById(R.id.botonconsulta);
        botonconsulta.setOnClickListener(this);

        botoncct = findViewById(R.id.botoncont);
        botoncct.setOnClickListener(this);

        botonfaltas = findViewById(R.id.botonfalta);
        botonfaltas.setOnClickListener(this);

        botontabulador = findViewById(R.id.botontabula);
        botontabulador.setOnClickListener(this);

        botontarjeton = findViewById(R.id.botontarjeton);
        botontarjeton.setOnClickListener(this);

        botoncursos = findViewById(R.id.botoncursos);
        botoncursos.setOnClickListener(this);

        botonpermutas = findViewById(R.id.botonpermutas);
        botonpermutas.setOnClickListener(this);

        botonpases = findViewById(R.id.botonpases);
        botonpases.setOnClickListener(this);

        botonpliego = findViewById(R.id.botonpliego);
        botonpliego.setOnClickListener(this);

        botonsustis = findViewById(R.id.botontxt);
        botonsustis.setOnClickListener(this);

        botondias = findViewById(R.id.botondias);
        botondias.setOnClickListener(this);

        botonjubilacion = findViewById(R.id.botonjubilacion);
        botonjubilacion.setOnClickListener(this);

        botontiposdecontrato = findViewById(R.id.botoncontratos);
        botontiposdecontrato.setOnClickListener(this);

        botonincapacidades = findViewById(R.id.botonincapa);
        botonincapacidades.setOnClickListener(this);

        botonseguro = findViewById(R.id.botonseguro);
        botonseguro.setOnClickListener(this);

        botonrecuperar = findViewById(R.id.botonrecupera);
        botonrecuperar.setOnClickListener(this);

        botonbono = findViewById(R.id.botonbono);
        botonbono.setOnClickListener(this);

        botonpresta = findViewById(R.id.botonprestamos);
        botonpresta.setOnClickListener(this);

        botonsegunda = findViewById(R.id.botonsegundadejulio);
        botonsegunda.setOnClickListener(this);

        botoncalcuvacas = findViewById(R.id.botoncalcuvaca);
        botoncalcuvacas.setOnClickListener(this);

        botoncajadeahorro = findViewById(R.id.botoncajadeahorro);
        botoncajadeahorro.setOnClickListener(this);

        botonaguinaldo = findViewById(R.id.botonaguinaldo);
        botonaguinaldo.setOnClickListener(this);

        botonhipotecario = findViewById(R.id.botonhipotecario);
        botonhipotecario.setOnClickListener(this);

        botonconceptos = findViewById(R.id.botonconceptos);
        botonconceptos.setOnClickListener(this);

        botonclausulanoventaysiete = findViewById(R.id.botonclausulanoventaysiete);
        botonclausulanoventaysiete.setOnClickListener(this);

        link.setOnClickListener(this);


    }

    private void dialogoinfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogo_requisitos, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        quitarbordesdialogo(dialog);
        TextView titulo = vi.findViewById(R.id.titulo);
        titulo.setText("Aviso");
        TextView txtconf = vi.findViewById(R.id.texto);
        txtconf.setTextSize(15);
        txtconf.setText("La información proporcionada por esta aplicación es solo para facilitarle el acceso para el descargo del tarjetón que se obtiene directamente de los sitios http://rh.imss.gob.mx/TarjetonDigital/ y http://rh.imss.gob.mx/tarjetonjubilados/(S(ilsapuvnqy5bppvgfk3nghep))/default.aspx . Así como la facilidad de consultar  varios documentos que se encuentran en la página del https://sntss.org.mx/ y por último obtener de una manera más fácil la información de la caja de ahorros que se proporciona del link https://www.cpasntss.mx/ y con todo esto ayudar a los trabajadores del instituto\n" +
                "Sin embargo, no hacemos ninguna representación o garantía de ningún tipo, expresa o implícita, con respecto a la precisión, adecuación, validez, confiabilidad, disponibilidad o integridad de cualquier información en los Sitios [o nuestra aplicación móvil].");

        Button botonno = vi.findViewById(R.id.botonok);


        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("infoinicio", false);
                editor.apply();
                dialog.dismiss();

            }
        });

        dialog.show();

    }


    private void dialogocalifica() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogocalifica, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        quitarbordesdialogo(dialog);
        Button botonsi = vi.findViewById(R.id.botonsi);
        botonsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.todimssayuda.todosobrimss")));
            }
        });
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mInterstitialAd.show(MainActivity.this);


            }
        });

        dialog.show();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        contadorads++;
        SharedPreferences.Editor editor = sharedPref.edit();
        if (mInterstitialAd != null && contadorads > 7) {
            mInterstitialAd.show(MainActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    contadorads = 0;
                    editor.putInt("contadorads", contadorads);
                    editor.apply();

                    botonessinads(view);
                }
            });
        } else {
            editor.putInt("contadorads", contadorads);
            editor.apply();
            botonessinads(view);

        }

    }

    private void botonessinads(View view) {

        switch (view.getId()) {
            case R.id.botonenlacefb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tarjetondigitalimss/"));
                startActivity(intent);
                break;

            case R.id.botontarjeton:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.dialogo_tarjeton, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                quitarbordesdialogo(dialog);
                Button botonok = vi.findViewById(R.id.botoncont);
                final RadioButton rbtna = vi.findViewById(R.id.rbtna);
                final RadioButton rbtnj = vi.findViewById(R.id.rbtnj);
                botonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (rbtna.isChecked()) {
                            Intent intent1 = new Intent(MainActivity.this, WbViewTarjeton.class);
                            intent1.putExtra("jubilados", false);
                            startActivity(intent1);
                            finish();
                        }
                        if (rbtnj.isChecked()) {

                            Intent intent1 = new Intent(MainActivity.this, WbViewTarjeton.class);
                            intent1.putExtra("jubilados", true);
                            startActivity(intent1);
                            finish();

                        }


                    }
                });

                dialog.show();
                break;

            case R.id.botoncalendario:
                cambioActivity(Calendario.class);
                break;
            //web
            case R.id.botonpromociones:
                cambioActivityUrl("https://www.sntss.org.mx/promociones", "Promociones");
                break;
            //web
            case R.id.botonenterate:
                cambioActivityUrl("https://eltioimss.blogspot.com/?m=1", "Notificaciones");
                break;
            //web
            case R.id.botonconvocatorias:
                cambioActivityUrl("https://sntss.org.mx/convocatorias", "Convocatorias");
                break;

            //pantalla con boton en actionbar hacia pdf
            case R.id.botonrol:
                cambioActivity(RolVacacional.class);
                break;

            //pantalla con boton en actionbar hacia pdf
            case R.id.botonconvenioescuela:
                cambioActivityPdf("conveniosntss", "", "Convenios");
                //cambioActivity(RolVacacional.class);
                break;


            case R.id.botonconsulta:
                Toast toast = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.lytLayout));
                TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("Esto puede tardar unos segundos, favor de esperar " +
                        "GRACIAS");

                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                intent = new Intent(this, PDFViewer.class);
                startActivity(intent);
                finish();
                break;

            //pantalla con un boton en actionbar hacia pdf
            case R.id.botoncont:
                cambioActivity(ContratoColectivoTrabajadores.class);

                break;

            case R.id.botonfalta:
                cambioActivityPdf("faltas1", "", "Faltas");
                break;

            case R.id.botontabula:
                cambioActivityPdf("tabula", "", "Tabulador 2024");
                break;

            case R.id.botoncursos:
                cambioActivity(CursosIm.class);
                break;

            //web
            case R.id.botonpermutas:
                cambioActivityUrl("https://www.sntss.org.mx/permutas", "Permutas");
                break;

            //pantalla con dos botones
            case R.id.botonpases:
                cambioActivity(Pases.class);
                break;

            case R.id.botonpliego:
                cambioActivity(PliegoTesta.class);
                break;

            case R.id.botontxt:
                cambioActivityPdf("convenio", "", "Convenio TXT 2022");
                break;

            case R.id.botondias:
                cambioActivityPdf("festivos22", "", "Dias festivos 2024");
                break;

            case R.id.botonjubilacion:
                cambioActivityPdf("jubila", "", "Req. para tramitar jubilacion");
                break;
            //muchos cuadros de texto fijos
            case R.id.botoncontratos:
                cambioActivity(TiposDeContrato.class);
                break;
            //pantalla con un boton
            case R.id.botonincapa:
                cambioActivity(Incapacidades.class);
                break;

            case R.id.botonseguro:
                cambioActivity(SeguroFacul.class);
                break;

            case R.id.botonrecupera:
                cambioActivity(RecuperarContrasena.class);
                break;
            //pantalla con boton en actionbar hacia web
            case R.id.botonbono:
                cambioActivity(Declaracion.class);
                break;

            //pantalla con boton en action bar hacia calculadora
            case R.id.botonprestamos:
                cambioActivity(PrestamosPorCategoria.class);
                break;
            //calculadora
            case R.id.botonsegundadejulio:
                cambioActivity(CalcSegundaDeJulio.class);
                break;

            case R.id.botoncalcuvaca:
                cambioActivity(MenuCalcVacaciones.class);
                break;
            //web
            case R.id.botoncajadeahorro:
                cambioActivityUrl("https://www.cpasntss.mx/", "Caja de ahorro");

                break;
            //calculadora
            case R.id.botonaguinaldo:
                cambioActivity(CalcAguinaldo.class);
                break;

            case R.id.botonhipotecario:
                cambioActivity(CalcPrestamoHipotecario.class);
                break;

            case R.id.botonmedianoplazo:
                cambioActivity(CalcPrestamoMedianoPlazo.class);
                break;

            case R.id.botoncarro:
                cambioActivity(CalcPrestamoCarro.class);
                break;
            case R.id.botontiempoextra:
                cambioActivity(CalcTiempoExtra.class);
                break;

            case R.id.botonconceptos:
                cambioActivityPdf("conceptos", "", "Conceptos Tarjeton");
                break;
            case R.id.botonclausulanoventaysiete:
                cambioActivity(CalcClausulaNoventySiete.class);
                break;
        }

        //guardar posicion de scroll
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("pos", scrollView.getScrollY());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        menu.findItem(R.id.item1).setTitle("Info");
        menu.findItem(R.id.item1).setIcon(null);
        menu.findItem(R.id.item2).setTitle("");
        menu.findItem(R.id.item2).setIcon(getResources().getDrawable(R.drawable.info));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item2) {
            dialogoinfo();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            quitarbordesdialogo(dialog);
            //decidir despues si sera cancelable o no
            dialog.setCancelable(false);
            Button botonsi = vi.findViewById(R.id.botonsi);
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            MainActivity.super.onDestroy();
                            System.exit(0);
                        }
                    }
            );
            Button botonno = vi.findViewById(R.id.botonno);
            botonno.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();

                        }
                    }
            );
            dialog.show();
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void cambioActivityPdf(String pdf, String clase, String titulo) {

        intent = new Intent(this, ShowPdf.class);
        intent.putExtra("pdf", pdf);
        intent.putExtra("titulo", titulo);
        intent.putExtra("clase", clase);
        startActivity(intent);
        finish();
    }

    public void cambioActivityUrl(String url, String titulo) {

        intent = new Intent(this, Urls.class);
        intent.putExtra("url", url);
        intent.putExtra("titulo", titulo);

        startActivity(intent);
        finish();
    }

    public void cambioActivityPdf(String pdf, String clase, String titulo, String boton) {

        intent = new Intent(this, ShowPdf.class);
        intent.putExtra("pdf", pdf);
        intent.putExtra("titulo", titulo);
        intent.putExtra("clase", clase);
        startActivity(intent);
        finish();
    }

    public void cambioActivity(Class c) {
        intent = new Intent(this, c);
        startActivity(intent);
        finish();
    }


    public static void quitarbordesdialogo(AlertDialog dialog) {
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
    }


    private void PedirPermisonotificaciones() {
        //Comprobación 'Racional'
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.POST_NOTIFICATIONS)) {

            AlertDialog AD;
            AlertDialog.Builder ADBuilder = new AlertDialog.Builder(MainActivity.this);
            ADBuilder.setMessage("Las notificaciones te mantienen al día, activalas para recibir informacion de pagos, rol vacacional y avisos en general.");
            ADBuilder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Solicitamos permisos
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            PERMISO_NOTIFICACIONES);
                }
            });

            AD = ADBuilder.create();
            AD.show();


        } else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    PERMISO_NOTIFICACIONES);
        }


    }


}