package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
