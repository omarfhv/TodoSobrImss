package com.todimssayuda.todosobrimss;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;


import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.Calendar;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NewSusti extends AppCompatActivity implements View.OnClickListener {


    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    final Calendar c = Calendar.getInstance();
    public static String RUTA_IMAGEN = New.CARPETA_RAIZ + "SustisIMSS";
    Button add_elsusutis, botonCargarsustis, fechabtnsustis;
    EditText nombresustis, phonesustis;
    CheckBox pagado;
    TextView textfechasustis;
    ImageView imagensustis;
    boolean foto = false;
    String path, fechasustis, radiobtnsustis, messustis, diasustis;
    private int diaintsustis, mesintsustis, anosustis;
    String chbx = "0";
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_susti);
        add_elsusutis = (Button) findViewById(R.id.agregarsusti);
        add_elsusutis.setOnClickListener(this);
        nombresustis = (EditText) findViewById(R.id.nombresustis);
        phonesustis = (EditText) findViewById(R.id.telefonosustis);
        textfechasustis = (TextView) findViewById(R.id.textfechasustis);
        pagado = findViewById(R.id.chbxpago);
        pagado.setOnClickListener(this);
        pagado.setChecked(false);
        imagensustis = (ImageView) findViewById(R.id.imagemIdsustis);
        botonCargarsustis = (Button) findViewById(R.id.btnCargarImgsustis);
        botonCargarsustis.setOnClickListener(this);
        fechabtnsustis = findViewById(R.id.fechabtnsustis);
        fechabtnsustis.setOnClickListener(this);
        diaintsustis = c.get(Calendar.DAY_OF_MONTH);
        mesintsustis = c.get(Calendar.MONTH);
        anosustis = c.get(Calendar.YEAR);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        if (validaPermisos()) {
            botonCargarsustis.setEnabled(true);
        } else {
            botonCargarsustis.setEnabled(false);
        }


    }

    private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) ||
                (shouldShowRequestPermissionRationale(CALL_PHONE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, CALL_PHONE}, 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                botonCargarsustis.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(NewSusti.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });


        alertOpciones.show();
    }


    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, CALL_PHONE}, 100);
            }
        });
        dialogo.show();
    }


    private void tomarFotografia() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();
        String nombreImagen = "";
        if (isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }
        if (isCreada == true) {
            if (pagado.isChecked()) {
                radiobtnsustis = "PAGADA";
                chbx = "0";
            }
            if (!pagado.isChecked()) {
                radiobtnsustis = "NO PAGADA";
                chbx = "1";
            }
            nombreImagen = 0 + fechasustis + chbx + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() +
                File.separator + RUTA_IMAGEN + File.separator + nombreImagen;

        File imagen = new File(path);
        if (imagen.exists()) {
            final AlertDialog.Builder constructor = new AlertDialog.Builder(this);
            View vista = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
            constructor.setView(vista);
            final AlertDialog dialogo = constructor.create();
            Button botonok = vista.findViewById(R.id.botonok);
            TextView texto = vista.findViewById(R.id.txt);
            texto.setText("Ya existe un registro de sustitucion con la fecha que tienes seleccionada por favor intenta con una fecha diferente");
            botonok.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dialogo.cancel();
                                           }
                                       }
            );
            dialogo.show();
            //Toast.makeText(this, "Ya existe un registro con estos datos", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ////
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String authorities = getApplicationContext().getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
            }
            startActivityForResult(intent, COD_FOTO);
            ////
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imagensustis.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "Path: " + path);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    imagensustis.setBackgroundDrawable(d);
                    botonCargarsustis.setText("volver a tomar");
                    if (imagensustis != null) {
                        foto = true;
                    }
                    break;
            }


        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chbxpago:
                if (pagado.isChecked()) {
                    pagado.setText("Pagada");
                }
                if (!pagado.isChecked()) {
                    pagado.setText("No pagada");

                }
                break;
            case R.id.fechabtnsustis:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        messustis = "" + (month + 1);
                        //Toast.makeText(New.this, "" + mes.length(), Toast.LENGTH_LONG).show();
                        if (messustis.length() == 1) {
                            messustis = 0 + messustis;
                        }
                        diasustis = "" + dayOfMonth;
                        if (diasustis.length() == 1)
                            diasustis = 0 + diasustis;

                        fechasustis = diasustis + messustis + year;
                        textfechasustis.setText(diasustis + "/" + messustis + "/" + year);
                        if (foto) {
                            add_elsusutis.setEnabled(false);
                            botonCargarsustis.setText("Vuelve a tomar la foto");
                            imagensustis.setBackground(getDrawable(R.drawable.camaraimg));
                        }
                        //Toast.makeText(getBaseContext(), "fecha!!" + dayOfMonth + " / " + (month + 1) + " / " + year, Toast.LENGTH_LONG).show();
                    }
                }, anosustis, mesintsustis, diaintsustis);
                datePickerDialog.show();
                break;

            case R.id.agregarsusti:

                if (fechasustis != null) {
                    if (foto) {
                        if (phonesustis.getText().toString().length() == 10) {

                            if (pagado.isChecked()) {
                                radiobtnsustis = "PAGADA";
                            }
                            if (!pagado.isChecked()) {
                                radiobtnsustis = "NO PAGADA";
                            }

                            ContactSustis c = new ContactSustis(getBaseContext());
                            c.open();
                            c.createSustis(fechasustis, nombresustis.getText().toString(), phonesustis.getText().toString(), radiobtnsustis, chbx);
                            Toast.makeText(getBaseContext(), "Elemento Agregado!!", Toast.LENGTH_LONG).show();

                            Intent intentds = new Intent(this, SustisAgenda.class);
                            startActivity(intentds);
                            finish();


                        } else {
                            Toast.makeText(getBaseContext(), "Ingresa el numero a 10 digitos ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), " Agrega la imagen", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getBaseContext(), " Agrega la fecha", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.btnCargarImgsustis:
                tomarFotografia();
                /*
                final CharSequence[] opciones = {"Tomar Foto", "Cancelar"};
                final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(New.this);
                alertOpciones.setTitle("Seleccione una Opción");
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (opciones[i].equals("Tomar Foto")) {
                            tomarFotografia();
                        } else {
                         /*  if (opciones[i].equals("Cargar Imagen")) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/");
                                startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), COD_SELECCIONA);
                            } else {
                                */
                            /*dialogInterface.dismiss();
                        }
                    }
                });




                alertOpciones.show();
                add_el.setEnabled(true);
                */
                break;


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivity(new Intent(getBaseContext(), SustisAgenda.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentds = new Intent(this, SustisAgenda.class);
            startActivity(intentds);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
