package com.narender.healthdesk.controller;

import com.narender.healthdesk.service.HealthFaqService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestController
public class HealthDeskController {
    private final HealthFaqService faqService;
    public HealthDeskController(HealthFaqService faqService) { this.faqService = faqService; }

    @PostMapping("/api/faq")
    public Map<String, String> askFaq(@RequestBody Map<String, String> body) {
        String question = body.getOrDefault("question", "").trim();
        if (question.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a question.");
        if (question.length() > 1000) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question is too long.");
        return Map.of("answer", faqService.answer(question));
    }
}
