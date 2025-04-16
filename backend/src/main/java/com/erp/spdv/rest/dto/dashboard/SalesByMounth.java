package com.erp.spdv.rest.dto.dashboard;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SalesByMounth {

	public SalesByMounth(long amountSales, BigDecimal amountPrice) {
		this.amountSales = amountSales;
		this.amountPrice = amountPrice;
	}

	BigDecimal amountPrice;
	long amountSales;

}
