package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.ProcessedDto;
import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.services.ProcessedService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/adoption_processed/")
@CrossOrigin(origins = {"*"})
public class ProcessedController {


    private final ProcessedService processedService;

    @Autowired
    public ProcessedController(ProcessedService processedService) {
        this.processedService = processedService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Processed>>> getAll() {
        return new ResponseEntity<>(
                this.processedService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Processed>> getOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.processedService.getOne(id),
                HttpStatus.OK
        );
    }

    ///post
    @PostMapping("/process")
    public ResponseEntity<CustomResponse<Processed>> processAdoption(
            @RequestBody ProcessedDto processedDto) {
        CustomResponse<Processed> response = processedService.processAdoption(processedDto);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Processed>> update(
            @PathVariable UUID id,
            @RequestBody ProcessedDto dto,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.processedService.update(dto.getProcessed()),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/updateApprovalStatus")
    public ResponseEntity<CustomResponse<Processed>> updateApprovalStatus(
            @PathVariable UUID id,
            @RequestParam ApprovalStatus status) {
        CustomResponse<Processed> response = processedService.updateApprovalStatus(id, status);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.processedService.deleteById(id),
                HttpStatus.OK
        );
    }
}
