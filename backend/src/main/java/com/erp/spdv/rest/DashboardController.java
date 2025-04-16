package com.erp.spdv.rest;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.spdv.model.repository.ClientRepository;
import com.erp.spdv.model.repository.ProductRepository;
import com.erp.spdv.model.repository.SaleRepository;
import com.erp.spdv.rest.dto.dashboard.DashboardDTO;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	private final SaleRepository saleRepository;
	private final ClientRepository clientRepository;
	private final ProductRepository productRepository;

	public DashboardController(SaleRepository repository, ClientRepository clientRepository,
			ProductRepository productRepository) {
		this.saleRepository = repository;
		this.clientRepository = clientRepository;
		this.productRepository = productRepository;
	}

	@GetMapping()
	public DashboardDTO getDashboard() {
		DashboardDTO dashboardDTO = new DashboardDTO();
		var currentMonth = LocalDate.now().getMonthValue();
		var lastMonth = currentMonth - 1;
		dashboardDTO.setAmountAllSales(saleRepository.count());
		dashboardDTO.setAmountAllProducts(productRepository.count());
		dashboardDTO.setAmountAllClients(clientRepository.count());
		dashboardDTO.setSalesByCurrentMounth(saleRepository.findSaleDateByMonth(currentMonth));
		dashboardDTO.setSalesByLastMounth(saleRepository.findSaleDateByMonth(lastMonth));
		dashboardDTO.setRankingProducts(saleRepository.rankingProducts());
		dashboardDTO.setRankingClients(saleRepository.rankingClients());
		return dashboardDTO;
	}
}
