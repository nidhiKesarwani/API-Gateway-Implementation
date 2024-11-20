package com.nidhi.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    /* When we mention identity here, the column should be
       defined as auto_increment
       ALTER TABLE question MODIFY COLUMN id INT AUTO_INCREMENT;
    */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String category;
    private String questionTitle;
    private String difficultyLevel;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;

}
