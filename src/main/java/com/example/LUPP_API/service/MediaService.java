package com.example.LUPP_API.service;


import com.example.LUPP_API.domain.Question.Question;
import com.example.LUPP_API.domain.Question.QuestionRequestDTO;
import com.example.LUPP_API.domain.Question.QuestionResponseDTO;
import com.example.LUPP_API.domain.alternative.Alternative;
import com.example.LUPP_API.domain.alternative.AlternativeRequestDTO;
import com.example.LUPP_API.domain.alternative.AlternativeResponseDTO;
import com.example.LUPP_API.domain.media.Media;
import com.example.LUPP_API.domain.media.MediaRequestDTO;
import com.example.LUPP_API.domain.media.MediaResponseDTO;
import com.example.LUPP_API.domain.media.MediaType;
import com.example.LUPP_API.repositories.MediaRepository;
import com.example.LUPP_API.repositories.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Cria nova mídia (com ou sem perguntas)
    public MediaResponseDTO createMedia(MediaRequestDTO dto) {
        if (dto.getType() == MediaType.QUESTION && (dto.getQuestions() == null || dto.getQuestions().isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mídias do tipo QUESTION devem ter perguntas");
        }
        if (dto.getType() == MediaType.POST && dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mídias do tipo POST não podem ter perguntas");
        }

        Media media = new Media();
        media.setTitle(dto.getTitle());
        media.setDescription(dto.getDescription());
        media.setCategory(dto.getCategory());
        media.setMediaUrl(dto.getMediaUrl());
        media.setType(dto.getType());
        media.setCreationDate(LocalDateTime.now());

        // Se for QUESTION, adiciona perguntas e alternativas
        if (dto.getType() == MediaType.QUESTION) {
            for (QuestionRequestDTO qdto : dto.getQuestions()) {
                Question question = new Question();
                question.setDescription(qdto.getDescription());
                question.setValue(qdto.getValue());
                question.setMedia(media);
                // Alternativas
                for (AlternativeRequestDTO adto : qdto.getAlternatives()) {
                    Alternative alt = new Alternative();
                    alt.setText(adto.getText());
                    alt.setCorrect(adto.getCorrect());
                    alt.setQuestion(question);
                    question.getAlternatives().add(alt);
                }
                media.getQuestions().add(question);
            }
        }

        mediaRepository.save(media);
        return toMediaDTO(media);
    }

    // Lista todas as mídias mais recentes
    public List<MediaResponseDTO> getAllMedia() {
        List<Media> medias = mediaRepository.findAllByOrderByCreationDateDesc();
        return medias.stream().map(this::toMediaDTO).collect(Collectors.toList());
    }

    // Filtra por categoria
    public List<MediaResponseDTO> getMediaByCategory(String category) {
        List<Media> medias = mediaRepository.findByCategory(category);
        return medias.stream().map(this::toMediaDTO).collect(Collectors.toList());
    }

    // Pesquisa por termo no título ou descrição
    public List<MediaResponseDTO> searchMedia(String term) {
        List<Media> medias = mediaRepository.searchByTitleOrDescription(term);
        return medias.stream().map(this::toMediaDTO).collect(Collectors.toList());
    }

    // Busca por ID
    public MediaResponseDTO getMediaById(UUID id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mídia não encontrada"));
        return toMediaDTO(media);
    }

    // Atualiza mídia existente
    public MediaResponseDTO updateMedia(UUID id, MediaRequestDTO dto) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mídia não encontrada"));

        // Atualiza campos básicos
        media.setTitle(dto.getTitle());
        media.setDescription(dto.getDescription());
        media.setCategory(dto.getCategory());
        media.setMediaUrl(dto.getMediaUrl());
        media.setType(dto.getType());

        // Remove perguntas antigas (se houver) e adiciona novas
        media.getQuestions().clear();
        if (dto.getType() == MediaType.QUESTION && dto.getQuestions() != null) {
            for (QuestionRequestDTO qdto : dto.getQuestions()) {
                Question question = new Question();
                question.setDescription(qdto.getDescription());
                question.setValue(qdto.getValue());
                question.setMedia(media);
                for (AlternativeRequestDTO adto : qdto.getAlternatives()) {
                    Alternative alt = new Alternative();
                    alt.setText(adto.getText());
                    alt.setCorrect(adto.getCorrect());
                    alt.setQuestion(question);
                    question.getAlternatives().add(alt);
                }
                media.getQuestions().add(question);
            }
        }

        mediaRepository.save(media);
        return toMediaDTO(media);
    }

    // Deleta mídia por ID
    public void deleteMedia(UUID id) {
        if (!mediaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mídia não encontrada");
        }
        mediaRepository.deleteById(id);
    }

    // Cria pergunta (com alternativas) para uma mídia do tipo QUESTION existente
    public QuestionResponseDTO addQuestion(UUID mediaId, QuestionRequestDTO qdto) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mídia não encontrada"));
        if (media.getType() != MediaType.QUESTION) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mídia não é do tipo QUESTION");
        }

        Question question = new Question();
        question.setDescription(qdto.getDescription());
        question.setValue(qdto.getValue());
        question.setMedia(media);
        for (AlternativeRequestDTO adto : qdto.getAlternatives()) {
            Alternative alt = new Alternative();
            alt.setText(adto.getText());
            alt.setCorrect(adto.getCorrect());
            alt.setQuestion(question);
            question.getAlternatives().add(alt);
        }

        // Associação bidirecional
        media.getQuestions().add(question);
        mediaRepository.save(media);

        return toQuestionDTO(question);
    }

    // Atualiza apenas a URL da mídia (ex: após upload no S3)
    public MediaResponseDTO updateMediaUrl(UUID id, String url) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mídia não encontrada"));
        media.setMediaUrl(url);
        mediaRepository.save(media);
        return toMediaDTO(media);
    }

    // Mapeia entidade Media para DTO
    private MediaResponseDTO toMediaDTO(Media media) {
        List<QuestionResponseDTO> qDtos = media.getQuestions().stream()
                .map(this::toQuestionDTO).collect(Collectors.toList());
        return new MediaResponseDTO(
                media.getId(),
                media.getTitle(),
                media.getDescription(),
                media.getCategory(),
                media.getMediaUrl(),
                media.getCreationDate(),
                media.getType(),
                qDtos
        );
    }

    // Mapeia entidade Question para DTO
    private QuestionResponseDTO toQuestionDTO(Question question) {
        List<AlternativeResponseDTO> aDtos = question.getAlternatives().stream()
                .map(alt -> new AlternativeResponseDTO(alt.getId(), alt.getText(), alt.isCorrect()))
                .collect(Collectors.toList());
        return new QuestionResponseDTO(
                question.getId(),
                question.getDescription(),
                question.getValue(),
                aDtos
        );
    }
}