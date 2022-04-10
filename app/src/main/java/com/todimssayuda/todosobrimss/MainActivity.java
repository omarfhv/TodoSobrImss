package com.todimssayuda.todosobrimss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botontiempoextra,botonmedianoplazo, botonprestamocarro, botontarjeton, botoncalendario, botonpromociones, botonenterate, botonrol, botonconsulta, botoncct, botonfaltas,botontabulador, botoncursos, botonpermutas, botonpases, botonpliego,botonsustis,botondias, botonjubilacion, botontiposdecontrato, botonincapacidades,botonseguro,botonrecuperar,botonbono, botonpresta,botonsegunda,botoncalcuvacas, botoncajadeahorro, botonaguinaldo, botonhipotecario, botonconceptos;
    SharedPreferences sharedPref;
    PDFView pdfView;


    private AdView mAdView;
    int califica;
    InterstitialAd mInterstitialAd;
    ColorDrawable dialogColor;
    LinearLayout bloqueo;
    Button link ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int  mm = c.get(Calendar.MONTH);

        bloqueo = findViewById(R.id.bloqueo);
        link = findViewById(R.id.botonenlacefb);
       if (yy < 2023 )
           bloqueo.setVisibility(View.INVISIBLE);

        mAdView = findViewById(R.id.adView1);
        AdSize tam = new AdSize(300 , 100);
        //mAdView.setAdSize(tam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


       mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2736592244570345/9645372492");
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener());

        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        califica = sharedPref.getInt("califica", 0);
        boolean binfo = sharedPref.getBoolean("infoinicio", true);
        if (binfo)
        dialogoinfo();

        if (califica == 8) {
            dialogocalifica();
            califica = 0;
        } else
            califica++;




        SharedPreferences.Editor editor = sharedPref.edit();
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

        link.setOnClickListener(this);


    }
    private void dialogoinfo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogocalifica, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        TextView txtconf = vi.findViewById(R.id.txtcalifica);
        txtconf.setText("La información proporcionada por esta aplicación es solo para facilitarle el acceso para el descargo del tarjetón que se obtiene directamente de los sitios http://rh.imss.gob.mx/TarjetonDigital/ y http://rh.imss.gob.mx/tarjetonjubilados/(S(ilsapuvnqy5bppvgfk3nghep))/default.aspx . Así como la facilidad de consultar  varios documentos que se encuentran en la página del https://sntss.org.mx/ y por último obtener de una manera más fácil la información de la caja de ahorros que se proporciona del link https://www.cpasntss.mx/ y con todo esto ayudar a los trabajadores del instituto\n" +
                "Sin embargo, no hacemos ninguna representación o garantía de ningún tipo, expresa o implícita, con respecto a la precisión, adecuación, validez, confiabilidad, disponibilidad o integridad de cualquier información en los Sitios [o nuestra aplicación móvil].");
        Button botonsi = vi.findViewById(R.id.botonsi);
        botonsi.setVisibility(View.INVISIBLE);
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setText("Ok");
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("infoinicio", false);
                editor.apply();
                dialog.dismiss();
                mInterstitialAd.show();
                
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
        ColorDrawable dialogColor = new ColorDrawable(Color.GRAY);
        dialogColor.setAlpha(0);
        dialog.getWindow().setBackgroundDrawable(dialogColor);
        Button botonsi = vi.findViewById(R.id.botonsi);
        botonsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.todimssayuda.todosobrimss"));
                startActivity(intentae4);
            }
        });
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                mInterstitialAd.show();



            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.botonenlacefb:
                Intent intentfb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tarjetondigitalimss/"));
                startActivity(intentfb);


                break;

            case R.id.botontarjeton:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater1 = getLayoutInflater();
                View vi = inflater1.inflate(R.layout.dialogo_tarjeton, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(dialogColor);
                Button botonok = vi.findViewById(R.id.botoncont);
                final RadioButton rbtna = vi.findViewById(R.id.rbtna);
                final RadioButton rbtnj = vi.findViewById(R.id.rbtnj);
                botonok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (rbtna.isChecked()) {
                            Intent intent1 = new Intent(MainActivity.this, SubMenuActivos.class);
                            startActivity(intent1);
                            finish();
                        }
                        if (rbtnj.isChecked()) {
                            Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rh.imss.gob.mx/tarjetonjubilados/(S(lpvgwevvhy0ja2padtk4t12e))/default.aspx"));
                            startActivity(intentae4);
                        }


                    }
                });

                dialog.show();

                break;


            case R.id.botoncalendario:
                Intent intent1312t2 = new Intent(this, Calendario.class);
                startActivity(intent1312t2);

                finish();


                break;

            case R.id.botonconceptos:
                Intent intent1312t211 = new Intent(this, Conceptos.class);
                startActivity(intent1312t211);

                finish();


                break;

            case R.id.botontiempoextra:
                Intent intent1312t2w = new Intent(this, CalcuTiempoExtra.class);
                startActivity(intent1312t2w);

                finish();


                break;

            case R.id.botonmedianoplazo:
                Intent intent1312t21 = new Intent(this, PrestamoMedianoPlazo.class);
                startActivity(intent1312t21);

                finish();


                break;

            case R.id.botoncarro:
                Intent intent1312t22 = new Intent(this, PrestamoCarro.class);
                startActivity(intent1312t22);

                finish();


                break;

            case R.id.botonpromociones:
                Intent intentdajaa = new Intent(this, Promociones.class);
                startActivity(intentdajaa);
                finish();


                break;



            case R.id.botonenterate:
                Intent intentd0ajaa = new Intent(this, Noticias.class);
                startActivity(intentd0ajaa);
                finish();

                break;

            case R.id.botonrol:
                Intent intentdajaa1 = new Intent(this, RolVacacional.class);
                startActivity(intentdajaa1);
                finish();

                break;

            case R.id.botonconsulta:
                Toast toast3 = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.lytLayout));
                TextView txtMsg = layout.findViewById(R.id.txtMensaje);
                txtMsg.setText("Esto puede tardar unos segundos, favor de esperar " +
                        "GRACIAS");

                toast3.setDuration(Toast.LENGTH_LONG);
                toast3.setView(layout);
                toast3.show();

                Intent intent11111 = new Intent(this, PDFViewer.class);
                startActivity(intent11111);
                finish();
                break;


            case R.id.botoncont:

                Intent intentdajaa12 = new Intent(this, ContratoColectivoTrabajadores.class);
                startActivity(intentdajaa12);
                finish();

                break;

            case R.id.botonfalta:

                Intent intentdajaa126 = new Intent(this, Faltas.class);
                startActivity(intentdajaa126);

                finish();


                break;

            case R.id.botontabula:
                Intent intentdajaa1267 = new Intent(this, Tabulador.class);
                startActivity(intentdajaa1267);

                finish();


                break;

            case R.id.botoncursos:
               /* Toast toast4 = new Toast(getApplicationContext());
                LayoutInflater inflater4 = getLayoutInflater();
                View layout4 = inflater4.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.lytLayout));

                TextView txtMsg4 = layout4.findViewById(R.id.txtMensaje);
                txtMsg4.setText("Proximamente estaremos subiendo las becas para el proximo año " +
                        "GRACIAS");

                toast4.setDuration(Toast.LENGTH_LONG);
                toast4.setView(layout4);
                toast4.show();*/
                Intent intentdajaa12671 = new Intent(this, CursosIm.class);
                startActivity(intentdajaa12671);

                finish();
                break;

            case R.id.botonpermutas:

                Intent intentdajaa12678 = new Intent(this, Permutas.class);
                startActivity(intentdajaa12678);

                finish();
                break;

            case R.id.botonpases:
                Intent intentdajaa126712 = new Intent(this, Pases.class);
                startActivity(intentdajaa126712);

                finish();

                break;

            case R.id.botonpliego:
                Intent intentdajaa126714 = new Intent(this, PliegoTesta.class);
                startActivity(intentdajaa126714);

                finish();


                break;

            case R.id.botontxt:

                Intent intentdajaa1267141 = new Intent(this, Sustis.class);
                startActivity(intentdajaa1267141);

                finish();

                break;

            case R.id.botondias:

                Intent intent = new Intent(this, Festivos.class);
                startActivity(intent);

                finish();

                break;

            case R.id.botonjubilacion:

                Intent intent1 = new Intent(this, Jubilacion.class);
                startActivity(intent1);

                finish();

                break;

            case R.id.botoncontratos:

                Intent intent13 = new Intent(this, TiposDeContrato.class);
                startActivity(intent13);

                finish();

                break;

            case R.id.botonincapa:
                Intent intent131 = new Intent(this, Incapacidades.class);
                startActivity(intent131);

                finish();

                break;

            case R.id.botonseguro:
                Intent intent1312 = new Intent(this, SeguroFacul.class);
                startActivity(intent1312);

                finish();


                break;

            case R.id.botonrecupera:
                Intent intent1312t = new Intent(this, RecuperarContrasena.class);
                startActivity(intent1312t);

                finish();

                break;

            case R.id.botonbono:
                Intent intent1312to = new Intent(this, Declaracion.class);
                startActivity(intent1312to);

                finish();

                break;


            case R.id.botonprestamos:
                Intent intent1312to1 = new Intent(this, Prestaciones.class);
                startActivity(intent1312to1);

                finish();

                break;

            case R.id.botonsegundadejulio:
                Intent intent1312to1f = new Intent(this, SegundaDeJulio.class);
                startActivity(intent1312to1f);

                finish();

                break;

            case R.id.botoncalcuvaca:
                Intent intent1312to1fa = new Intent(this, MarcasDeInclusion.class);
                startActivity(intent1312to1fa);

                finish();

                break;

            case R.id.botoncajadeahorro:
                Intent intentdajaaa = new Intent(this, CajaAhorro.class);
                startActivity(intentdajaaa);
                finish();


                break;

            case R.id.botonaguinaldo:
                Intent intentdajaaa1 = new Intent(this, Aguinaldo.class);
                startActivity(intentdajaaa1);
                finish();


                break;

            case R.id.botonhipotecario:
                Intent intentd0ajaaw = new Intent(this, PrestamoHipotecario.class);
                startActivity(intentd0ajaaw);
                finish();

                break;



        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinfo, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.botoninfo:
                    dialogoinfo();
                break;

          /*   case R.id.botonagenda:
                Intent intentdajaaa1 = new Intent(this, MenuPrincipalAgenda.class);
                startActivity(intentdajaaa1);
                finish();
                break; */

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
            dialog.getWindow().setBackgroundDrawable(dialogColor);

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
}
