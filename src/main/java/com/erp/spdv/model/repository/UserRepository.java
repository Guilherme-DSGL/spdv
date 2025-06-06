package com.erp.spdv.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.spdv.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String name);

	boolean existsByName(String name);
}
