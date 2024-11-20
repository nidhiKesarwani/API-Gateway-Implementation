package com.nidhi.question_service.repository;


import com.nidhi.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);

    @Query(value="SELECT * FROM question WHERE category= :category order by rand() LIMIT :limit", nativeQuery=true)
    List<Question> findRandomQuestionByCategoryWithLimit(@Param("category") String category, @Param("limit") int limit);
}
