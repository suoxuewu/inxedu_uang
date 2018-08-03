package com.shadow.edusoho.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.shadow.edusoho")
@SpringBootApplication
public class EdusohoManagerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdusohoManagerWebApplication.class, args);
	}
}
