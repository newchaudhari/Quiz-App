package com.navin.questionservice.service;

import com.navin.questionservice.dao.QuestionDao;
import com.navin.questionservice.model.Question;
import com.navin.questionservice.model.QuestionWrapper;
import com.navin.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQ) {
        List<Integer> questionList = questionDao.findAllRandomByCategory(categoryName, numQ);

        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<QuestionWrapper> wrappersList = new ArrayList<>();
        List<Question> questionsList = new ArrayList<>();

        for (Integer id : questionIds) {
            questionsList.add(questionDao.findById(id).get());
        }

        for (Question question : questionsList) {
            QuestionWrapper wrapper = new QuestionWrapper(question.getId(), question.getTitle(),
                    question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            wrappersList.add(wrapper);
        }

        return new ResponseEntity<>(wrappersList, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (response.getResponse().equals(question.getCorrect_answer()))
                right++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
