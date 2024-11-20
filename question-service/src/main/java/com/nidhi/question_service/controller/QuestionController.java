package com.nidhi.question_service.controller;


import com.nidhi.question_service.model.Question;
import com.nidhi.question_service.model.QuestionDTO;
import com.nidhi.question_service.model.Response;
import com.nidhi.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestion")
    public ResponseEntity<List<Question>> getAllQuestions() {
      return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String category) {
        return questionService.getByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id) {
        return questionService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Question updateQuestion(@PathVariable int id,  @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Question> patchQuestion(@PathVariable int id, @RequestBody Question question) {
        Question updatedQuestion =  questionService.patchQuestion(id, question);

        if (updatedQuestion != null) {
            return ResponseEntity.ok(updatedQuestion); // Return the updated question with status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the question was not found
        }
    }

    @GetMapping("/getQuestions")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam int numberOfQuestions){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsForQuiz(category, numberOfQuestions);
    }

   @PostMapping("/generateQuiz")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> ids){
       System.out.println(environment.getProperty("local.server.port"));
          return questionService.getQuestionsFromId(ids);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getScore(responses);
    }
}
