package com.example.belejki.demo;

import com.example.belejki.demo.repository.AuthorityRepository;
import com.example.belejki.demo.repository.ReminderRepository;
import com.example.belejki.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
