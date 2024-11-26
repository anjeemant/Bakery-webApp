package com.anjeemant.e_comm.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anjeemant.e_comm.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findUserByEmail(String email);
}
