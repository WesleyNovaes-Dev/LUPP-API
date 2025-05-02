package com.example.LUPP_API.repositories;


import com.example.LUPP_API.domain.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}