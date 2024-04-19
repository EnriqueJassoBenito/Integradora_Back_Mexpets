package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AdoptionDto;

import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.services.AdoptionService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/adoption/")
@CrossOrigin(origins = {"*"})
public class AdoptionController {

    private final AdoptionService adoptionService;

    @Autowired
    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Adoption>>> getAll() {
        return new ResponseEntity<>(
                this.adoptionService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Adoption>> getOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.adoptionService.getOne(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomResponse<List<Adoption>>> getAdoptionsByUser(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(
                adoptionService.getAdoptionsByUser(userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<CustomResponse<List<Adoption>>> getPendingAdoptions() {
        return new ResponseEntity<>(
                adoptionService.getPendingAdoptions(),
                HttpStatus.OK
        );
    }

    @GetMapping("/managed")
    public ResponseEntity<CustomResponse<List<Adoption>>> getApprovedAdoptions() {
        return new ResponseEntity<>(
                adoptionService.getApprovedAdoptions(),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Adoption>> insert(
            @ModelAttribute AdoptionDto dto,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adoptionService.insert(dto.toAdoption(), imageFiles),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Adoption>> update( @PathVariable UUID id,
                                                            @ModelAttribute AdoptionDto adoptionDto,
                                                            @RequestParam(required = false) List<MultipartFile> imageFiles){
        CustomResponse<Adoption> response = adoptionService.update(id, adoptionDto, imageFiles);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> cancelAdoption(@PathVariable("id") UUID id) {
        CustomResponse<Boolean> response = adoptionService.cancelAdoption(id);
        if (response.isError()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

}
