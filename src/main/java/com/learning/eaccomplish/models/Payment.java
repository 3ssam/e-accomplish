package com.learning.eaccomplish.models;

import com.learning.eaccomplish.models.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 16,min = 16,message = "should be 12 digit")
    @NotBlank(message = "This field is required field")
    private String creditNumber;

    @Size(max = 2,min = 2,message = "should be 2 digit")
    @NotBlank(message = "This field is required field")
    private String expiryMonth;

    @Size(max = 2,min = 2,message = "should be 2 digit")
    @NotBlank(message = "This field is required field")
    private String expiryYear;

    @Size(max = 3,min = 3,message = "should be 3 digit")
    @NotBlank(message = "This field is required field")
    private String panCode;

    @NotBlank(message = "This field is required field")
    private String name;

    @NotBlank(message = "This field is required field")
    private String type;

    @OneToOne()
    private Parent parent;
}
