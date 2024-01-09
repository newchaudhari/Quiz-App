package com.navin.quizservice.controller;


import com.navin.quizservice.model.QuestionWrapper;
import com.navin.quizservice.model.QuizDto;
import com.navin.quizservice.model.Response;
import com.navin.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz
            (@RequestBody QuizDto quizDto) {

        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQ(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> questionForUser(@PathVariable Integer id){
        return quizService.questionForUser(id);
    }
    @PostMapping("score/{id}")
    public ResponseEntity<Integer> getScore(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.getScore(id, responses);
    }
}
