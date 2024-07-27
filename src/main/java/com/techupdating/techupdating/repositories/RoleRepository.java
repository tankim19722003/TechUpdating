package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT DISTINCT r FROM Role r "
            + "JOIN FETCH r.users "
            + "WHERE r.id= :id")
    Role findUserByIdWithJoinFetch(@Param("id") int id);

//    Role findByUserId(int userId);
}
