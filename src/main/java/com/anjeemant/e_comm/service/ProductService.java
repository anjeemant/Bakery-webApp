package com.anjeemant.e_comm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjeemant.e_comm.model.Product;
import com.anjeemant.e_comm.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProduct(){
		return productRepo.findAll();
	}
	
	public void addProduct(Product product) {
		productRepo.save(product);
	}
	
	public void deleteProductById(long id) {
		productRepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		return productRepo.findById(id);
	}
	
	public List<Product> getAllProductByCategoryId(int id){
		return productRepo.findAllByCategory_Id(id);
	}
	
	
	
	
	
	
	
	
	
}
