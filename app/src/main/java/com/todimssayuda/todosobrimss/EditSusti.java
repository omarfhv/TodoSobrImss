package com.todimssayuda.todosobrimss;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class EditSusti extends AppCompatActivity implements View.OnClickListener {


    private final String RUTA_IMAGEN = New.CARPETA_RAIZ + "SustisIMSS";
    Button upd_elsustis, del_btnsustis, botonwhats, botoncall;
    EditText nombresustis, phonesustis;
    CheckBox pagado;
    TextView textfechasustis;
    ImageView imagensustis;
    String idimagensustis, radiobtnsustis, name, Fechasustis;
    long idsustis;
    Intent i;
    static Integer densidadpantalla;
    private AdView mAdView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_susti);
        upd_elsustis = findViewById(R.id.upd_element);
        upd_elsustis.setOnClickListener(this);
        del_btnsustis = findViewById(R.id.del_btn);
        del_btnsustis.setOnClickListener(this);
        botonwhats = findViewById(R.id.botonwhats);
        botonwhats.setOnClickListener(this);
        botoncall = findViewById(R.id.botoncall);
        botoncall.setOnClickListener(this);
        textfechasustis = findViewById(R.id.textfechasustis);
        nombresustis = findViewById(R.id.nombresustis);
        phonesustis = findViewById(R.id.telefonosustis);
        imagensustis = findViewById(R.id.imagenId);
        pagado = findViewById(R.id.chbxpago);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adinter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();

            }

        });

        i = getIntent();
        idsustis = i.getLongExtra("idsusti", 0);
        Fechasustis = i.getStringExtra("fechasusti");
        idimagensustis = i.getStringExtra("idimagen");
        //name.setText(Fecha);
        textfechasustis.setText(Fechasustis.charAt(0) + "" + Fechasustis.charAt(1) + "/" + Fechasustis.charAt(2) + "" + Fechasustis.charAt(3) + "/" +
                Fechasustis.charAt(4) + "" + Fechasustis.charAt(5) + Fechasustis.charAt(6) + Fechasustis.charAt(7));

        switch (i.getStringExtra("rbtnsusti")) {
            case "PAGADA":
                pagado.setChecked(true);
                break;
            case "NO PAGADA":
                pagado.setChecked(false);
                break;
        }
        nombresustis.setText(i.getStringExtra("namesusti"));
        phonesustis.setText(i.getStringExtra("phonesusti"));


        String paths = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + 0 + Fechasustis + i.getStringExtra("chbx") + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        imagensustis.setImageBitmap(bitmap);


        //recuso boton a atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), Sustis.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), Sustis.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void onClickWhatsApp(Context context, ImageView iv, String text) {

        Bitmap bm = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        PackageManager pm = context.getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("*/*");
            String fileName = "" + "12" + "FMQ.jpg";
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(ExternalStorageDirectory + File.separator + fileName);
            FileOutputStream fileOutputStream = null;
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes.toByteArray());
            } catch (IOException e) {

            } finally {
                if (fileOutputStream != null) {
                    Uri bmpUr = Uri.parse(file.getPath());
                    waIntent.putExtra(Intent.EXTRA_TEXT, text + Html.fromHtml("<br />") +
                            Html.fromHtml("<br />") +
                            "https://play.google.com/store/apps/details?id=com.games.user.agendaimss");
                    waIntent.putExtra(
                            Intent.EXTRA_STREAM,
                            bmpUr);
                    //context.startActivity(Intent.createChooser(waIntent,
                    //        "AgendaIMSS "));
                }
            }
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");

            Toast.makeText(context, "Selecciona el contacto", Toast.LENGTH_LONG).show();
            context.startActivity(Intent.createChooser(waIntent, "Compartir con"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upd_element:

                if (textfechasustis.getText().toString().length() > 0) {

                    if (pagado.isChecked()) {
                        radiobtnsustis = "PAGADA";
                    }
                    if (!pagado.isChecked()) {
                        radiobtnsustis = "NO PAGADA";
                    }

                    ContactSustis c = new ContactSustis(getBaseContext());
                    c.open();
                    c.updateSustis(idsustis, Fechasustis, nombresustis.getText().toString(), phonesustis.getText().toString(), radiobtnsustis, i.getStringExtra("chbx"));
                    nombresustis.setText("");
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();
                    //se agrega metodo para pasar a nueva actividad
                    Intent intentds = new Intent(EditSusti.this, Sustis.class);
                    startActivity(intentds);
                    //se cierra actividdad actual
                    finish();


                } else {
                    Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();

                }


                break;
            case R.id.del_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditSusti.this);

                builder.setTitle("  Confirmar  ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ContactSustis c = new ContactSustis(getBaseContext());
                        c.open();
                        c.deleteContactSustis(idsustis);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();
                        Intent intentds = new Intent(EditSusti.this, Sustis.class);
                        startActivity(intentds);
                        finish();

                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.botonwhats:
                onClickWhatsApp(this, imagensustis, "Hola Compañer@, solo para recordarte que aun no me haces el pago de la Sustitucion del día " + textfechasustis.getText().toString() + " antemano muchas gracias, Saludos");
                break;
            case R.id.botoncall:
                phonesustis.getText().toString();
                if (!TextUtils.isEmpty(phonesustis.getText().toString())) {
                    if (phonesustis.getText().toString().length() == 10) {
                        String dial = "tel:" + phonesustis.getText().toString();
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    } else {
                        Toast.makeText(this, "Ingresa el numero a 10 digitos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Ingresa el numero", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}



