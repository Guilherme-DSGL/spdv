package com.erp.spdv.rest.dto.dashboard;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RankingProducts {

	public RankingProducts(String name, long amountSales) {
		this.name = name;
		this.amountSales = amountSales;
	}

	String name;
	long amountSales;
}
