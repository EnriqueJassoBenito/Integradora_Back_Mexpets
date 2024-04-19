package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.constraints.NotNull;
import mx.edu.utez.mexprotec.models.logs.Logs;
import mx.edu.utez.mexprotec.services.LogsService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/logs/")
@CrossOrigin(origins = "http://localhost:5173")
public class LogsController {
    private final LogsService service;

    public LogsController(LogsService services) {
        this.service = services;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Logs>>> getAll() {
        return new ResponseEntity<>(this.service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/paginado/{page}/{size}")
    public ResponseEntity<CustomResponse<Page<Logs>>> getAllPaginado(@PathVariable("page") @NotNull Integer page, @PathVariable("size") @NotNull Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.service.getAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public CustomResponse<Logs> save(
            @RequestBody Logs logs) throws SQLException {
        return service.save(logs);
    }
}
