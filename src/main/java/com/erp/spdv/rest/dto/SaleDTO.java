package com.erp.spdv.rest.dto;


import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.model.entity.Client;
import com.erp.spdv.model.entity.Product;
import com.erp.spdv.model.entity.Sale;
import com.erp.spdv.model.repository.ClientRepository;
import com.erp.spdv.model.repository.ProductRepository;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class SaleDTO {
	
	@NotNull(message= "{field.sale.salePrice.required}")
	@Min(value=1, message= "{field.sale.salePrice.minValue}")
	private BigDecimal salePrice;
	@NotEmpty(message = "{field.sale.saleType.required}")
	private String saleType;
	@NotNull(message= "{field.sale.client.required}")
	@Min(value=1, message= "{field.sale.client.minValue}")
	private Integer client;
	@NotNull(message= "{field.sale.product.required}")
	@Min(value=1, message= "{field.sale.product.minValue}")
	private Integer product;
	
	public Sale saleFromDTO(SaleDTO saleDTO, ClientRepository clientRepository,
			ProductRepository productRepository) {
		Sale sale = new Sale();
		sale.setSalePrice(saleDTO.getSalePrice());
		sale.setSaleType(saleDTO.getSaleType());
		Client client = clientRepository.findById(saleDTO.getClient()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente nao encontrada"));
		Product product = productRepository.findById(saleDTO.getProduct()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto nao encontrado"));
		sale.setClient(client);
		sale.setProduct(product);
		sale.setSaleDate(Calendar.getInstance().getTime());
		
		return sale;
	}
}
