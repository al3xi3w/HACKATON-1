package org.example.init.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.example.init.DTO.ChatRequest;
import org.example.init.DTO.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private LimitService limitService;

    @Autowired
    private RequestLogService requestLogService;

    public ChatResponse handleChat(ChatRequest request) {
        String username = "mia"; // simulado
        int tokensEstimados = 50;

        if (!limitService.checkUserLimits(username, request.getModel())) {
            return new ChatResponse("Límite de uso excedido", 0);
        }

        ChatResponse response;

        switch (request.getModel()) {
            case "openai:gpt-4":
                response = usarOpenAI(request);
                break;
            case "meta:llama2":
                response = new ChatResponse("🧠 [Meta-LLaMA2] Respuesta simulada: " + request.getPrompt(), tokensEstimados);
                break;
            case "deepseek:chat":
                response = new ChatResponse("🔊 [DeepSeek] Respuesta simulada: " + request.getPrompt(), tokensEstimados);
                break;
            default:
                return new ChatResponse("Modelo no soportado: " + request.getModel(), 0);
        }

        // Guardar en base de datos
        if (response.getTokensUsed() > 0) {
            requestLogService.logRequest(
                    username,
                    request.getModel(),
                    request.getPrompt(),
                    response.getText(),
                    response.getTokensUsed()
            );
        }

        return response;
    }

    private ChatResponse usarOpenAI(ChatRequest request) {
        try {
            OkHttpClient client = new OkHttpClient();

            JSONObject json = new JSONObject();
            json.put("model", request.getModel());
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", request.getPrompt()));
            json.put("messages", messages);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json"));

            Request httpRequest = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            Response response = client.newCall(httpRequest).execute();
            String responseBody = response.body().string();

            JSONObject responseJson = new JSONObject(responseBody);
            String content = responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            int tokensUsed = responseJson.has("usage")
                    ? responseJson.getJSONObject("usage").getInt("total_tokens")
                    : 50;

            return new ChatResponse(content.trim(), tokensUsed);

        } catch (Exception e) {
            return new ChatResponse("Error al llamar a OpenAI: " + e.getMessage(), 0);
        }
    }
}
