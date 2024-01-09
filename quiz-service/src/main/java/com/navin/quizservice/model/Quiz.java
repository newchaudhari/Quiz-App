package com.navin.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer quiz_id;
    private String title;

    @ElementCollection
    private List<Integer> questions;
}
