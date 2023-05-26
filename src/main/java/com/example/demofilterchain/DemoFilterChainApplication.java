package com.example.demofilterchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class DemoFilterChainApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoFilterChainApplication.class, args);
	}
	@RequestMapping("/hello")
	public ResponseEntity<String> hello(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("header1","enter into controller");
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body("hello");
	}
}
