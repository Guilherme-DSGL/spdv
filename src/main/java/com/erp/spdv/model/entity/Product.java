package com.erp.spdv.model.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
		
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	@NotEmpty(message="{field.name.required}")
	private String name;
	@NotEmpty()
	@Column(nullable = false, length = 150)
	private String brand;
	
	@ManyToOne
	@NotNull(message = "{field.product.category.required}")
	@JoinColumn(name = "id_category")
	private Category category;
	
	@Column(nullable = false, length = 10)
	@Min(value = 1, message = "{field.price.minValue}")
	private double price;
	
	@Column(name = "purchase_price", nullable = false, length = 5)
	@Min(value = 1, message = "{field.purchasePrice.minValue}")
	private double purchasePrice;
	
	
	@Column(nullable = false, length = 10)
	@Min(value = 0, message = "{field.stock.minValue}")
	private int stock;
	
	
	
}
