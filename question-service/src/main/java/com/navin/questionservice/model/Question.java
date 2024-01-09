package com.navin.questionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    private Integer id;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correct_answer;
    private String category;
}
