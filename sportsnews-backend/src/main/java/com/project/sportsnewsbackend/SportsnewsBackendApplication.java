package com.project.sportsnewsbackend;

import com.project.sportsnewsbackend.repository.Stories.StoriesRepository;
import com.project.sportsnewsbackend.service.Stories.StoriesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SportsnewsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsnewsBackendApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(StoriesService storiesService, StoriesRepository storiesRepository) {
//		return args ->
//
//		{
//			storiesService.deleteStory(6L);
//		};
//	}




}
