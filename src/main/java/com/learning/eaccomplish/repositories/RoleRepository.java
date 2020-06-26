package com.learning.eaccomplish.repositories;

import com.learning.eaccomplish.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByRoleName(String role);
}
