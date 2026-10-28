package com.narender.healthdesk.controller;

import com.narender.healthdesk.model.Appointment;
import com.narender.healthdesk.repository.AppointmentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class AppointmentApiController {
    private final AppointmentRepository repository;
    private static final Set<String> STATUSES = Set.of("Requested", "Contacted", "Completed", "Cancelled");

    public AppointmentApiController(AppointmentRepository repository) { this.repository = repository; }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@Valid @RequestBody AppointmentRequest request) {
        if (request.preferredDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose today or a future date.");
        Appointment a = new Appointment();
        a.setPatientName(request.patientName().trim());
        a.setContactEmail(request.contactEmail().trim());
        a.setPreferredDate(request.preferredDate());
        a.setRequestType(request.requestType().trim());
        a.setStatus("Requested");
        a.setCreatedAt(LocalDateTime.now());
        Appointment saved = repository.save(a);
        return Map.of("id", saved.getId(), "message", "Appointment request saved");
    }

    @GetMapping("/api/appointments")
    public List<Appointment> list() {
        return repository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }

    @PostMapping("/api/appointments/{id}/status")
    public Map<String, String> update(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.getOrDefault("status", "");
        if (!STATUSES.contains(status)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status.");
        Appointment a = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found."));
        a.setStatus(status);
        repository.save(a);
        return Map.of("message", "Status updated", "status", status);
    }

    public record AppointmentRequest(
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max=100) String patientName,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Email @jakarta.validation.constraints.Size(max=200) String contactEmail,
        @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.FutureOrPresent LocalDate preferredDate,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max=80) String requestType
    ) {}
}
