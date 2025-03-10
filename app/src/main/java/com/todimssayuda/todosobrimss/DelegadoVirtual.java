package com.todimssayuda.todosobrimss;

import android.content.Intent;
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

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DelegadoVirtual extends AppCompatActivity {
    Button botonconsultar;
    TextView txtv;
    EditText edittx;
    private AdView mAdView;
    private boolean continter = false;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegado_virtual);
        this.setTitle("Delegado Digital");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        loadInter();


        loadGIF(R.drawable.images);
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        botonconsultar = findViewById(R.id.buttondelegado);
        txtv = findViewById(R.id.textviewdelegado);
        edittx = findViewById(R.id.edittextdelegado);

        botonconsultar.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edittx.getText().toString().trim())) {
                OpenAIService openAIService = new OpenAIService();
                runOnUiThread(() -> {
                    txtv.setVisibility(View.VISIBLE);
                    txtv.setText("Cargando respuesta...");
                    loadGIF(R.drawable.botpensando);
                });

                openAIService.queryOpenAI(edittx.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("OpenAIQueryError", "Error al realizar la consulta revisa tu conexion a internet " + e.getMessage(), e); // Log para el error completo
                        runOnUiThread(() -> {
                            txtv.setText("No se ha podido realizar tu consulta, revisa tu conexion a internet e intentalo nuevamente");
                            loadGIF(R.drawable.images);
                        });

                    }


                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful() && response.body() != null) {


                            String responseData = response.body().string();
                            // Parsear la respuesta de OpenAI y mostrarla
                            try {
                                JSONObject jsonResponse = new JSONObject(responseData);
                                String answer = jsonResponse.getJSONArray("choices")
                                        .getJSONObject(0)
                                        .getJSONObject("message")
                                        .getString("content");

                                runOnUiThread(() -> {
                                    if (continter) {
                                        if (mInterstitialAd != null) {
                                            mInterstitialAd.show(DelegadoVirtual.this);
                                            Log.d("Intersticial: ", "se ha mostrado");

                                        } else {
                                            Log.d("Intersticial: ", " intersticial es null ");
                                        }
                                    } else {
                                        Log.d("Intersticial: ", " continter es falso");
                                    }
                                    txtv.setVisibility(View.VISIBLE);
                                    loadGIF(R.drawable.images);
                                    txtv.setText(answer.trim());
                                    continter = true;
                                });

                            } catch (Exception e) {
                                runOnUiThread(() -> txtv.setText("Ha ocurrido un error intentalo de nuevo"));
                            }
                        } else {
                            String errorResponse = response.body() != null ? response.body().string() : "Cuerpo vacío";
                            runOnUiThread(() -> txtv.setText("Ha ocurrido un error intentalo de nuevo "));
                        }
                    }
                });
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
        Glide.with(this)
                .asGif() // Indica que es un GIF
                .load(resource) // Puede ser un recurso local o URL
                .into(imageView);
    }

    private void loadInter() {
        AdRequest adRequestinter = new AdRequest.Builder().build();
        InterstitialAd.load(this, String.valueOf(R.string.adinter), adRequestinter,
                new InterstitialAdLoadCallback() {
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
}
