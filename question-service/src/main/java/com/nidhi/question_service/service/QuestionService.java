package com.nidhi.question_service.service;


import com.nidhi.question_service.model.Question;
import com.nidhi.question_service.model.QuestionDTO;
import com.nidhi.question_service.model.Response;
import com.nidhi.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            List<Question> questions = questionRepository.findAll();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        try{
            List<Question> questions = questionRepository.findByCategory(category);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Question> addQuestion(Question question) {
        try {
           return new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteById(long id) {
         try{
             questionRepository.deleteById(id);
             return new ResponseEntity<>("Question deleted, Id = " + id, HttpStatus.OK);
         } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>("Error while deleting question",
                     HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    public Question updateQuestion(long id, Question question) {
        return questionRepository.findById(id)
                .map(ques -> {
                    ques.setCategory(question.getCategory());
                    ques.setQuestionTitle(question.getQuestionTitle());
                    ques.setDifficultyLevel(question.getDifficultyLevel());
                    ques.setOption1(question.getOption1());
                    ques.setOption2(question.getOption2());
                    ques.setOption3(question.getOption3());
                    ques.setOption4(question.getOption4());
                    ques.setCorrectAnswer(question.getCorrectAnswer());

                    return questionRepository.save(ques);
                })
                .orElse(null);
    }

    public Question patchQuestion(long id, Question question) {
        return questionRepository.findById(id)
                .map(ques -> {
                    if(question.getCategory()!=null)
                     ques.setCategory(question.getCategory());

                    if (question.getQuestionTitle()!=null)
                     ques.setQuestionTitle(question.getQuestionTitle());

                    if (question.getDifficultyLevel()!=null)
                     ques.setDifficultyLevel(question.getDifficultyLevel());

                    if (question.getOption1()!=null)
                     ques.setOption1(question.getOption1());

                    if (question.getOption2()!=null)
                     ques.setOption2(question.getOption2());

                    if (question.getOption3()!=null)
                      ques.setOption3(question.getOption3());

                    if (question.getOption4()!=null)
                      ques.setOption4(question.getOption4());

                    if (question.getCorrectAnswer()!=null)
                     ques.setCorrectAnswer(question.getCorrectAnswer());

                    return questionRepository.save(ques);
                })
                .orElse(null);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numberOfQuestions) {

        List<Question> questions = questionRepository.findRandomQuestionByCategoryWithLimit(category, numberOfQuestions);
        List<Integer> ans = new ArrayList<>();
        for(Question q : questions){
            ans.add(q.getId());
        }

        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(List<Integer> ids) {
        List<QuestionDTO> resp = new ArrayList<>();

        for(Integer i : ids){
            Long id = Long.valueOf(i);
            Question ques = questionRepository.findById(id).get();
            QuestionDTO q = new QuestionDTO(ques.getId(), ques.getOption1(), ques.getOption2(),
                    ques.getOption3(), ques.getOption4(), ques.getQuestionTitle());
            resp.add(q);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer score = 0;

        for(Response r : responses){
            String answer = questionRepository.findById((long)r.getId()).get().getCorrectAnswer();
            if(answer.equals(r.getResponse()))
                score++;
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
