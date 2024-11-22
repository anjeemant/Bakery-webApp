package com.anjeemant.e_comm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anjeemant.e_comm.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
	
	public List<Product> findAllByCategory_Id(int id);
	
}
