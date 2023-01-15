package com.erp.spdv.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 150)
	@NotEmpty(message = "{field.client.name.required}")
	private String name;  
	
	@Column(nullable = false, length = 20)
	@NotEmpty(message = "{field.client.pass.required}")
	private String pass;
	
	@Column(nullable = false, length = 10)
	@NotEmpty(message = "{field.client.alocation.required}")
	private String alocation;
	
	@Column(name = "birth_date",nullable = false, length = 10)
	@NotNull(message = "{field.client.birthDate.required}")
	private Date birthDate;
}
