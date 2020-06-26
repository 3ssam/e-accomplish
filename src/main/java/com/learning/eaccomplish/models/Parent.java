package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
public class Parent{

    private boolean isFullChild;

    @OneToOne()
    private Payment payment;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "This field is required field")
    private String email;

    @NotBlank(message = "This field is required field")
    private String password;

    @NotNull(message = "This field is required field")
    private Boolean activated = true;

    @NotBlank(message = "This field is required field")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Child> children;

}
