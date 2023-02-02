package com.erp.spdv.rest.dto.dashboard;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RankingProducts {
	
	@Autowired
	public RankingProducts(String name, long amountSales) {
		this.name = name;
		this.amountSales = amountSales;
	}
	String name;
	long amountSales;
}

