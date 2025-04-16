package com.erp.spdv.rest.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.model.entity.Category;
import com.erp.spdv.model.entity.Product;
import com.erp.spdv.model.repository.CategoryRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
	
	@NotEmpty(message="{field.product.name.required}")
	private String name;
	@NotEmpty(message="{field.product.brand.required}")
	private String brand;
	
	@NotNull(message = "{field.product.category.required}")
	@Min(value = 1, message = "{field.product.category.minValue}")
	private Integer category;
	
	@NotNull(message = "{field.product.price.required}")
	@Min(value = 1, message = "{field.product.price.minValue}")
	private Integer price;
	
	@NotNull(message = "{field.product.purchasePrice.required}")
	@Min(value = 1, message = "{field.product.purchasePrice.minValue}")
	private Integer purchasePrice;
	
	@NotNull(message = "{field.product.stock.required}")
	@Min(value = 0, message = "{field.product.stock.minValue}")
	private Integer stock;
	
	public Product productFromDTO(ProductDTO productDTO, CategoryRepository repository) {
		Product product = new Product();
		product.setBrand(productDTO.getBrand());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setPurchasePrice(productDTO.getPurchasePrice());
		product.setStock(productDTO.getStock());
		Category category  = repository
				.findById(productDTO.getCategory())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria nao encontrada"));
		product.setCategory(category);
		
		return product;
	}
}
