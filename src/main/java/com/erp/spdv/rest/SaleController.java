package com.erp.spdv.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.erp.spdv.model.entity.Sale;
import com.erp.spdv.model.repository.ClientRepository;
import com.erp.spdv.model.repository.ProductRepository;
import com.erp.spdv.model.repository.SaleRepository;
import com.erp.spdv.rest.dto.SaleDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
	private final SaleRepository repository;
	private final ClientRepository clientRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public SaleController(SaleRepository repository, ClientRepository clientRepository, ProductRepository productRepository) {
		this.repository = repository;
		this.clientRepository = clientRepository;
		this.productRepository = productRepository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Sale save( @RequestBody @Valid SaleDTO saleDTO) {		
		Sale sale = saleDTO.saleFromDTO(saleDTO, clientRepository, productRepository);
		sale = this.repository.save(sale);
		this.productRepository.substractStock(sale.getProduct().getId());
		sale.substractStock();
		return sale;
	}
	@GetMapping()
	public List<Sale> getAllSales() {
		return this.repository
				.findAll();
	}
	
	@GetMapping("{id}")
	public Sale getSaleById(@PathVariable Integer id) {
		return this.repository
				.findById(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
	}
	
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSaleById(@PathVariable Integer id) {
		this.repository
			.findById(id)
			.map(sale -> {
					repository.deleteById(id);
					return Void.TYPE;
				})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)                          
	public void update(@PathVariable Integer id, @RequestBody SaleDTO saleDTO) {
		this.repository
		.findById(id)
		.map( sale -> {
				Sale saleUpdated = saleDTO.saleFromDTO(saleDTO, clientRepository, productRepository);
				Integer saleUpdatedProductId = saleUpdated.getProduct().getId();
				Integer saleProductId = sale.getProduct().getId();
				saleUpdated.setId(sale.getId());
				saleUpdated = repository.save(saleUpdated);
				if(saleUpdatedProductId != saleProductId) {
				productRepository.addStock(saleProductId);
				productRepository.substractStock(saleUpdatedProductId);
				}
				return saleUpdated;
			}) 
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
