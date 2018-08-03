package com.shadow.edusoho;

import com.shadow.edusoho.Bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

/**
 * @RunW  使用SpringBoot的驱动器在
 * @SpringBootTest 这是一个SpringBoot的单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EdusohoTestApplicationTests {

	@Autowired
    Person person;
	@Test
	public void contextLoads() {
		System.out.println(person);
	}
	@Test
	public void test() {
		Class<Person> personClass = Person.class;
		AnnotatedType[] annotatedInterfaces = personClass.getAnnotatedInterfaces();
		int length = annotatedInterfaces.length;
		System.out.println(length);
		//获取当前类上的所有注解
		Annotation[] declaredAnnotations = personClass.getDeclaredAnnotations();
		Class<? extends Annotation> aClass = declaredAnnotations[0].annotationType();
		System.out.println(declaredAnnotations.length);
		System.out.println("--------------");
		Component declaredAnnotation = personClass.getDeclaredAnnotation(Component.class);
		System.out.println(declaredAnnotation.value());



	}

}
