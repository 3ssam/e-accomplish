package com.learning.eaccomplish.services;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
import com.learning.eaccomplish.repositories.ParentRepository;
import com.learning.eaccomplish.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void createPayment(Payment payment,Parent parent) {
        payment.setActive(true);
        paymentRepository.save(payment);
        parent.setPayment(payment);
        parentRepository.save(parent);
    }

    public Payment getPayment(Long id) {
        Payment payment = paymentRepository.getOne(id);
        if (payment == null)
            throw new IllegalArgumentException("Invalid Payment Id:" + id);
        return payment;
    }

    public Payment getPaymentByParent(Parent parent) {
        Payment payment = paymentRepository.getByParent(parent);
        if (payment == null)
            throw new IllegalArgumentException("Invalid Payment");
        return payment;
    }


    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.getOne(id);
        if (payment == null)
            throw new IllegalArgumentException("Invalid Payment Id:" + id);
        paymentRepository.delete(payment);
    }

    @Transactional
    public void EditPayment(Payment payment,Parent parent) {
        paymentRepository.save(payment);
        parent.setPayment(payment);
        parentRepository.save(parent);
    }

}
