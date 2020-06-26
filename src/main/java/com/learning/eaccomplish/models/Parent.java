package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@Entity
public class Parent extends User{

    private boolean isFullChild;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Child> children;
}
