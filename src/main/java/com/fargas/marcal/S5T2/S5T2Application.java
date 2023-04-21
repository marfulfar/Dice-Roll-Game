package com.fargas.marcal.S5T2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class S5T2Application {


	@Bean
	public ModelMapper modelMapper(){return new ModelMapper();}

	public static void main(String[] args) {
		SpringApplication.run(S5T2Application.class, args);
	}

}
