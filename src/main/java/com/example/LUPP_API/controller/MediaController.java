package com.example.LUPP_API.controller;

import com.example.LUPP_API.domain.Question.QuestionRequestDTO;
import com.example.LUPP_API.domain.Question.QuestionResponseDTO;
import com.example.LUPP_API.domain.media.MediaRequestDTO;
import com.example.LUPP_API.domain.media.MediaResponseDTO;
import com.example.LUPP_API.service.AWSS3Service;
import com.example.LUPP_API.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@Validated
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private AWSS3Service awsS3Service;

    /**
     * 1) Criar mídia via JSON puro
     *    Content-Type: application/json
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MediaResponseDTO> createMediaJson(
            @RequestBody @Validated MediaRequestDTO dto) {

        MediaResponseDTO created = mediaService.createMedia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * 2) Criar mídia via multipart/form-data (arquivo + data JSON)
     *    Content-Type: multipart/form-data
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaResponseDTO> createMediaMultipart(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") @Validated MediaRequestDTO dto) {

        // 1) Faz upload do arquivo ao S3
        String url = awsS3Service.uploadFile(file);
        // 2) Preenche a URL no DTO
        dto.setMediaUrl(url);
        // 3) Persiste a mídia
        MediaResponseDTO created = mediaService.createMedia(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Listar todas as mídias
    @GetMapping
    public ResponseEntity<List<MediaResponseDTO>> getAllMedia() {
        return ResponseEntity.ok(mediaService.getAllMedia());
    }

    // Filtrar por categoria
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MediaResponseDTO>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(mediaService.getMediaByCategory(category));
    }

    // Pesquisar por query
    @GetMapping("/search")
    public ResponseEntity<List<MediaResponseDTO>> searchMedia(@RequestParam String query) {
        return ResponseEntity.ok(mediaService.searchMedia(query));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<MediaResponseDTO> getMediaById(@PathVariable UUID id) {
        return ResponseEntity.ok(mediaService.getMediaById(id));
    }

    // Atualizar mídia
    @PutMapping("/{id}")
    public ResponseEntity<MediaResponseDTO> updateMedia(
            @PathVariable UUID id,
            @RequestBody @Validated MediaRequestDTO dto) {

        return ResponseEntity.ok(mediaService.updateMedia(id, dto));
    }

    // Deletar mídia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable UUID id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }

    // Criar pergunta para uma mídia QUESTION
    @PostMapping("/{id}/questions")
    public ResponseEntity<QuestionResponseDTO> addQuestion(
            @PathVariable UUID id,
            @RequestBody @Validated QuestionRequestDTO dto) {

        QuestionResponseDTO created = mediaService.addQuestion(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
