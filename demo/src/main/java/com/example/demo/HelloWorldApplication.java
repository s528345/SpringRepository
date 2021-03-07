package com.example.demo;

import org.assertj.core.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication()
//@ComponentScan(basePackages={"com/example/demo"})
//@ComponentScan(basePackages = {"controller"})
public class HelloWorldApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(HelloWorldApplication.class, args);

		List<String> list = new ArrayList<String>(java.util.Arrays.asList(context.getBeanDefinitionNames()));

		System.out.println(list.contains("apiValidationHandler"));
	}

}
