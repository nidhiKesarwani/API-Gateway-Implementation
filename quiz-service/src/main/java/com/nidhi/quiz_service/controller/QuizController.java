package com.nidhi.quiz_service.controller;



import com.nidhi.quiz_service.model.QuestionDTO;
import com.nidhi.quiz_service.model.Quiz;
import com.nidhi.quiz_service.model.QuizDTO;
import com.nidhi.quiz_service.model.Response;
import com.nidhi.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quiz){

       return quizService.createQuiz(quiz.getCategory(), quiz.getNumberOfQuestion(),
                                     quiz.getTitle());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Quiz>> getQuiz(){
        return quizService.getAllQuiz();
    }

//    @GetMapping("/getByName/{title}")
//    public ResponseEntity<List<QuestionDTO>> getQuizByTitle(@PathVariable String title){
//        return quizService.getQuestionsFromQuizbyTitle(title);
//    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizById(@PathVariable Long id){
        return quizService.getQuestionsFromQuizById(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Long id, @RequestBody List<Response> resp){
         return  quizService.checkAnswers(id, resp);
    }

}
