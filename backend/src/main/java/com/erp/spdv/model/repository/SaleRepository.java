package com.erp.spdv.model.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.spdv.model.entity.Sale;
import com.erp.spdv.rest.dto.dashboard.RankingClients;
import com.erp.spdv.rest.dto.dashboard.RankingProducts;
import com.erp.spdv.rest.dto.dashboard.SalesByMounth;

public interface SaleRepository extends JpaRepository<Sale, Integer>{
	
	@Query( value="SELECT new com.erp.spdv.rest.dto.dashboard.SalesByMounth(COUNT(*) as amountSales, SUM(salePrice) as amountPrice) FROM Sale WHERE MONTH(saleDate) = :month")
	SalesByMounth findSaleDateByMonth(@Param("month") Integer month);
	
	@Query(value="SELECT new com.erp.spdv.rest.dto.dashboard.RankingProducts(p.name, COUNT(product) as amountSale) FROM Sale INNER JOIN Product p ON p.id = product GROUP BY product")
	List<RankingProducts> rankingProducts();
	
	@Query(value="SELECT new com.erp.spdv.rest.dto.dashboard.RankingClients(c.name, COUNT(client) as amountSales) FROM Sale INNER JOIN Client c ON client = c.id GROUP BY client ORDER BY amountSales DESC")
	List<RankingClients> rankingClients();
	
}
