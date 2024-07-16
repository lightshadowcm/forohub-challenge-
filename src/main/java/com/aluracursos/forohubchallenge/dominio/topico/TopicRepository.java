package com.aluracursos.forohubchallenge.dominio.topico;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{

        @Query("""
                SELECT t.title
                FROM Topic t
                WHERE REPLACE(t.title, " ", "") = :title
                """)
        String findByTitle(String title);

        @Query("""
                SELECT t.message
                FROM Topic t
                WHERE REPLACE(t.message, " ", "") = :message
                """)
        String findByMessage(String message);

        @Query("""
                SELECT t
                FROM Topic t
                WHERE t.id = :idTopic
                AND t.author.id = :idAuthor        
                """)
        Optional<Topic> findByIdAndAuthorId(Long idTopic, Long idAuthor);

        @Query("""
                SELECT t
                FROM Topic t
                WHERE t.id = :idTopic
                AND t.status = true
                """)
        Optional<Topic> findByIdAndStatus(Long idTopic);
}
