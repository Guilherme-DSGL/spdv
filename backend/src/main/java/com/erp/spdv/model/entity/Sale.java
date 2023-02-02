package com.erp.spdv.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.erp.spdv.rest.dto.dashboard.SalesByMounth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
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
//@SqlResultSetMapping(name = "saleEntityMapping",
//classes = @ConstructorResult(targetClass = SalesByMounth.class,
//columns = {
//		@ColumnResult(name = "amountSales", type= Integer.class),
//		@ColumnResult(name = "amountPrice", type = Integer.class),
//		}))
//@NamedNativeQuery(name = "Sale.findSaleDateByMonth", 
//  				  query = "SELECT COUNT(*) as amountSales, SUM(sale_price) as amountPrice FROM Sale WHERE MONTH(sale_date) = ?1",
//  				  resultSetMapping = "saleEntityMapping")

public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	@Column(nullable = false, length = 100, name="sale_price")
	@Min(value = 1, message = "{field.price.minValue}")
	private BigDecimal salePrice;
	
	@Column(nullable = false, length = 100, name="sale_type")
	@NotEmpty(message="{field.name.required}")
	private String saleType;
	
	@Column(name = "sale_date",nullable = false, length = 10)
	@NotNull(message = "{field.user.birthDate.required}")
	private Date saleDate;
	
	public void substractStock() {
		Integer stock = this.product.getStock();
		this.product.setStock(stock-1);
	}
	
	public void addStock() {
		Integer stock = this.product.getStock();
		this.product.setStock(stock+1);
	}
}
