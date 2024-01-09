package com.navin.quizservice.service;

import com.navin.quizservice.dao.QuizDao;
import com.navin.quizservice.feign.QuizInterface;
import com.navin.quizservice.model.QuestionWrapper;
import com.navin.quizservice.model.Quiz;
import com.navin.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {
        List<Integer> questionsForQuiz = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionsForQuiz);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> questionForUser(Integer id) {
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Integer> questions = quiz.getQuestions();
            ResponseEntity<List<QuestionWrapper>> questionsFromIds = quizInterface.getQuestionsFromIds(questions);
            return questionsFromIds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> getScore(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
