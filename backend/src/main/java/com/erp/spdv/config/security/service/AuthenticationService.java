package com.erp.spdv.config.security.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.config.security.core.Role;
import com.erp.spdv.model.entity.User;
import com.erp.spdv.model.repository.UserRepository;
import com.erp.spdv.rest.dto.AuthenticateRequestUserDTO;
import com.erp.spdv.rest.dto.AuthenticationResponseUserDTO;
import com.erp.spdv.rest.dto.RegisterRequestUserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public Optional<AuthenticationResponseUserDTO> register(RegisterRequestUserDTO request) {
		var user = User.builder()
				.name(request.getName())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		if(!repository.existsByName(user.getName())) {
			repository.save(user);
			var jwtToken = jwtService.generateToken(user);
			return Optional.of(AuthenticationResponseUserDTO.builder().token(jwtToken).build());
		}
		return Optional.empty();
	}

	public AuthenticationResponseUserDTO authenticate(AuthenticateRequestUserDTO request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()
						));

		var user = repository.findByName(request.getName()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponseUserDTO.builder().token(jwtToken).build();
	}


}
