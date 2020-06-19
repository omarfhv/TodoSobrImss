package com.todimssayuda.todosobrimss;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.Manifest.permission.CALL_PHONE;

public class RecuperaSoporte extends AppCompatActivity implements View.OnClickListener {


    private static final int SOLICITUD_PERMISO_CALL_PHONE = 1;
    private Intent intentllamada;
    LinearLayout botoncorreo, botonllamada;
    ColorDrawable dialogColor;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //botonatras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setContentView(R.layout.activity_recupera_soporte);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



// SI NOS CONCEDE EL PERMISO Y LANZA LA LLAMADA


        botonllamada = findViewById(R.id.botonllamada);
        botonllamada.setOnClickListener(this);

        botoncorreo = findViewById(R.id.botoncorreo);
        botoncorreo.setOnClickListener(this);


        validaPermisos();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.botoncorreo:

                //Instanciamos un Intent del tipo ACTION_SEND
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                //Definimos la tipologia de datos del contenido dle Email en este caso text/html
                emailIntent.setType("text/html");
                // Indicamos con un Array de tipo String las direcciones de correo a las cuales
                //queremos enviar el texto
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"soporte.tarjeton@imss.gob.mx"});
                // Definimos un titulo para el Email
                emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "IMSS");
                // Definimos un Asunto para el Email
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recuperar Contraseña Tarjeton Digital");
                // Obtenemos la referencia al texto y lo pasamos al Email Intent
                try {
                    //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
                    startActivity(Intent.createChooser(emailIntent, "Enviar E-mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(RecuperaSoporte.this, "No hay ningun cliente de correo instalado.", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

            case R.id.botonllamada:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:5553331100"));
                startActivity(intent);

                break;
        }


    }


    private void validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if ((checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED)) {
            return;
        }

        if ((shouldShowRequestPermissionRationale(CALL_PHONE))) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(RecuperaSoporte.this);
            dialogo.setTitle("Permisos Desactivados");
            dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{ CALL_PHONE}, 100);
                }
            });
            dialogo.show();
        } else {
            requestPermissions(new String[]{ CALL_PHONE}, 100);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), RecuperarContrasena.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}