package com.nidhi.quiz_service.repository;


import com.nidhi.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Quiz findByTitle(String title);
}
