package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   boolean existsByAccount(String account);

   Optional <User> findByAccount(String account);
}
