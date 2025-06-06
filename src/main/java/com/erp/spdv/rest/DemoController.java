package com.erp.spdv.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/demo")
@RequiredArgsConstructor
public class DemoController {

	@GetMapping
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Say hello");
	}
}
