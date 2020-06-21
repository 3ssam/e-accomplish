package com.learning.eaccomplish.models;

import com.learning.eaccomplish.models.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String creditNumber;

    private Boolean expiryMonth;

    private String expiryYear;

    private String panCode;

    private String Name;

    private PaymentType type;

    @OneToOne(mappedBy = "payment",fetch = FetchType.LAZY)
    private Parent parent;

}
