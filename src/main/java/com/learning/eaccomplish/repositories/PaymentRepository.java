package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Parent;
import com.learning.eaccomplish.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment getByParent(Parent parent);
}
