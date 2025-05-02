package com.example.LUPP_API.repositories;


import com.example.LUPP_API.domain.media.Media;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    List<Media> findAllByOrderByCreationDateDesc();
    List<Media> findByCategory(String category);

    @Query("SELECT m FROM Media m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(m.description) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Media> searchByTitleOrDescription(@Param("term") String term);
}