package com.todimssayuda.todosobrimss;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class EmbeddingSearch {
    private static final String OPENAI_API_KEY = ;
    private static final String OPENAI_EMBEDDING_URL = "https://api.openai.com/v1/embeddings";
    private static final String JSON_FILE_PATH = "embeddings.json"; // Sin "assets/"

    static class EmbeddingData {
        public String texto;
        public List<Double> embedding;
    }




    public static void obtenerEmbeddingAsync(String texto, EmbeddingCallback callback) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(Map.of("input", texto, "model", "text-embedding-3-small"));
        } catch (IOException e) {
            callback.onError(e);
            return;
        }

        RequestBody body = RequestBody.create(MediaType.get("application/json"), jsonBody);
        Request request = new Request.Builder()
                .url(OPENAI_EMBEDDING_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> responseMap = objectMapper.readValue(response.body().string(), new TypeReference<Map<String, Object>>() {});
                    List<Double> embedding = (List<Double>) ((Map<String, Object>) ((List<?>) responseMap.get("data")).get(0)).get("embedding");
                    callback.onSuccess(embedding);
                } else {
                    callback.onError(new IOException("Error en la solicitud: " + response.message()));
                }
            }
        });
    }




    public static List<EmbeddingData> buscarMejoresCoincidencias(Context context, List<Double> embeddingPregunta, int numResultados) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(JSON_FILE_PATH);
        if (inputStream == null) {
            Log.e("EmbeddingSearchClass", "No se pudo encontrar el archivo de embeddings en assets");
            throw new IOException("No se pudo encontrar el archivo de embeddings en assets");
        }
        List<EmbeddingData> embeddings = objectMapper.readValue(inputStream, new TypeReference<List<EmbeddingData>>() {} );

        // Ordenamos por similitud del coseno de mayor a menor
        List<EmbeddingData> mejoresCoincidencias = embeddings.stream()
                .sorted((a, b) -> Double.compare(calcularSimilitudCoseno(embeddingPregunta, b.embedding), calcularSimilitudCoseno(embeddingPregunta, a.embedding)))
                .limit(numResultados)
                .toList();

        return mejoresCoincidencias;
    }


    private static double calcularSimilitudCoseno(List<Double> v1, List<Double> v2) {
        double dotProduct = 0.0, normV1 = 0.0, normV2 = 0.0;
        for (int i = 0; i < v1.size(); i++) {
            dotProduct += v1.get(i) * v2.get(i);
            normV1 += Math.pow(v1.get(i), 2);
            normV2 += Math.pow(v2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normV1) * Math.sqrt(normV2));
    }

    public interface EmbeddingCallback {
        void onSuccess(List<Double> embedding);
        void onError(Exception e);
    }

}
