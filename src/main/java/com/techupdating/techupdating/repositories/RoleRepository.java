package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

//    Role findByUserId(int userId);
}
