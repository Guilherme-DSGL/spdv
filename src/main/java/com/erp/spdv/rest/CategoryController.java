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

import com.erp.spdv.model.entity.Category;
import com.erp.spdv.model.repository.CategoryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
	
private final CategoryRepository repository;
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Category save(@Valid @RequestBody Category category) {
		category = this.repository.save(category);
		return category;
	}
	
	@GetMapping()
	public List<Category> getAllCategories() {
		return this.repository.findAll();
	}
	
	@GetMapping("{id}")
	public Category getCategoryById(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria nao encontrado"));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoryById(@PathVariable Integer id) {
		this.repository
			.findById(id)
			.map( category -> {
					repository.deleteById(id);
					return Void.TYPE;
				})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)                          
	public void update(@PathVariable Integer id, @RequestBody @Valid Category categoryUpdated) {
		this.repository
		.findById(id)
		.map( category -> {
			categoryUpdated.setId(category.getId());
				return repository.save(categoryUpdated);
			})
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
