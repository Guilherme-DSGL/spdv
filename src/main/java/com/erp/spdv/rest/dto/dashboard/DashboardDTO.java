package com.erp.spdv.rest.dto.dashboard;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashboardDTO {
	
	private long amountAllSales;
	private long amountAllProducts;
	private long amountAllClients;
	private SalesByMounth SalesByCurrentMounth;
	private SalesByMounth SalesByLastMounth;
	private List<RankingProducts> rankingProducts;
	private List<RankingClients> rankingClients;
}




