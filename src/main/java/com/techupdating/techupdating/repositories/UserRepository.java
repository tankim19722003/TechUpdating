package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   boolean existsByAccount(String account);

   Optional <User> findByAccount(String account);

   // Custom query to update only the email column by user ID
   @Modifying
   @Transactional
   @Query("UPDATE User u SET u.email = :email WHERE u.id = :userId")
   int updateEmailById(@Param("email") String email, @Param("userId") int userId);

   @Modifying
   @Transactional
   @Query("UPDATE User u SET u.fullname = :fullname WHERE u.id = :userId")
   int updateFullnameById(@Param("fullname") String fullname, @Param("userId") int userId);

}
