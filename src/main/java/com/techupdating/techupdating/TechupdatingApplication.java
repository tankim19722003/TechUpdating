package com.techupdating.techupdating;

import com.techupdating.techupdating.Services.UserService;
import com.techupdating.techupdating.configurations.CustomUserDetailsService;
import com.techupdating.techupdating.configurations.EmailServiceImpl;
import com.techupdating.techupdating.dtos.AdminLoginDTO;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.dtos.UserRegisterDTO;
import com.techupdating.techupdating.models.Role;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.RoleRepository;
import com.techupdating.techupdating.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TechupdatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechupdatingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			RoleRepository roleRepository,
			UserRepository userRepository,
			UserService userService,
			EmailServiceImpl emailService,
			CustomUserDetailsService customUserDetailsService
	) {
		return runner -> {
			UserDetail(customUserDetailsService);
//			sendEmail(emailService);
//			createUser(roleRepository, userRepository);
		};
	}

	private void UserDetail(CustomUserDetailsService customUserDetailsService) {
		String account = "tuankiet123";
		customUserDetailsService.loadUserByUsername(account);

	}

	private void sendEmail(EmailServiceImpl emailService) {
		String to = "tankim1972@gmail.com";
		String subject = "Integrate email to project";
		String text = "<h1>My content</h1>";
		String from = "tankim071902@gmail.com";
		emailService.sendHtmlMessage(to, subject, text);

	}

	public void loginAdmin(UserService userService) {

		AdminLoginDTO adminLoginDTO = new AdminLoginDTO("ngocnhan1972","ngocnhan192");
		User user = userService.loginAdmin(adminLoginDTO);

		System.out.println(user);

	}

	public void findByAccount(UserService userService) {

		System.out.println("Finding user by Account: ");
		UserLoginDTO  userLoginDTO= UserLoginDTO.builder()
				.account("ngocson1997")
				.password("ngocson1997")
				.build();

		User user = userService.login(userLoginDTO);
		System.out.println("User: " + user);

	}

	private void createUserWithService(UserService userService) {

		UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
				.account("tuankiet1234")
				.fullname("Kim Tuan Kiet")
				.password("1234567891")
				.retypePassword("123456789")
				.email("Tuankiet12345@gmail.com")
				.build();

		// save user
		System.out.println("Saving user: ");
		User user = userService.register(userRegisterDTO);

		// print user has just saved
		System.out.println(user);

	}

	private void checkExistingUser(UserRepository userRepository) {
		System.out.println("Finding user ");
		boolean existingUser = userRepository.existsByAccount("Tankim1972");

		System.out.println("User existing: " + existingUser);
	}

	private void findRoleWithJoinFetch(RoleRepository roleRepository) {
//		Role role = roleRepository.findUserByIdWithJoinFetch(1);
//		System.out.println(role);
	}

	private void findUserById(UserRepository userRepository) {
		// id
		int id = 1;

		System.out.println("Finding user");
		User user = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User does not exist")
		);
		System.out.println(user);



	}
	private void createRole(RoleRepository roleRepository, UserRepository userRepository) {
		Role role = new Role();

		role = roleRepository.save(role);
		System.out.println(role);

		System.out.println("Create role successfully");

	}

	private void createUser(RoleRepository roleRepository, UserRepository userRepository) {
		User user = new User();

		user.setAccount("Tankim1972");
		user.setEmail("tankim1972@gmail.com");
		user.setId(1);
		user.setPassword("ljfndsfjs");
		user.setFullname("Kim Ngoc Tan");
		user.setEnabled(true);

		// saving user
		System.out.println("Saving user");
		user = userRepository.save(user);

		System.out.println(user);
	}

}
