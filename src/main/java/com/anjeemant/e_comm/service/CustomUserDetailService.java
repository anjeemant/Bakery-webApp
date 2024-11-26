package com.anjeemant.e_comm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anjeemant.e_comm.model.CustomUserDetail;
import com.anjeemant.e_comm.model.User;
import com.anjeemant.e_comm.repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByEmail(email);
		user.orElseThrow( () -> new UsernameNotFoundException("User Not Found"));
		return user.map(CustomUserDetail::new).get();
	}

}
