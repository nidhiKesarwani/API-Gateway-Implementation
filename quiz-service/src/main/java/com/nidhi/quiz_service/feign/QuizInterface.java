package com.nidhi.quiz_service.feign;

import com.nidhi.quiz_service.model.QuestionDTO;
import com.nidhi.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

        @GetMapping("question/getQuestions")
        public ResponseEntity<List<Integer>> getQuestionsForQuiz(
                @RequestParam String category, @RequestParam int numberOfQuestions);

        @PostMapping("question/generateQuiz")
        public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> ids);

        @PostMapping("question/getScore")
        public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}
