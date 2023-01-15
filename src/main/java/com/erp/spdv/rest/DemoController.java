package com.erp.spdv.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.spdv.config.security.service.AuthenticationService;
import com.erp.spdv.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {
	
	@GetMapping
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Say hello");
	}
}
