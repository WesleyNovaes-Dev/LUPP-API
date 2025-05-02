package com.example.LUPP_API.repositories;


import com.example.LUPP_API.domain.alternative.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlternativeRepository extends JpaRepository<Alternative, UUID> {
}