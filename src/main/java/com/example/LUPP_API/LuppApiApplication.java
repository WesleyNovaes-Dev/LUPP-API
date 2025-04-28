package com.example.LUPP_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LuppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuppApiApplication.class, args);
	}

	@RestController
	public class HelloController {

		@GetMapping("/")
		public String hello() {
			return "Hello World";
		}
	}
}
