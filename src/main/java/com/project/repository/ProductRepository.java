package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllProductsByCategoryId(int id);

}
