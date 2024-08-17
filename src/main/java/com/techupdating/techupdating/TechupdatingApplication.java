package com.techupdating.techupdating;

import com.techupdating.techupdating.Services.*;
import com.techupdating.techupdating.configurations.EmailServiceImpl;
import com.techupdating.techupdating.dtos.AdminLoginDTO;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.dtos.UserRegisterDTO;
import com.techupdating.techupdating.models.*;
import com.techupdating.techupdating.repositories.*;
import com.techupdating.techupdating.responses.CategoryResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class TechupdatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechupdatingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
		CourseRepository courseRepository,
		CourseRegistrationRepository registerCourseRepository
	) {
		return runner -> {
//			checkExistingUserInCourse(registerCourseRepository);
//			findRegistrationByUserIdAndCourseId(registerCourseRepository);
			calculateDays();
		};
	}

	private void calculateDays() {

		LocalDate localDate = LocalDate.of(2024, 8, 17);
		LocalDate localDate2 = LocalDate.of(2024, 8, 20);
		long daysBetween = ChronoUnit.DAYS.between(localDate, localDate2);
		System.out.println(daysBetween);
	}


	private void findRegistrationByUserIdAndCourseId(CourseRegistrationRepository courseRegistrationRepository){

		int userId = 1;
		int courseId = 2;

		// find usser
		System.out.println("Finding user: ");
		CourseRegistration courseRegistration = courseRegistrationRepository
				.findRegistrationByUserIdAndCourseId(userId, courseId);

		System.out.println(courseRegistration);

	}
	private void checkExistingUserInCourse(CourseRegistrationRepository registerCourseRepository) {

		int userId = 1;
		int courseId = 2;

		boolean isExistingUserInCourse = registerCourseRepository.existsByUserIdAndCourseId(userId, courseId);

		System.out.println("User is existing: " + isExistingUserInCourse);

	}

	private void findCourseById(CourseRegistrationRepository courseRegistrationRepository) {

		int id = 1;

		CourseRegistration courseRegistration = courseRegistrationRepository.findById(id).orElseThrow(
				() ->  new RuntimeException("Course does not found")
		);

		System.out.println(courseRegistration);

	}

	private void findCourseById(CourseRepository courseRepository) {

		// course id
		int courseId = 1;

		Course course = courseRepository.findById(courseId).orElseThrow(
				() ->  new RuntimeException("Data not found exception")
		);

		System.out.println(course);
	}


	private void findCategory(CategoryService categoryService) {

		int id = 1;

		CategoryResponse category = categoryService.findCategoryById(id);

		System.out.println(category);
	}

	private void findLanguageWithJoinFetch(LanguageRepository languageRepository) {

		// set id for language id
		int languageId = 1;

		// query to find courses have same language id
		Language language = languageRepository.findLanguageWithJoinFetch(languageId).orElseThrow(
				() -> new RuntimeException("Language does not found")
		);

		// print language
		System.out.println("Language: " + language);
		// print list course with this language
		if (language.getCourses() != null) {
			List<Course> courses = language.getCourses();

			for(Course course : courses) {
				System.out.println(course);
			}
		}

	}

	private void findAllLanguages(LanguageService languageService) {

		// return all languages
		System.out.println(languageService.findAllLanguages());
	}
	private void findCourseByLanguageId(PostService postService) {

		// set language id to 1
		int languageId = 1;

		// find course with language id exist
		System.out.println("Finding courses with language id: " + languageId);
//		List<Course> courses = postService.findCoursesByLanguageId(languageId);

		// print courses with language id exist
//		System.out.println("result: " + courses);


	}

//	private void UserDetail(CustomUserDetailsService customUserDetailsService) {
//		String account = "tuankiet123";
//		customUserDetailsService.loadUserByUsername(account);
//
//	}

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
		int id = 5;

		System.out.println("Finding user");
		User user = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User does not exist")
		);

		System.out.println(user.getRoles());
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
