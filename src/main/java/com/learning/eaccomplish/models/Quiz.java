package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Level;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
    private List<Report> reports;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lastQuiz")
    private List<Child> children;

}
