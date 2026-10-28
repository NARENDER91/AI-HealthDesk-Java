package com.narender.healthdesk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.util.Map;

@Service
public class HealthFaqService {
    private final RestClient restClient = RestClient.create();

    @Value("${app.ai.api-key:}")
    private String apiKey;

    @Value("${app.ai.model:gemini-2.5-flash}")
    private String model;

    public String answer(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Please enter a question.");
        }
        if (apiKey == null || apiKey.isBlank()) {
            return "AI integration is not configured yet. Add GEMINI_API_KEY to your environment to enable live AI responses. For this demo, please contact a qualified healthcare professional for personal medical questions.";
        }

        String systemPrompt = """
            You are HealthDesk, a cautious healthcare information assistant.
            Provide general health education and administrative information in plain language.
            Never diagnose, prescribe medication, recommend a specific treatment, or claim certainty.
            For personal symptoms or treatment questions, advise the user to consult a qualified clinician.
            If the user describes possible life-threatening symptoms, tell them to contact local emergency services immediately.
            Do not ask for names, addresses, medical record numbers, or other identifying information.
            Keep answers concise. State when information is uncertain.
            This is not a substitute for professional medical advice.
            """;

        Map<String, Object> body = Map.of(
            "systemInstruction", Map.of("parts", List.of(Map.of("text", systemPrompt))),
            "contents", List.of(Map.of("parts", List.of(Map.of("text", question)))),
            "generationConfig", Map.of("temperature", 0.2, "maxOutputTokens", 350)
        );

        try {
            Map response = restClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .body(Map.class);

            if (response == null) return fallback();
            List candidates = (List) response.get("candidates");
            if (candidates == null || candidates.isEmpty()) return fallback();
            Map candidate = (Map) candidates.get(0);
            Map content = (Map) candidate.get("content");
            List parts = content == null ? null : (List) content.get("parts");
            if (parts == null || parts.isEmpty()) return fallback();
            Object text = ((Map) parts.get(0)).get("text");
            return text == null ? fallback() : text.toString();
        } catch (Exception ex) {
            return "I couldn't retrieve an AI response right now. Please try again later or contact a qualified healthcare professional for personal medical advice.";
        }
    }

    private String fallback() {
        return "I couldn't produce a reliable response. Please contact a qualified healthcare professional for personal medical advice. For an emergency, contact local emergency services.";
    }
}
