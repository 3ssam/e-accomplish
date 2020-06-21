package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private Boolean activated;

    private String email;

    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;


}
