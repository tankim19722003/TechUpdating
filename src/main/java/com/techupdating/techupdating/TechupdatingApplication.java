package com.techupdating.techupdating;

import com.techupdating.techupdating.models.Role;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.RoleRepository;
import com.techupdating.techupdating.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class TechupdatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechupdatingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			RoleRepository roleRepository,
			UserRepository userRepository
	) {
		return runner -> {
			findRoleWithJoinFetch(roleRepository);
//			findUserById(userRepository);
//			createUser(roleRepository, userRepository);
//			createRole();
		};
	}

	private void findRoleWithJoinFetch(RoleRepository roleRepository) {
		Role role = roleRepository.findUserByIdWithJoinFetch(1);
		System.out.println(role.getUsers());
		System.out.println(role);
	}

	private void findUserById(UserRepository userRepository) {
		// id
		int id = 1;

		System.out.println("Finding user");
		User user = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User does not exist")
		);

		System.out.println("Role: " + user.getRole());


	}
	private void createRole(RoleRepository roleRepository, UserRepository userRepository) {
		Role role = new Role();
		role.setName("User");

		role = roleRepository.save(role);
		System.out.println(role);

		System.out.println("Create role successfully");

	}

	private void createUser(RoleRepository roleRepository, UserRepository userRepository) {
		User user = new User();

		user.setAccount("Tankim1972");
		user.setBirthdate(new Date("2001/01/01"));
		user.setEmail("tankim1972@gmail.com");
		user.setId(1);
		user.setPassword("ljfndsfjs");
		user.setFullname("Kim Ngoc Tan");

		
		// set role for user
		int roleId = 1;
		Role role = roleRepository.findById(roleId).orElseThrow(
				() ->  new RuntimeException("User does not exist")
		);
		user.setRole(role);

		// saving user
		System.out.println("Saving user");
		user = userRepository.save(user);

		System.out.println(user);
	}

}
