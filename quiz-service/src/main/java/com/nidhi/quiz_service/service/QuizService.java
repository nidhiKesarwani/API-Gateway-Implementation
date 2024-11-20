package com.nidhi.quiz_service.service;


import com.nidhi.quiz_service.feign.QuizInterface;
import com.nidhi.quiz_service.model.QuestionDTO;
import com.nidhi.quiz_service.model.Quiz;
import com.nidhi.quiz_service.model.Response;
import com.nidhi.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numberOfQuestion, String title) {
       //http://localhost:8080/question/getQuestions?category=Science&numberOfQuestions=5
        // but we are using feign and eureka server so, we will not use the above url
        // we have created a eureka registry which run on port number 8761
        // and by enabling eureka server on question-service
        // we have made it register on the registry.
        // quiz-service has feign client dependency and an interface with feignclient, which directs the
        // requests to question service via eureka server
       List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numberOfQuestion).getBody();
       Quiz quiz = new Quiz();
       quiz.setTitle(title);
       quiz.setQuestionIds(questionIds);
       quizRepository.save(quiz);
        return ResponseEntity.ok("Quiz created");
    }

    public ResponseEntity<List<Quiz>> getAllQuiz() {
        return new ResponseEntity<>(quizRepository.findAll(), HttpStatus.OK);
    }

//    public ResponseEntity<List<QuestionDTO>> getQuestionsFromQuizbyTitle(String title) {
//
////        Quiz quiz = quizRepository.findByTitle(title);
////        List<Question> questions = quiz.getQuestions();
//        List<QuestionDTO> questionDTOS = new ArrayList<>();
////        for(Question question : questions) {
////            QuestionDTO QDto = new QuestionDTO(question.getId(), question.getOption1(),
////                    question.getOption2(), question.getOption3(), question.getOption4(),
////                    question.getQuestionTitle());
////            questionDTOS.add(QDto);
////        }
//       return new ResponseEntity<>(questionDTOS, HttpStatus.OK);
//    }

    public ResponseEntity<List<QuestionDTO>> getQuestionsFromQuizById(Long id) {

        List<Integer> questionId = quizRepository.findById(id).get().getQuestionIds();
        List<QuestionDTO> questionDTOS = quizInterface.getQuestionsFromId(questionId).getBody();
        return new ResponseEntity<>(questionDTOS, HttpStatus.OK);
    }

    public ResponseEntity<Integer> checkAnswers(Long quizId, List<Response> resp) {
        Integer score = quizInterface.getScore(resp).getBody();
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
