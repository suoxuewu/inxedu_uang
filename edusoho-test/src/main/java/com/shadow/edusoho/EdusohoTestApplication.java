package com.shadow.edusoho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.shadow.edusoho")
@SpringBootApplication
public class EdusohoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdusohoTestApplication.class, args);
	}
}
