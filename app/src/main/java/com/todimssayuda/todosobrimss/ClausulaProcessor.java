package com.todimssayuda.todosobrimss;


import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;

public class ClausulaProcessor {
    public static void procesarClausulas(Context context) {
        String inputFile = "contratocolectivotemp.jsonl"; // Archivo JSONL en assets
        String outputFile = context.getFilesDir() + "/preguntas_respuestas.json"; // Archivo de salida en almacenamiento interno

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode listaPreguntasRespuestas = objectMapper.createArrayNode();

        try (InputStream inputStream = context.getAssets().open(inputFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                JsonNode jsonNode = objectMapper.readTree(line);
                JsonNode messages = jsonNode.get("messages");

                if (messages != null && messages.isArray() && messages.size() >= 3) {
                    String pregunta = messages.get(1).get("content").asText();
                    String respuesta = messages.get(2).get("content").asText();

                    ObjectNode preguntaRespuestaNode = objectMapper.createObjectNode();
                    preguntaRespuestaNode.put("pregunta", pregunta);
                    preguntaRespuestaNode.put("respuesta", respuesta);

                    listaPreguntasRespuestas.add(preguntaRespuestaNode);
                }
            }

            // Guardar archivo JSON estructurado en almacenamiento interno
            objectMapper.writeValue(new File(outputFile), listaPreguntasRespuestas);
            Toast.makeText(context, "Archivo JSON generado con éxito", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al procesar el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}