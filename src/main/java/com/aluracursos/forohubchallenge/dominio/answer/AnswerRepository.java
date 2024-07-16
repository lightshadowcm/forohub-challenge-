package com.aluracursos.forohubchallenge.dominio.answer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{
    @Query("""
            SELECT a
            FROM Answer a
            WHERE a.topic.id = :idTopic
            AND a.answer = :message
            """)
    Optional<Answer> findByTopicMessage(Long idTopic, String message);
}
