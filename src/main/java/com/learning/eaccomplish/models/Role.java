package com.learning.eaccomplish.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String roleName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    private List<Parent> users;

}
