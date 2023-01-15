package com.erp.spdv.rest;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.model.entity.Client;
import com.erp.spdv.model.repository.ClientRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
	
	private final ClientRepository repository;
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client save(@Valid @RequestBody Client client) {
		client = this.repository.save(client);
		return client;
	} 
	                
	@GetMapping()
	public List<Client> getAllClient() {
		return this.repository
				.findAll();
				
	}
	
	@GetMapping("{id}")
	public Client getClientById(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
	}
	
	@GetMapping("/category{id}")
	public Client getProductsByCategoryId(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
	}
	
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProductById(@PathVariable Integer id) {
		this.repository
			.findById(id)
			.map( product -> {
					repository.deleteById(id);
					return Void.TYPE;
				})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)                          
	public void update(@PathVariable Integer id, @RequestBody @Valid Client clientUpdated) {
		this.repository
		.findById(id)
		.map( client -> {
				clientUpdated.setId(client.getId());
				return repository.save(clientUpdated);
			})
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
