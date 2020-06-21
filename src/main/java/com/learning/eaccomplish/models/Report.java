package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int numOfWrong;

    private int numOfRight;

    private int totalNumber;

    private String state;

    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id")
    private Quiz quiz;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
    private List<Result> results;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private Child child;
}
