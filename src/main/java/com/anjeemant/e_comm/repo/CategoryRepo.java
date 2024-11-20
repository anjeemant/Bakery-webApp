package com.anjeemant.e_comm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anjeemant.e_comm.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
