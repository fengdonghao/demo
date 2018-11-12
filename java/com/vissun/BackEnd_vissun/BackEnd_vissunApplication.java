package com.vissun.BackEnd_vissun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class BackEnd_vissunApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEnd_vissunApplication.class, args);
	}
}
