package com.shadow.edusoho.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@ComponentScan("com.shadow.edusoho")
@MapperScan("com.shadow.edusoho")
@SpringBootApplication
public class EdusohoManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdusohoManagerServiceApplication.class, args);
	}
}
