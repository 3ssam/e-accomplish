package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long numOne;

    @NotNull
    private long numTwo;

    @NotNull
    private String operator;

    @NotNull
    private long actualResult;

    @NotNull
    private String fullQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    @OneToOne(mappedBy = "question",fetch = FetchType.LAZY)
    private Result result;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lastQuestion")
    private List<Child> children;

}
