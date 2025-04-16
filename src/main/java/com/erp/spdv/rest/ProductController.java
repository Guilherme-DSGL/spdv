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

import com.erp.spdv.model.entity.Product;
import com.erp.spdv.model.repository.CategoryRepository;
import com.erp.spdv.model.repository.ProductRepository;
import com.erp.spdv.rest.dto.ProductDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductRepository repository;
	private final CategoryRepository categoryRepository;
	
	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product save( @RequestBody @Valid ProductDTO productDTO) {		
		Product product = productDTO.productFromDTO(productDTO, categoryRepository);
		product = this.repository.save(product);
		return product;
	}
	@GetMapping()
	public List<Product> getAllProducts() {
		return this.repository
				.findAll();
	}
	
	@GetMapping("{id}")
	public Product getProductById(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	@GetMapping("/category{id}")
	public Product getProductsByCategoryId(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
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
	public void update(@PathVariable Integer id, @RequestBody @Valid ProductDTO productDTO) {
		this.repository
		.findById(id)
		.map( product -> {
				Product productUpdated = productDTO.productFromDTO(productDTO, categoryRepository);
				productUpdated.setId(product.getId());
				return repository.save(productUpdated);
			})
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
