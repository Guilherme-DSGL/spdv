package com.erp.spdv.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.config.security.service.AuthenticationService;
import com.erp.spdv.model.repository.UserRepository;
import com.erp.spdv.rest.dto.AuthenticateRequestUserDTO;
import com.erp.spdv.rest.dto.AuthenticationResponseUserDTO;
import com.erp.spdv.rest.dto.RegisterRequestUserDTO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRepository repository;
	private final AuthenticationService service;
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthenticationResponseUserDTO save(@RequestBody RegisterRequestUserDTO request) {
		return service.register(request).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existente")); 
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponseUserDTO> authenticate(@RequestBody AuthenticateRequestUserDTO request){
		return ResponseEntity.ok(service.authenticate(request));
		
	}
	
}
