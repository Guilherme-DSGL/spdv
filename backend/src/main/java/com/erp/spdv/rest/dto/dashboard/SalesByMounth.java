package com.erp.spdv.rest.dto.dashboard;



import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class SalesByMounth {
	
	@Autowired
	public SalesByMounth(long amountSales, BigDecimal amountPrice) {
		this.amountSales = amountSales;
		this.amountPrice = amountPrice;
	}
	
	BigDecimal amountPrice;
	long amountSales;
	
}
