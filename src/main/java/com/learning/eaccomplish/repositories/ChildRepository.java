package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Child;
import com.learning.eaccomplish.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Child getByNameAndPinCodeAndParent(String name,String pinCode,Parent parent);
    
    List<Child> findAllByParent(Parent parent);
}
