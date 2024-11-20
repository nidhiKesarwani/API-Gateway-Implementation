package com.nidhi.quiz_service.model;

import lombok.Data;

@Data
public class QuizDTO {
    private String category;
    private int numberOfQuestion;
    private String title;
}
