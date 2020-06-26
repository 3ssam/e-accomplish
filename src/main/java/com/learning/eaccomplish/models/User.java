package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "This field is required field")
    private String email;

    @NotBlank(message = "This field is required field")
    private String password;

    @NotNull(message = "This field is required field")
    private Boolean activated;

    @NotBlank(message = "This field is required field")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;
}
