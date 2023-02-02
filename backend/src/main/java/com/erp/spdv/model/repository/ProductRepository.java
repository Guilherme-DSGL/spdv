package com.erp.spdv.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.erp.spdv.model.entity.Product;
import com.erp.spdv.model.entity.Sale;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	 @Modifying
	 @Transactional
	 @Query("UPDATE Product p SET p.stock=p.stock+1 WHERE p.id = :id")
	 int addStock(@Param("id") Integer id);
	 
	 @Modifying
	 @Transactional
	 @Query("UPDATE Product p SET p.stock=p.stock-1 WHERE p.id = :id")
	 int substractStock(@Param("id") Integer id);
}
