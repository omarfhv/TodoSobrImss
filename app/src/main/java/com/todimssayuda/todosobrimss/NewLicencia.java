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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.Calendar;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NewLicencia extends AppCompatActivity implements View.OnClickListener {

    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 0;
    final Calendar c = Calendar.getInstance();
    public static String RUTA_IMAGEN = New.CARPETA_RAIZ + "LicenciaIMSS";
    Button add_ellicencia, botonCargarlicencia, fechabtninicio, fechabtnfinal;
    EditText motivolicencia;
    TextView textfechainicio, textfechafinal;
    ImageView imagenlicencia;
    CheckBox sueldo;
    boolean foto = false;
    String path, fechainiciolicencia, fechafinal, rbtnlicencia, mesinicio, diainicio,
            mesfinal, diafinal, anoinicio, anofinal;
    private AdView mAdView;
     InterstitialAd mInterstitialAd;
    //private int diaintinicio, mesintinicio, anoinicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_licencia);
        add_ellicencia = (Button) findViewById(R.id.add_elementlicencia);
        add_ellicencia.setOnClickListener(this);
        motivolicencia = (EditText) findViewById(R.id.motivolicencia);
        textfechainicio = (TextView) findViewById(R.id.textfechainicio);
        textfechafinal = (TextView) findViewById(R.id.textfechafinal);
        sueldo = findViewById(R.id.chbxsueldo);
        imagenlicencia = (ImageView) findViewById(R.id.imagemIdlicencia);
        botonCargarlicencia = (Button) findViewById(R.id.btnCargarImglicencia);
        botonCargarlicencia.setOnClickListener(this);
        fechabtninicio = findViewById(R.id.fechabtniniciolicencia);
        fechabtninicio.setOnClickListener(this);
        fechabtnfinal = findViewById(R.id.fechabtnfinal);
        fechabtnfinal.setOnClickListener(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

         mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.adinter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intentds = new Intent(NewLicencia.this, Licencia.class);
                startActivity(intentds);
                finish();

            }

        });

        if (validaPermisos()) {
            botonCargarlicencia.setEnabled(true);
        } else {
            botonCargarlicencia.setEnabled(false);
        }


    }

    private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                botonCargarlicencia.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(NewLicencia.this);
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
        AlertDialog.Builder dialogo = new AlertDialog.Builder(NewLicencia.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
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
            nombreImagen = 0 + fechainiciolicencia + ".jpg";
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
            texto.setText("Ya existe un registro con la fecha que tienes seleccionada por favor intenta con una fecha diferente");
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
                    imagenlicencia.setImageURI(miPath);
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
                    imagenlicencia.setBackgroundDrawable(d);
                    add_ellicencia.setEnabled(true);
                    botonCargarlicencia.setText("Volver a tomar");
                    if (imagenlicencia != null) {
                        foto = true;
                    }
                    break;
            }


        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fechabtniniciolicencia:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        anoinicio = "" + year;
                        mesinicio = "" + (month + 1);
                        //Toast.makeText(New.this, "" + mes.length(), Toast.LENGTH_LONG).show();
                        if (mesinicio.length() == 1) {
                            mesinicio = 0 + mesinicio;
                        }
                        diainicio = "" + dayOfMonth;
                        if (diainicio.length() == 1)
                            diainicio = 0 + diainicio;

                        fechainiciolicencia = diainicio + mesinicio + year;

                        textfechainicio.setText(diainicio + "/" + mesinicio + "/" + year);
                        textfechafinal.setText("  /  /    ");
                        if (foto) {
                            add_ellicencia.setEnabled(false);
                            botonCargarlicencia.setText("Vuelve a tomar la foto");
                            imagenlicencia.setBackground(getDrawable(R.drawable.camaraimg));
                        }
                        //Toast.makeText(getBaseContext(), "fecha!!" + dayOfMonth + " / " + (month + 1) + " / " + year, Toast.LENGTH_LONG).show();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;

            case R.id.fechabtnfinal:
                if (fechainiciolicencia != null) {
                    //Toast.makeText(this, textfechainicio.getText().toString(), Toast.LENGTH_SHORT).show();
                    DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Toast.makeText(NewLicencia.this, "" + anoinicio + "" + year, Toast.LENGTH_SHORT).show();

                            if (Integer.parseInt(anoinicio) <= year) {
                                if (Integer.parseInt(mesinicio) <= month + 1) {
                                    if (Integer.parseInt(diainicio) <= dayOfMonth || Integer.parseInt(mesinicio) == month) {
                                        mesfinal = "" + (month + 1);
                                        //Toast.makeText(New.this, "" + mes.length(), Toast.LENGTH_LONG).show();
                                        if (mesfinal.length() == 1) {
                                            mesfinal = 0 + mesfinal;
                                        }
                                        diafinal = "" + dayOfMonth;
                                        if (diafinal.length() == 1)
                                            diafinal = 0 + diafinal;

                                        fechafinal = diafinal + mesfinal + year;
                                        textfechafinal.setText(diafinal + "/" + mesfinal + "/" + year);
                                    } else
                                        Toast.makeText(getBaseContext(), "la fecha final no puede ser antes que la inicial (dia)", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(getBaseContext(), "la fecha final no puede ser antes que la inicial (mes)", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getBaseContext(), "la fecha final no puede ser antes que la inicial (año)", Toast.LENGTH_LONG).show();
                        }
                    }, Integer.parseInt(anoinicio), Integer.parseInt(mesinicio) - 1, Integer.parseInt(diainicio));
                    datePickerDialog1.show();
                } else
                    Toast.makeText(getBaseContext(), "Ingresa primero la fecha inicial", Toast.LENGTH_LONG).show();
                break;

            case R.id.add_elementlicencia:

                if (fechainiciolicencia != null) {
                        if (fechafinal != null) {
                            if (foto) {
                                if (sueldo.isChecked()) {
                                    rbtnlicencia = "CON SUELDO";
                                } else
                                    rbtnlicencia = "SIN SUELDO";


                                ContactLicencia c = new ContactLicencia(getBaseContext());
                                c.open();
                                c.createLicencia(fechainiciolicencia, fechafinal, rbtnlicencia, motivolicencia.getText().toString());
                                Toast.makeText(getBaseContext(), "Elemento Agregado!!", Toast.LENGTH_LONG).show();
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                }else {
                                    Intent intentds = new Intent(NewLicencia.this, Licencia.class);
                                    startActivity(intentds);
                                    finish();
                                }

                            } else {
                                Toast.makeText(getBaseContext(), " Ingresa la imagen", Toast.LENGTH_LONG).show();
                            }

                        } else
                            Toast.makeText(getBaseContext(), " Ingresa la fecha final", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getBaseContext(), " Ingresa la fecha de inicio", Toast.LENGTH_LONG).show();

                break;
            case R.id.btnCargarImglicencia:

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
            startActivity(new Intent(getBaseContext(), Licencia.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intentds = new Intent(this, Licencia.class);
            startActivity(intentds);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
