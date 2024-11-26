package com.anjeemant.e_comm.configuration;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.anjeemant.e_comm.model.Role;
import com.anjeemant.e_comm.model.User;
import com.anjeemant.e_comm.repo.RoleRepo;
import com.anjeemant.e_comm.repo.UserRepo;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	UserRepo userRepo;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		
		// if User IS NOT present in DB then save this below info in DB
		if(!userRepo.findUserByEmail(email).isPresent()) {
			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setEmail(email);
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleRepo.findById(2).get());  // id = 2, cuz it only have two roles(ADMIN, USER)
													// and we decided that id for USER will be 2.
			
			user.setRoles(roles);
			userRepo.save(user);
		}
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
		
	}
		
}
