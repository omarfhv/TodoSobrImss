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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout botontarjeton, botoncalendario, botonpromociones, botonnoticias, botonrol, botonconsulta, botoncct, botonfaltas,botontabulador, botoncursos, botonpermutas, botonpases, botonpliego,botonsustis,botondias, botonjubilacion, botontiposdecontrato, botonincapacidades,botonseguro,botonrecuperar,botonbono;
    SharedPreferences sharedPref;
    PDFView pdfView;


    //  private AdView mAdView;
    int califica;
    //InterstitialAd mInterstitialAd;
    ColorDrawable dialogColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /*  mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9129010539844350/9620578226");
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);
        mInterstitialAd.setAdListener(new AdListener());*/

        sharedPref = getSharedPreferences("inicio", Context.MODE_PRIVATE);
        califica = sharedPref.getInt("califica", 0);

        if (califica == 8) {
            dialogocalifica();
            califica = 0;
        } else
            califica++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("califica", califica);
        editor.apply();

        botontarjeton = findViewById(R.id.botontarjeton);
        botontarjeton.setOnClickListener(this);

        botoncalendario = findViewById(R.id.botoncalendario);
        botoncalendario.setOnClickListener(this);

        botonpromociones = findViewById(R.id.botonpromociones);
        botonpromociones.setOnClickListener(this);

        botonnoticias = findViewById(R.id.botonnoticias);
        botonnoticias.setOnClickListener(this);

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
                Intent intentae4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.heisenbergtao.manualsupervivencia"));
                startActivity(intentae4);
            }
        });
        Button botonno = vi.findViewById(R.id.botonno);
        botonno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
               // mInterstitialAd.show();



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

            case R.id.botontarjeton:
                Intent intent131t = new Intent(this, SubMenuActivos.class);
                startActivity(intent131t);

                finish();

                break;


            case R.id.botoncalendario:
                Intent intent1312t2 = new Intent(this, Calendario.class);
                startActivity(intent1312t2);

                finish();


                break;

            case R.id.botonpromociones:
                Intent intentdajaa = new Intent(this, Promociones.class);
                startActivity(intentdajaa);
                finish();


                break;



            case R.id.botonnoticias:
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


                break;

            case R.id.botonpermutas:

                Intent intentdajaa12678 = new Intent(this, Permutas.class);
                startActivity(intentdajaa12678);

                finish();
                break;

            case R.id.botonpases:
                Intent intentdajaa12671 = new Intent(this, Pases.class);
                startActivity(intentdajaa12671);

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


                break;


        }



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
