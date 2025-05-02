package com.example.LUPP_API;

import com.example.LUPP_API.config.AWSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties(AWSProperties.class)
public class LuppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuppApiApplication.class, args);
	}

	@RestController
	static class HelloController {
		@GetMapping("/")
		public String hello() {
			return "Hello World";
		}
	}
}
