package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @OneToOne()
    private Result result;

    @OneToMany(mappedBy = "lastQuestion",fetch = FetchType.LAZY)
    private List<Child> children = new ArrayList<>();

}
