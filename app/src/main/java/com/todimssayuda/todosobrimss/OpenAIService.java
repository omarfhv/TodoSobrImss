package com.todimssayuda.todosobrimss;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class OpenAIService {
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private OkHttpClient client = new OkHttpClient();
    public void queryOpenAI(String prompt, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("model", "ft:gpt-4o-mini-2024-07-18:personal:delegadovirtual:B2Vrrx6q");
            json.put("temperature", 0);
            json.put("max_tokens", 500);


            // Crear el array de mensajes
            JSONArray messages = new JSONArray();

            // Agregar mensaje del sistema
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "Delegado Digital es un asistente que proporciona información oficial del contrato colectivo del IMSS y demas documentos del instituto. Responde siempre de manera precisa");
            messages.put(systemMessage);

            // Agregar mensaje del usuario con el prompt recibido
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.put(userMessage);

            // Agregar el array de mensajes al JSON de la solicitud
            json.put("messages", messages);

            // Crear el cuerpo de la solicitud HTTP
            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(OPENAI_API_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();

            // Ejecutar la solicitud
            client.newCall(request).enqueue(callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}