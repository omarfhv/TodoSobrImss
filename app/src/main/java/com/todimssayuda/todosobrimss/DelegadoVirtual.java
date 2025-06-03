package com.todimssayuda.todosobrimss;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DelegadoVirtual extends AppCompatActivity {
    Button botonconsultar;
    TextView txtv;
    EditText edittx;
    private AdView mAdView;
    private boolean continter = false;
    private InterstitialAd mInterstitialAd;
    private static final String OPENAI_API_KEY = ;

    List<String> frasesPropuesta = Arrays.asList(
            "dame una propuesta",
            "quiero una propuesta",
            "consigueme una propuesta",
            "buscame una propuesta"
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegado_virtual);
        this.setTitle("Delegado Digital");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        loadGIF(R.drawable.images);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        loadInter();

        botonconsultar = findViewById(R.id.buttondelegado);
        txtv = findViewById(R.id.textviewdelegado);
        edittx = findViewById(R.id.edittextdelegado);


        botonconsultar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edittx.getText().toString().trim())) {
                runOnUiThread(() -> {
                    txtv.setVisibility(View.VISIBLE);
                    txtv.setText("Cargando respuesta...");
                    loadGIF(R.drawable.botpensando);
                    botonconsultar.setVisibility(View.INVISIBLE);
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(DelegadoVirtual.this);
                    }

                });


                String preguntaNormalizada = edittx.getText().toString().toLowerCase().trim()
                        .replaceAll("[^a-zA-Záéíóúñü0-9\\s]", "");

                boolean esEasterEgg = frasesPropuesta.stream()
                        .anyMatch(preguntaNormalizada::contains);
                if (esEasterEgg) {
                    obtenerRespuestaDesdeOpenAI("","",new OpenAICallback() {
                        @Override
                        public void onSuccess(String respuesta) {
                            // Mostrar en UI (recuerda usar runOnUiThread si estás en una actividad)
                            runOnUiThread(() -> {
                                loadGIF(R.drawable.images);
                                txtv.setText(respuesta);
                                botonconsultar.setVisibility(View.VISIBLE);
                            });
                        }

                        @Override
                        public void onError(String error) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                                loadGIF(R.drawable.images);
                                botonconsultar.setVisibility(View.VISIBLE);
                            });
                        }
                    },true);
                } else {

                    EmbeddingSearch.obtenerEmbeddingAsync(edittx.getText().toString(), new EmbeddingSearch.EmbeddingCallback() {
                        @Override
                        public void onSuccess(List<Double> embeddingPregunta) {
                            Log.d("EmbeddingSearch", "Embedding obtenido con éxito: " + embeddingPregunta);

                            try {
                                List<EmbeddingSearch.EmbeddingData> coincidencias = EmbeddingSearch.buscarMejoresCoincidencias(DelegadoVirtual.this, embeddingPregunta, 6);
                                if (coincidencias != null) {

                                    EmbeddingSearch.EmbeddingData mejorCoincidencia = coincidencias.isEmpty() ? null : coincidencias.get(0);
                                    Log.d("EmbeddingSearch", "Mejor 1 coincidencia encontrada: " + mejorCoincidencia.texto);

                                    EmbeddingSearch.EmbeddingData mejorCoincidencia2 = coincidencias.isEmpty() ? null : coincidencias.get(1);
                                    Log.d("EmbeddingSearch", "Mejor 2 coincidencia encontrada: " + mejorCoincidencia2.texto);
                                    EmbeddingSearch.EmbeddingData mejorCoincidencia3 = coincidencias.isEmpty() ? null : coincidencias.get(2);
                                    Log.d("EmbeddingSearch", "Mejor 3 coincidencia encontrada: " + mejorCoincidencia3.texto);
                                    EmbeddingSearch.EmbeddingData mejorCoincidencia4 = coincidencias.isEmpty() ? null : coincidencias.get(3);
                                    Log.d("EmbeddingSearch", "Mejor 4 coincidencia encontrada: " + mejorCoincidencia4.texto);
                                    EmbeddingSearch.EmbeddingData mejorCoincidencia5 = coincidencias.isEmpty() ? null : coincidencias.get(4);
                                    Log.d("EmbeddingSearch", "Mejor 5 coincidencia encontrada: " + mejorCoincidencia5.texto);
                                    EmbeddingSearch.EmbeddingData mejorCoincidencia6 = coincidencias.isEmpty() ? null : coincidencias.get(5);
                                    Log.d("EmbeddingSearch", "Mejor 6 coincidencia encontrada: " + mejorCoincidencia6.texto);

                                    String textoembedding1 = limpiarTextoEmbedding(mejorCoincidencia.texto);
                                    Log.d("EmbeddingSearch", "contexto 1 limpio: " + textoembedding1);
                                    String textoembedding2 = limpiarTextoEmbedding(mejorCoincidencia2.texto);

                                    String textoembedding3 = limpiarTextoEmbedding(mejorCoincidencia3.texto);
                                    Log.d("EmbeddingSearch", "contexto 2 limpio: " + textoembedding2);

                                    obtenerRespuestaDesdeOpenAI(textoembedding1 + textoembedding2 + textoembedding3, edittx.getText().toString(), new OpenAICallback() {
                                        @Override
                                        public void onSuccess(String respuesta) {
                                            // Mostrar en UI (recuerda usar runOnUiThread si estás en una actividad)
                                            runOnUiThread(() -> {
                                                loadGIF(R.drawable.images);
                                                txtv.setText(respuesta);
                                                botonconsultar.setVisibility(View.VISIBLE);
                                            });
                                        }

                                        @Override
                                        public void onError(String error) {
                                            runOnUiThread(() -> {
                                                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                                                loadGIF(R.drawable.images);
                                                botonconsultar.setVisibility(View.VISIBLE);
                                            });
                                        }
                                    }, false);


                                } else {
                                    Log.e("EmbeddingSearch", "No se encontró una respuesta relevante.");
                                    runOnUiThread(() -> {
                                        loadGIF(R.drawable.images);
                                        botonconsultar.setVisibility(View.VISIBLE);
                                    });
                                }
                            } catch (IOException e) {
                                Log.e("EmbeddingSearch", "Error al buscar la mejor coincidencia", e);
                                runOnUiThread(() -> {
                                    loadGIF(R.drawable.images);
                                    botonconsultar.setVisibility(View.VISIBLE);
                                });
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("EmbeddingSearch", "Error al obtener embedding", e);

                            runOnUiThread(() -> {
                                loadGIF(R.drawable.images);
                                botonconsultar.setVisibility(View.VISIBLE);
                                txtv.setVisibility(View.INVISIBLE);
                                Toast.makeText(DelegadoVirtual.this, "Ocurrio un error, revisa tu conexion de internet e intenta nuevamente.", Toast.LENGTH_LONG).show();

                            });
                        }
                    });
                }


            } else {
                Toast.makeText(this, "Escribe la duda que tengas.", Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadGIF(int resource) {
        ImageView imageView = findViewById(R.id.gifImageView);
        Glide.with(this).asGif() // Indica que es un GIF
                .load(resource) // Puede ser un recurso local o URL
                .into(imageView);
    }

    private void loadInter() {
        AdRequest adRequestinter = new AdRequest.Builder().build();
        InterstitialAd.load(this, getResources().getString(R.string.adinterDD), adRequestinter, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Log.d("Intersticial: ", "se ha cargado ");
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("Intersticial: ", "se ha cerrado el inter ");
                        continter = false;
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        mInterstitialAd = null;
                        Log.d("Intersticial: ", "inter se ha asignado null");
                        loadInter();

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        mInterstitialAd = null;
                        Log.d("Intersticial: ", "inter se ha asignado null");

                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Toast.makeText(DelegadoVirtual.this, "Estoy trabajando en tu solicitud.", Toast.LENGTH_SHORT).show();
                        Log.d("Intersticial: ", "se ha mostrado  ");
                        // Called when ad is shown.

                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.d("Intersticial: ", "no se ha cargado y se asigna null");
                mInterstitialAd = null;
            }
        });


    }

    public interface OpenAICallback {
        void onSuccess(String respuesta);

        void onError(String error);
    }

    public void obtenerRespuestaDesdeOpenAI(String contexto, String preguntaUsuario, OpenAICallback callback, boolean esterEggbool) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)  // ← Aumenta a 2 minutos
                .build();

        String prompt = "";

        if (esterEggbool) {

            prompt = "Eres el Delegado Digital del IMSS. Un usuario te ha hecho una pregunta relacionada con obtener una propuesta (ascenso). \" +\n" +
                    "        \"responde exactamente lo sigueinte: Déjame lo checo y te aviso, deseas realizar otra consulta? " ;
        } else {

            prompt = "Responde la siguiente pregunta utilizando el contexto proporcionado, el cual proviene directamente del Contrato Colectivo de Trabajo del IMSS. " +
                    "Si el contexto no resulta útil o no contiene la información necesaria, ignóralo y responde con base en tu conocimiento general. " +
                    "Si no existe una respuesta precisa, clara y oficial por parte del IMSS, sugiere al usuario reformular su pregunta.\n\n" +
                    "Contexto:\n" + contexto + "\n\n" +
                    "Pregunta del usuario:\n" + preguntaUsuario;
        }

        MediaType mediaType = MediaType.parse("application/json");

        JSONObject json = new JSONObject();
        try {
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "system")
                    .put("content", "Eres delegado digital, un asistente que proporciona informacion del contrato colectivo de trabajo del IMSS"));
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt));

            json.put("model", "gpt-4-turbo");
            json.put("messages", messages);
            json.put("temperature", 0.2);
        } catch (JSONException e) {
            callback.onError("Error al construir el JSON: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY) // ← Cambia por tu clave real
                .addHeader("Content-Type", "application/json")
                .build();

        // Ejecutamos en background con enqueue
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(() -> {
                    loadGIF(R.drawable.images);
                    botonconsultar.setVisibility(View.VISIBLE);
                    txtv.setVisibility(View.INVISIBLE);
                    Toast.makeText(DelegadoVirtual.this, "Ocurrio un error, revisa tu conexion de internet e intenta nuevamente.", Toast.LENGTH_LONG).show();

                });

                callback.onError("Error de red: " + e.getMessage());
                Log.e("EmbeddingSearch", "Fallo en la red: ", e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {

                    callback.onError("Error en la respuesta: " + response.code() + " - " + response.message());
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseBody);
                    String respuesta = jsonObject
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content")
                            .trim();
                    callback.onSuccess(respuesta);
                } catch (JSONException e) {
                    callback.onError("Error al interpretar la respuesta JSON: " + e.getMessage());
                }
            }
        });
    }

    public String limpiarTextoEmbedding(String texto) {
        if (texto == null) return "";

        // Normaliza y verifica si empieza con "clausula"
        if (texto.toLowerCase().startsWith("clausula")) {
            int total = texto.length();
            if (total > 500) {
                // Elimina los primeros y últimos 310 caracteres
                return texto.substring(310, total - 310);
            }
        }

        // Si no empieza con "clausula" o es demasiado corto, se retorna el texto original
        return texto;
    }
}


