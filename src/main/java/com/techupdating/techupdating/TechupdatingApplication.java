package com.techupdating.techupdating;

import com.techupdating.techupdating.Services.ImageServiceImpl;
import com.techupdating.techupdating.Services.ImageStoreService;
import com.techupdating.techupdating.Services.ImageStoreServiceImpl;
import com.techupdating.techupdating.models.Image;
import com.techupdating.techupdating.repositories.ImageRepository;
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
			ImageServiceImpl imageService
	) {
		return runner -> {
//			removeImage(imageService);
		};
	}

//	private void removeImage(ImageServiceImpl imageService) {
//		imageService.removeImageFormDb(21);
//	}
}