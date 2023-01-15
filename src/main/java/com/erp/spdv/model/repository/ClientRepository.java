package com.erp.spdv.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.spdv.model.entity.Client;


public interface ClientRepository extends JpaRepository<Client, Integer>{

}
